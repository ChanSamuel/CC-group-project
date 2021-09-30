package nz.ac.vuw.ecs.swen225.gp21.app;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameEvent;
import nz.ac.vuw.ecs.swen225.gp21.domain.Item;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.persistency.GameCaretaker;
import nz.ac.vuw.ecs.swen225.gp21.persistency.LevelHandler;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;
import nz.ac.vuw.ecs.swen225.gp21.recorder.Recorder;
import nz.ac.vuw.ecs.swen225.gp21.recorder.RecorderException;
import nz.ac.vuw.ecs.swen225.gp21.renderer.SoundType;
import nz.ac.vuw.ecs.swen225.gp21.renderer.WorldJPanel;
import nz.ac.vuw.ecs.swen225.gp21.renderer.WrapperJPanel;

/**
 * A Controller interface which provides implemented methods for interacting with the game.
 * Subclasses are expected to use this class by calling the requisite methods to interact with the game.
 * 
 * This class' exposed methods do not do anything immediately, but rather queue up their Action as a request 
 * which gets handled at the very start of each iteration of the game loop.
 * 
 * The game loop is always initalised before any user-interface code provided by the subclasses is executed.
 * 
 * @author Sam
 *
 */
public abstract class Controller {
	
	/**
	 * Contains a queue of the actions issued by the Controller. This queue is polled every tick.
	 */
	private Queue<Action> actions;
	
	/**
	 * The entrypoint into the rendering module.
	 */
	protected WrapperJPanel renderer;
	
	/**
	 * The entrypoint into the persistency module.
	 */
	protected Persister persister;
	
	/**
	 * The level number.
	 * 0 For the test level, 1 for level 1, 2 for level 2.
	 */
	protected int levelNumber = 1;
	
	/**
	 * The inventory of the player. Pretty much just contains keys.
	 */
	protected List<Item> inventory = new ArrayList<Item>();
	
	/**
	 * The entrypoint into the Domain module of the game.
	 */
	protected Domain world;
	
	/**
	 * The entrypoint into the Recorder module.
	 */
	protected Recorder recorder;
	
	/**
	 * The game loop where all other modules are updated periodically.
	 * It is Package private so that only Action can access it.
	 */
	GameLoop gLoop;
	
	/**
	 * Constructs the Controller by initialising this object's fields and then starts the main game loop.
	 * After construction, the Controller can be used to make requests to the game by calling this object's
	 * methods.
	 * 
	 * The game loop thread is always intialised before any subclass' code is executed.
	 * The reason the thread is started on construction is to prevent subclass code from initialising their 
	 * Controller implementation before the program is able to pick up actions requested by the subclass Controller.
	 */
	public Controller() {
		// First, construct all the objects, then open the new thread.
		actions = new ArrayDeque<Action>();
		renderer = WrapperJPanel.getInstance();
		recorder = new Recorder();
		
		world = new World() {

			@Override
			public void enteredExit() {
				Controller.this.enteredExit();
			}

			@Override
			public void enteredInfo(String msg) {
				Controller.this.enteredInfo(msg);
			}

			@Override
			public void leftInfo() {
				Controller.this.leftInfo();
			}

			@Override
			public void playerLost() {
				Controller.this.playerLost();
			}

			@Override
			public void playerGainedItem(Item item) {
				Controller.this.playerGainedItem(this.playerEntity.getInventory(), item);
			}

			@Override
			public void playerConsumedItem(Item item) {
				Controller.this.playerConsumedItem(this.playerEntity.getInventory(), item);
			}

			@Override
			public void openedDoor() {
				Controller.this.openedDoor();
			}

			@Override
			public void collectedChip() {
				Controller.this.collectedChip(this.totalTreasure - playerEntity.treasureCollected);
			}

			@Override
			public void objectTeleported() {
				Controller.this.objectTeleported();
			}

			@Override
			public void objectPushed() {
				Controller.this.objectPushed();
			}

			@Override
			public void eventOccured(GameEvent e) {
				// Add the event to the recorder via a proxy object.
				try {
					recorder.add(new GameUpdateProxy(e));
				} catch (RecorderException re) {
					warning("Something went wrong when adding a tick to the recorder:\n"
									+ re.getMessage());
				}
			}
			
		};
		
		try {
			persister = new Persister(new LevelHandler(), new GameCaretaker(world));
		} catch (PersistException e) {
			throw new Error("Failed to initalise the Controller because:\n"+ e.getMessage(), e);
		}
		
		// Open the thread and start it.
		gLoop = new GameLoop(actions, this);
		Thread t = new Thread(gLoop);
		
		t.start();
	}
	
	/**
	 * Run the event loop of this Controller and initialise the user interface.
	 */
	public abstract void run();
	
	/**
	 * Dialog to warn the user of something.
	 * @param message
	 */
	protected abstract void warning(String message);
	
	/**
	 * Dialog to notify the user of something.
	 * @param message
	 */
	protected abstract void report(String message);
	
	/**
	 * Informs the user of something.
	 * Does not necesarrily bring up a dialog.
	 * @param message
	 */
	protected abstract void inform(String message);
	
	
	/* ****************************************************
	 * METHODS WHICH ARE CALLED BY THE WORLD IMPLEMENTATION
	 * ****************************************************
	 */
	
	/**
	 * The method to be invoked when chap enters the exit tile.
	 */
	protected abstract void enteredExit();
	
	protected abstract void enteredInfo(String msg);

	protected void leftInfo() {
		// Do nothing...
	}

	protected abstract void playerLost();

	protected abstract void playerGainedItem(List<Item> playerInventory, Item item);

	protected abstract void playerConsumedItem(List<Item> playerInventory, Item item);

	protected abstract void openedDoor();

	protected abstract void collectedChip(int remainingChips);

	protected abstract void objectTeleported();
	
	protected abstract void objectPushed();
	
	/* ************************
	 * OTHER NOTIFYING METHODS.
	 * ************************
	 */
	
	protected abstract void updateTimerOperation(int timeLeft);
	
	protected abstract void pauseOperation();
	
	void clearInventory() {
		this.inventory.clear();
	}
	
	
	
	/* *********************************************
	 * METHODS FOR ACTIONS THE CONTROLLER CAN ISSUE.
	 * *********************************************
	 */
	
	/**
	 * Issue an Action to the game by adding it to the Action queue.
	 * The Action may not be proccessed immediately.
	 * Actions can be moves, or requests to pause the game.
	 * @param a : the Action that should be issued.
	 * 
	 */
	protected void issue(Action a) {
		if (this.actions.size() >= 1000) { // Stop issuing actions once 1000 are queued up.
			return;
		}
		this.actions.add(a);
	}
	
	/**
	 * Attempts to move Chap up.
	 * The move may not be attempted immediately.
	 */
	public void moveUp() {
		issue(new MoveUpAction());
	}
	
	/**
	 * Attempts to move Chap down.
	 * The move may not be attempted immediately.
	 */
	public void moveDown() {
		issue(new MoveDownAction());
	}
	
	/**
	 * Attempts to move Chap left.
	 * The move may not be attempted immediately.
	 */
	public void moveLeft() {
		issue(new MoveLeftAction());
	}
	
	/**
	 * Attempts to move Chap right.
	 * The move may not be attempted immediately.
	 */
	public void moveRight() {
		issue(new MoveRightAction());
	}
	
	/**
	 * Exits the game without saving.
	 * This method performs a hard System.exit(...) which will close all threads upon invocation.
	 */
	public void exit() {
		System.exit(0);
	}
	
	/**
	 * Exits the game and saves the latest state into a file.
	 * The game can be resumed later on.
	 * @param saveFile 
	 */
	public void exitAndSave(File saveFile) {
		issue(new ExitSaveAction(saveFile));
	}
	
	/**
	 * Loads a file to be replayed. After calling this method, the game should be in a 'replay' state.
	 * @param f
	 */
	public void loadReplay(File f) {
		issue(new LoadReplayAction(f));
	}
	
	/**
	 * Loads a new game at level 1.
	 */
	public void loadLevel1() {
		issue(new LoadLevel1Action());
	}
	
	/**
	 * Loads a new game at level 2.
	 */
	public void loadLevel2() {
		issue(new LoadLevel2Action());
	}
	
	/**
	 * Load a new game using the Test level.
	 */
	public void loadTestLevel() {
		issue(new LoadTestLevelAction());
	}
	
	/**
	 * Pauses the game loop and sets the Controller to refuse all Actions other than a resume.
	 * Resumes the game loop if already paused.
	 */
	public void togglePause() {
		issue(new TogglePauseAction());
	}
	
	public void pauseGame() {
		issue(new PauseGameAction());
	}
	
	public void resumeGame() {
		issue(new ResumeGameAction());
	}
	
	/**
	 * Toggles whether the current replay is in auto play mode or manual play mode.
	 * Will warn the caller and won't execute the action if not currently in replay.
	 */
	public void toggleAutoPlay() {
		issue(new ToggleAutoPlayAction());
	}
	
	/**
	 * Loads a previously saved game.
	 * @param f
	 */
	public void loadGame(File f) {
		issue(new LoadGameAction(f));
	}
	
	/**
	 * Start a new game from the specified level.
	 * @param s : a String which can be "Level 1", "Level 2", or "TEST LEVEL".
	 */
	public void newGame(String s) {
		issue(new NewGameAction(s));
	}
	
	/**
	 * Start a new game from the specified level.
	 * @param levelNumber : 0 for the test level, 1 for level 1, 2 for level 2.
	 */
	public void newGame(int levelNumber) {
		if (levelNumber == 1) {
			newGame("Level 1");
		} else if (levelNumber == 2) {
			newGame("Level 2");
		} else if (levelNumber == 0){
			newGame("TEST LEVEL");
		} else {
			warning("Level number " + levelNumber + " not recognised.\n"
					+ "Acceptable level numbers are [0,1,2] where 0 is the test level.");
		}
	}
	
	/**
	 * Save the current game state to a file.
	 * Warns the user if a game isn't being currently played.
	 * @param saveFile
	 */
	public void saveGameState(File saveFile) {
		issue(new SaveStateAction(saveFile));
	}
	
	/**
	 * Save the current game into a replay file.
	 * Warns the user if a game isn't currently being played.
	 * @param saveFile
	 */
	public void saveReplay(File saveFile) {
		issue(new SaveReplayAction(saveFile));
	}
	
	/**
	 * Change the speed that the replay is being played at.
	 * Any values between [0.2, 5] inclusive are acceptable speeds.
	 * Warns the user if the speed is not acceptable.
	 * Warns the user if the value isn't a number.
	 * @param val
	 */
	public void changeSpeed(String val) {
		// Using scanner purely to check if the value is a number.
		Scanner sc = new Scanner(val);
		if (sc.hasNextFloat()) {
			sc.close();
			issue(new ChangeTickSpeedAction(Double.valueOf(val)));
		} else {
			sc.close();
			warning("Speed multiplier value must be a number");
		}
	}
	
	/**
	 * Change the speed that the replay is being played at.
	 * Any values between [0.2, 5] inclusive are acceptable speeds.
	 * Warns the user if the speed is not acceptable.
	 * @param multiple
	 */
	public void changeSpeed(double multiple) {
		issue(new ChangeTickSpeedAction(multiple));
	}
	
	/**
	 * Go back a tick during a replay.
	 * Warns the user if the game isn't in replay mode yet.
	 */
	public void backTick() {
		issue(new BackTickAction());
	}
	
	/**
	 * Go forward a tick during a replay.
	 * Warns the user if the game isn't in replay mode yet.
	 */
	public void forwardTick() {
		issue(new ForwardTickAction());
	}
	
	/**
	 * Display a help message.
	 * @param msg : the message.
	 */
	public void displayHelpMessage(String msg) {
		issue(new DisplayHelpAction(msg));
	}
	
	/**
	 * Exits to main menu.
	 * For FuzzController, this will simply move into the menu state, where only loading actions can
	 * be performed.
	 */
	protected void exitToMenu() {
		issue(new ExitToMenuAction());
	}
	
	
}
