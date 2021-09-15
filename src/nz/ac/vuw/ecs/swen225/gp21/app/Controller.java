package nz.ac.vuw.ecs.swen225.gp21.app;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Queue;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.Item;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.TestWorld;
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
	private Action failedAction = null;
	
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
	
	protected Recorder recorder;
	
	protected boolean isInitialised = false;
	
	/**
	 * The game loop.
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
		renderer = new WorldJPanel();
		persister = new ConcretePersister();
		recorder = new Recorder();
		
		world = new World() {

			@Override
			public void collectedAChip() {
				chipCollectedTrans();

			}

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
			public void openedADoor() {
				playerOpenedADoorTrans();
				
			}
		};
		
		// setupDomain(); Only used for testing the Renderer works with App.
		
		// Open the thread and start it.
		gLoop = new GameLoop(actions, this);
		Thread t = new Thread(gLoop);
		
		t.start();
	}
	
	/**
	 * Test method to make sure Renderer and App works.
	 */
	private void setupDomain() {
		Level testLevel;
		int rows = 10;
		int columns = 10;
		String tiles = "";
		tiles += "..........";
		tiles += "..........";
		tiles += "v#........";
		tiles += ".#........";
		tiles += "1#........";
		tiles += "##########";
		tiles += "1#........";
		tiles += ".#......#X";
		tiles += "v#......#.";
		tiles += ".....ccc#E";
		String entities = "";
		entities += "C.........";
		entities += "..........";
		entities += "..B.......";
		entities += "..........";
		entities += "..........";
		entities += "..........";
		entities += "..........";
		entities += "..........";
		entities += "..........";
		entities += "..........";
		testLevel = new Level(rows, columns, tiles, entities, "No Info");
		
		world.loadLevelData(testLevel);
		world.doneLoading();
	}
	
	
	/**
	 * Experimental method.
	 */
	protected void deInitialise() {
		this.isInitialised = false;
		renderer = new WorldJPanel();
	}
	
	/**
	 * Ready the renderer for game playing.
	 */
	protected void initialise() {
		this.isInitialised = true;
		renderer.setDomain(world);
	}
	
	/**
	 * Run the event loop of this Controller and initialise the user interface.
	 */
	public abstract void run();
	
	/**
	 * The transition method called upon chip collection.
	 * Called by GameWorld on the GameLoop thread.
	 */
	public abstract void chipCollectedTrans();
	
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
	public abstract void playerOpenedADoorTrans();
	
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
		return this.failedAction;
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
	 * The game should resume at the last unfinished level.
	 */
	public void exit() {
		issue(new ExitAction());
	}
	
	/**
	 * Exits the game and saves the latest state into a file.
	 * The game can be resumed later on.
	 */
	public void exitAndSave() {
		issue(new ExitSaveAction());
	}
	
	/**
	 * Loads a file to be replayed. After calling this method, the game should be in a 'replay' state.
	 * @param f
	 */
	public void loadReplay(File f) {
		issue(new LoadReplayAction());
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
	 * Sets the last failed action to the given Action.
	 * This method is should only be used internally by classes like GameLoop.
	 * This method should be used whenever an action fails.
	 * @param a
	 */
	public void setFailedAction(Action a) {
		this.failedAction = a;
	}
	
}
