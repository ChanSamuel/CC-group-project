package nz.ac.vuw.ecs.swen225.gp21.app;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Queue;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.Item;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.persistency.ConcretePersister;
import nz.ac.vuw.ecs.swen225.gp21.persistency.Persister;
import nz.ac.vuw.ecs.swen225.gp21.recorder.Recorder;
import nz.ac.vuw.ecs.swen225.gp21.renderer.WorldJPanel;

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
	protected WorldJPanel renderer;
	
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
		renderer = new WorldJPanel();
		persister = new ConcretePersister();
		recorder = new Recorder();
		
		world = new World() {

			@Override
			public void enteredExit() {
				enteredExitTrans();
			}

			@Override
			public void enteredInfo(String msg) {
				enteredInfoTrans();
			}

			@Override
			public void leftInfo() {
				leftInfoTrans();
			}

			@Override
			public void playerLost() {
				playerLostTrans();
			}

			@Override
			public void playerGainedItem(Item item) {
				playerGainedItemTrans();
			}

			@Override
			public void playerConsumedItem(Item item) {
				playerConsumedItemTrans();
			}

			@Override
			public void openedDoor() {
				playerOpenedDoorTrans();
				
			}

			@Override
			public void collectedChip() {
				
			}
			
		};
		
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
	 * The transition method called when chap enters the exit tile.
	 * Called by GameWorld on the GameLoop thread.
	 */
	public abstract void enteredExitTrans();
	
	/**
	 * The transition method called upon chap entering an info tile.
	 * Called by GameWorld on the GameLoop thread.
	 */
	public abstract void enteredInfoTrans();
	
	/**
	 * The transition method called when chap leaves an info tile.
	 * Called by GameWorld on the GameLoop thread.
	 */
	public abstract void leftInfoTrans();
	
	/**
	 * The transition method called when chap loses.
	 * Called by GameWorld on the GameLoop thread.
	 */
	public abstract void playerLostTrans();
	
	/**
	 * The transition method called when chap gains an item.
	 * Called by GameWorld on the GameLoop thread.
	 */
	public abstract void playerGainedItemTrans();
	
	/**
	 * The transition method called when chap consumes an item.
	 * Called by GameWorld on the GameLoop thread.
	 */
	public abstract void playerConsumedItemTrans();
	
	/**
	 * The transition method called when chap opens a door.
	 * Called by GameWorld on the GameLoop thread.
	 */
	public abstract void playerOpenedDoorTrans();
	
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
	 * Issue an Action to the game by adding it to the Action queue.
	 * The Action may not be proccessed immediately.
	 * Actions can be moves, or requests to pause the game.
	 * @param a : the Action that should be issued.
	 * 
	 */
	public void issue(Action a) {
		this.actions.add(a);
	}
	
	/**
	 * Returns the last failing Action that was issued.
	 * This is an Action which was issued, processed, then failed due to it being invalid some way.
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
	 * Pauses the game loop and sets the Controller to refuse all requests other than resumeGame().
	 */
	public void pauseGame() {
		issue(new PauseAction());
	}
	
	/**
	 * Resumes the game loop if it is paused.
	 */
	public void resumeGame() {
		issue(new ResumeAction());
	}
	
	/**
	 * Tells the game loop to go into auto play mode.
	 */
	public void setAutoPlay(boolean isAutoPlay) {
		issue(new SetAutoPlayAction(isAutoPlay));
	}
	
	public void loadGame(File f) {
		issue(new LoadGameAction(f));
	}
	
	public void newGame(String s) {
		issue(new NewGameAction(s));
	}
	
	public void haltGame() {
		issue(new HaltAction());
	}
	
	public void saveGameState(File saveFile) {
		issue(new SaveStateAction(saveFile));
	}
	
	public void saveReplay(File saveFile) {
		issue(new SaveReplayAction(saveFile));
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
