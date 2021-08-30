package nz.ac.vuw.ecs.swen225.gp21.domain;

import java.util.*;
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
	
	private Deque<Command> playerCommands;
	
	private Chip playerEntity;
	
	private State worldState;
	
	private List<GameObject> allEntities;
	
	private Board board;
	
	private boolean isGameOver;
	
	private final int totalTreasure;
//===========================================================
	/**
	 * Create a new test world. Initializes a test board of 10*10 dimension
	 * and a default player placement of 0,0
	 */
	public World() {
		worldState = new Loading();
		playerCommands = new ArrayDeque<Command>();
		board = new ArrayBoard();
		allEntities = new ArrayList<GameObject>();
		
		playerEntity = new Chip(this);
		//always add the player entity first, to ensure it is the first thing updated
		addObject(playerEntity, new Coord(0,0));
		addObject(new Block(this), new Coord(1, 2));
		isGameOver = false;
		//board.validate() ?
		totalTreasure = board.getRemainingChips();
		assert(totalTreasure >= 0);
		worldState = new Running();
	}
//===========================================================
	/**
	 * The big method, calling this will simulate the world and its behaviors for one tick
	 * We have made a decision that this should occur about ever 200 milliseconds for ~ 10 FPS gameplay
	 * Resource: http://gameprogrammingpatterns.com/game-loop.html, may change this to fixed time step updates later
	 * @param elapsedTime the time since this method was last called, calculated by the caller
	 */
	public void update(double elapsedTime) {
		worldState.update(this, elapsedTime);
		if(isGameOver) System.out.println("Game is over"); //TODO temp check here, do something?
		assert(totalTreasure == board.getRemainingChips());
		//Encapsulate all the events that occurred in this game tick and store it, so other modules can view what happened during this tick
		//TODO
	}
	/**
	 * Return the isGameOver flag
	 * Will be replaced in the future by the state variable
	 * @return true if the game is in a complete state
	 */
	public boolean isGameComplete() {
		return this.isGameOver;
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
	
//==========================================================
	/**
	 * Remove the top element player commands queue
	 * @return the oldest player command in the queue, or null if there are no commands
	 */
	Command poll() {
		return playerCommands.poll();
	}
	/**
	 * Get the tile at a location
	 * Can be used by game objects to make decisions
	 * @param location the location of the tile of interest
	 * @return the tile at the location
	 */
	Tile getTileAt(Coord location) {
		return board.getTileAt(location);
	}
	/**
	 * Get the list of all entities
	 * @return the list of all entities
	 */
	List<GameObject> getEntities(){
		return this.allEntities;
	}
	/**
	 * Get the command queue
	 * @return the command queue
	 */
	Deque<Command> getCommandQueue(){
		return this.playerCommands;
	}
	/**
	 * Get the board for this world
	 * @return the board this world is using
	 */
	Board getBoard() {
		return this.board;
	}
	/**
	 * Get the player entity object
	 * @return the player entity
	 */
	Chip getPlayer() {
		return this.playerEntity;
	}
	
//==========================================================
	/**
	 * Move an object directly, helper for teleporter
	 * Does not perform a terrain check when placing the object 
	 * {this can be changed easily if needed}
	 * Only asks the occupier if the object can be moved
	 * @param o the object being moved
	 * @param destination where it is being moved to
	 */
	void moveObject(GameObject o, Coord destination) {
		board.moveObject(o, destination);
	}
	/**
	 * Moves the specified game object up if possible
	 * @param o the object being moved
	 */
	void moveUp(GameObject o) {
		o.updateDirection(Direction.NORTH);
		board.moveUp(o);
		//notify?
	}
	/**
	 * Moves the specified game object down if possible
	 * @param o the object being moved
	 */
	void moveDown(GameObject o) { o.updateDirection(Direction.SOUTH); board.moveDown(o); }
	/**
	 * Moves the specified game object left if possible
	 * @param o the object being moved
	 */
	void moveLeft(GameObject o) { o.updateDirection(Direction.WEST); board.moveLeft(o); }
	/**
	 * Moves the specified game object right if possible
	 * @param o the object being moved
	 */
	void moveRight(GameObject o) { o.updateDirection(Direction.EAST); board.moveRight(o); }
//==================================================================================
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
//====================================================================================
	/**
	 * This method is called when chip enters a treasure tile
	 * But before the treasure type is replaced with a 'free' type
	 */
	void collectedAChip() {
		playerEntity.treasureCollected++;
		if(board.getRemainingChips() == 0) board.openExit();
		//					minus one because the board hasn't replaced the treasure tile yet
		assert((playerEntity.treasureCollected + board.getRemainingChips() - 1) == (totalTreasure));
	}
	/**
	 * This method is called when chip enters the exit square
	 */
	void enteredExit() {
		isGameOver = true;
//		worldState = new GameOver(); //TODO
	}
	/**
	 * Called when an entity enters an info tile
	 * @param msg the message the info tile contained
	 */
	void enteredInfo(String msg) {
		//TODO - do something useful here
		System.out.println("Information: ["+msg+"]"); //TODO temporary operation
	}
//====================================================================================
	@Override
	public String toString() {
		StringBuilder ans = new StringBuilder();
		//add board state
		ans.append("Is game over? -> "+isGameOver+"\n");
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
