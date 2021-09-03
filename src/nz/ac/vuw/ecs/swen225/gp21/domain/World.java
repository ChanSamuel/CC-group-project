package nz.ac.vuw.ecs.swen225.gp21.domain;

import java.util.*;

import nz.ac.vuw.ecs.swen225.gp21.domain.commands.*;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Block;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.GameOver;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Loading;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Running;
/**
 * The world provides the main interface of the domain package, for other modules to interact with.
 * @author Benjamin
 *
 */
public class World {
	//NOTE: the recording of game ticks can be in a linked list, we dont need to store these in contiguous storage
	//NOTE: to define entities (like the bug and block) I'm going to hard code them as classes for now
	//		this resource: http://gameprogrammingpatterns.com/type-object.html
	//		explains how to do this as data in a config file, although, this still needs to be determined
	//		Somehow, the persistence module needs to feed in new entities
	public int updates;
	
	public MultiMove event;
	
	private List<Tick> ticks;
	
	private Deque<Command> playerCommands;
	
	private Chip playerEntity;
	
	private State worldState;
	
	private List<GameObject> allEntities;
	
	private Board board;
	
	public int totalTreasure;
//===========================================================
//WORLD INITIALIZATION METHODS
	/**
	 * Create a new test world. Initializes a default test board of 10*10 dimension
	 * and a default player placement of 0,0
	 */
	public World() {
		updates = 0;
		worldState = new Loading();
		playerCommands = new ArrayDeque<Command>();
		board = new ArrayBoard();
		allEntities = new ArrayList<GameObject>();
		playerEntity = new Chip(this);
		//always add the player entity first, to ensure it is the first thing updated
		addObject(playerEntity, new Coord(0,0));
		addObject(new Block(this), new Coord(1, 2));
		//board.validate() ?
		totalTreasure = board.getRemainingChips();
		assert(totalTreasure >= 0);
		worldState = new Running();
	}
	/**
	 * Create a world initialized with level data
	 * @param level the level data
	 */
	public World(Level level) {
		worldState = new Loading();
		playerCommands = new ArrayDeque<Command>();
		allEntities = new ArrayList<GameObject>();
		this.loadLevel(level);
		this.totalTreasure = board.getRemainingChips();
		assert(totalTreasure >= 0);
	}
	
	
//===========================================================
//PUBLIC API METHODS FOR OTHER MODULES TO TALK TO
	/**
	 * The big method, calling this will simulate the world and its behaviors for one tick
	 * We have made a decision that this should occur about ever 200 milliseconds for ~ 10 FPS gameplay
	 * Resource: http://gameprogrammingpatterns.com/game-loop.html, may change this to fixed time step updates later
	 * @param elapsedTime the time since this method was last called, calculated by the caller
	 * @return A record of all the changes that happened during the update
	 */
	public Tick update(double elapsedTime) { return worldState.update(this, elapsedTime); }
	/**
	 * Move a tick into the world to be played forwards, or replayed
	 * @param tick
	 */
	public void saveTick(Tick tick) {
		//TODO
	}
	/**
	 * Initialize the world with data from the level object
	 * @param level the level information
	 */
	public void loadLevel(Level level) { worldState.loadLevel(this, level); }
	/**
	 * External package entity should call this when 
	 * all the external objects have been added 
	 * to the world via addObject(...)
	 */
	public void doneLoading() {
		this.worldState = new Running();
		//TODO this should probably be a temp method?
		//Theres gotta be a better way to load in external entities
	}
	/**
	 * Return the isGameOver flag
	 * Will be replaced in the future by the state variable
	 * @return true if the game is in a complete state
	 */
	public boolean isGameComplete() {
		return worldState instanceof GameOver;
	}
	/**
	 * Get the number of columns the board has
	 * @return the number of columns
	 */
	public int getBoardWidth() {
		return worldState.getBoardWidth(this);
	}
	/**
	 * Get the number of rows the board has
	 * @return the number of rows 
	 */
	public int getBoardHeight() {
		return worldState.getBoardHeight(this);
	}
	/**
	 * Determine if a coordinate is valid on the board
	 * @param c the coordinate
	 * @return if it is valid on the board
	 */
	public boolean isCoordValid(Coord c) {
		return worldState.isCoordValid(this, c);
	}
	/**
	 * Add a new game object to the world
	 * TODO currently only works during initialization
	 * @param e the entity being added
	 * @param c The location on the board this entity should be placed
	 * @return true if the addition succeeded
	 */
	public boolean addObject(GameObject e, Coord c) {
		return worldState.addObject(this, e, c);
	}
	/**
	 * Get the state of the world
	 * Any package can ask for this
	 * @return the state the world is in
	 */
	public State getWorldState() {
		return this.worldState;
	}
	//TODO add external state setters?
	// i.e. if the user is done watching replay and wants to go back to playing
	//      how will those state changes happen?
//==========================================================
//EVEN MORE PUBLIC API METHODS
		/**
		 * Enqueues a move up command for the playerEntity into the movement queue
		 */
		public void moveChipUp() {worldState.moveChipUp(this);}
		/**
		 * Enqueues a move down command for the playerEntity into the movement queue
		 */
		public void moveChipDown() {worldState.moveChipDown(this);}
		/**
		 * Enqueues a move left command for the playerEntity into the movement queue
		 */
		public void moveChipLeft() {worldState.moveChipLeft(this);}
		/**
		 * Enqueues a move right command for the playerEntity into the movement queue
		 */
		public void moveChipRight() {worldState.moveChipRight(this);}	
	
//==========================================================
//INTERNAL GAME GETTERS AND SETTERS - these methods are needed so worldState.load(...) can set fields
	/**
	 * Remove the top element player commands queue
	 * @return the oldest player command in the queue, or null if there are no commands
	 */
	public Command poll() {
		return playerCommands.poll();
	}
	/**
	 * Get the tile at a location
	 * Can be used by game objects to make decisions
	 * @param location the location of the tile of interest
	 * @return the tile at the location
	 */
	public Tile getTileAt(Coord location) {
		return board.getTileAt(location);
	}
	/**
	 * Get the list of all entities
	 * @return the list of all entities
	 */
	public List<GameObject> getEntities(){
		return this.allEntities;
	}
	/**
	 * Get the command queue
	 * @return the command queue
	 */
	public Deque<Command> getCommandQueue(){
		return this.playerCommands;
	}
	/**
	 * Get the board for this world
	 * @return the board this world is using
	 */
	public Board getBoard() {
		return this.board;
	}
	/**
	 * Set the board for this world
	 * Caution, GameEntities will still have board references
	 * @param b the board this world will use
	 */
	public void setBoard(Board b) {
		this.board = b;
	}
	/**
	 * Get the player entity object
	 * @return the player entity
	 */
	public Chip getPlayer() {
		return this.playerEntity;
	}
	/**
	 * Set the player entity
	 * for this world
	 * @param c the player entity this world will use
	 */
	public void setPlayer(Chip c) {
		this.playerEntity = c;
	}
//====================================================================================
//INTERNAL GAME EVENT METHODS - These methods are invoked when the relevant event occurs
	/**
	 * This method is called when chip enters a treasure tile
	 * But before the treasure type is replaced with a 'free' type
	 */
	public void collectedAChip() {
		playerEntity.treasureCollected++;
		//notify?
	}
	/**
	 * This method is called when chip enters the exit square
	 */
	public void enteredExit() {
		worldState = new GameOver(); 
	}
	/**
	 * Called when an entity enters an info tile
	 * @param msg the message the info tile contained
	 */
	public void enteredInfo(String msg) {
		//TODO - do something useful here
		System.out.println("Information: ["+msg+"]"); //TODO temporary operation
	}
	/**
	 * Called when the player looses
	 */
	public void playerLost() {
		System.out.println("Player lost");//TODO temporary operation
	}
//==========================================================
//MOVEMENT METHODS - these methods talk to the board to move stuff
// I'm open to criticism on why we have this extra layer of indirection GameObject/Tile -> world -> Board -> world information needed to make a decision	
//
//TODO 	How to stop this from getting out of control when playing a replay! :(
// 		Could off-load it to state, only do this while running.
//		While in replaying state: just move stuff, don't generate events. TODO
//	
	/**
	 * Try Move an object to a destination
	 * Moving an object can cause a cascade of further events to occur
	 * @param o the object being moved
	 * @param destination where the object is being moved to
	 * @return true if the move succeeded
	 */
	public boolean moveObject(GameObject o, Coord destination) {
		return board.tryMoveObject(destination, o);
	}
	/**
	 * Try to move the specified game object up.
	 * If the move succeeds, it is recorded.
	 * @param o the object being moved
	 */
	public void moveUp(GameObject o) {
		//TODO 	you could off-load this to worldState, would make life easier, then there 
		//		won't be crazy issues when you call these same methods to do a replay. "event" is already public
		//		(making a move in a replay should not generate an event to be recorded in a replay)
		o.updateDirection(Direction.NORTH);
		boolean moved = moveObject(o, o.dir.next(o.currentTile.location));
		if(moved) event.saveEvent(new MoveUp(o));
		//notify observers?
	}
	/**
	 * Try to move the specified game object down.
	 * If the move succeeds, it is recorded.
	 * @param o the object being moved
	 */
	public void moveDown(GameObject o) { 
		o.updateDirection(Direction.SOUTH); 
		Coord destination = o.dir.next(o.currentTile.location);
		if(moveObject(o, destination)) event.saveEvent(new MoveDown(o));
	}
	/**
	 * Try to move the specified game object left.
	 * If the move succeeds, it is recorded.
	 * @param o the object being moved
	 */
	public void moveLeft(GameObject o) { 
		o.updateDirection(Direction.WEST); 
		Coord destination = o.dir.next(o.currentTile.location);
		if(moveObject(o, destination)) event.saveEvent(new MoveLeft(o));
	}
	/**
	 * Try to move the specified game object right.
	 * If the move succeeds, it is recorded.
	 * @param o the object being moved
	 */
	public void moveRight(GameObject o) { 
		o.updateDirection(Direction.EAST); 
		Coord destination = o.dir.next(o.currentTile.location);
		if(moveObject(o, destination)) event.saveEvent(new MoveRight(o));
	}	
//====================================================================================
//TO-STRING
	@Override
	public String toString() {
		StringBuilder ans = new StringBuilder();
		//add board state
		ans.append("Is game over? -> "+isGameComplete()+"\n");
		//add player inventory
		//add queue
		ans.append("PlayerQueue: \n");
		if(playerCommands.isEmpty()) ans.append("EMPTY");
		for(Command c : playerCommands) ans.append(c+"\n");
		ans.append("\n");
		//add all entities
		ans.append("All entities: \n");
		for(GameObject e : allEntities) ans.append(e+"\n");
		ans.append("\n");
		//add board
		ans.append("Board: \n"+board);
		return ans.toString();
	}
}
