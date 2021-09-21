package nz.ac.vuw.ecs.swen225.gp21.app;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.JFrame;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.Item;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.persistency.ConcretePersister;
import nz.ac.vuw.ecs.swen225.gp21.persistency.Persister;
import nz.ac.vuw.ecs.swen225.gp21.recorder.Recorder;
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
	 * The last issued Action which was not valid.
	 */
	private Queue<Action> failedActions;
	
	/**
	 * The entrypoint into the rendering module.
	 */
	protected WrapperJPanel renderer;
	
	/**
	 * The entrypoint into the persistency module.
	 */
	protected Persister persister;
	
	/**
	 * The time left in the level in seconds
	 */
	protected long timeLeft = 60;
	
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
		failedActions = new ArrayDeque<Action>();
		renderer = new WrapperJPanel();
		persister = new ConcretePersister();
		recorder = new Recorder();
		
		world = new World() {

			@Override
			public void enteredExit() {
				
			}

			@Override
			public void enteredInfo(String msg) {
				
			}

			@Override
			public void leftInfo() {
				
			}

			@Override
			public void playerLost() {
				
			}

			@Override
			public void playerGainedItem(Item item) {
				
			}

			@Override
			public void playerConsumedItem(Item item) {
				
			}

			@Override
			public void openedDoor() {
				
			}

			@Override
			public void collectedChip() {
				WrapperJPanel.playSound(SoundType.PICK_UP_A_CHIP);
				//inform("Remaining Chips: " + (this.totalTreasure - playerEntity.treasureCollected));
			}

			@Override
			public void objectTeleported() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void objectPushed() {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		// Open the thread and start it.
		gLoop = new GameLoop(actions, this);
		Thread t = new Thread(gLoop);
		
		t.start();
	}
	
	/**
	 * Test
	 */
	public abstract JFrame getFrame();
	
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
	
	/**
	 * Requests back focus for the key listener.
	 * For Fuzz controller, this should do nothing.
	 */
	protected abstract void requestFocus();
	
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
	private void issue(Action a) {
		this.actions.add(a);
	}
	
	/**
	 * Returns the last failing Action that was issued.
	 * This is an Action which was issued, processed, then failed due to it being invalid some way.
	 * Currently does nothing.
	 * @return last failing action
	 */
	public Action getLastFailedAction() {
		return this.failedActions.peek();
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
	 * Pauses the game loop and sets the Controller to refuse all Actions other than another TogglePauseAction.
	 * Resumes the game loop if already paused.
	 */
	public void togglePause() {
		issue(new TogglePauseAction());
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
	 * Halt the game.
	 * This won't do anything in FuzzController.
	 */
	public void haltGame() {
		issue(new HaltAction());
	}
	
	/**
	 * Add a failed action as the last failed action.
	 * This method is should only be used internally by classes like GameLoop.
	 * This method should be used whenever an action fails.
	 * @param a
	 */
	void addFailedAction(Action a) {
		this.failedActions.add(a);
	}
	
	
	
}
