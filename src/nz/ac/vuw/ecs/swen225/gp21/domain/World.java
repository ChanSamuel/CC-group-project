package nz.ac.vuw.ecs.swen225.gp21.domain;

import java.util.Deque;
import java.util.List;

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
//===========================================================
	/**
	 * The big method, calling this will simulate the world and its behaviors for one tick
	 * We have made a decision that this should occur about ever 200 milliseconds for ~ 10 FPS gameplay
	 * Resource: http://gameprogrammingpatterns.com/game-loop.html, may change this to fixed time step updates later
	 * @param elapsedTime the time since this method was last called, calculated by the caller
	 */
	public void update(double elapsedTime) {
		//Process one player input { ? should we defer this to the 'player movement controller', or do it here, explicitly ? }
		
		//update all game objects
		
		//check game state (i.e. is the game over?)
		
		//Encapsulate all the events that occurred in this game tick and store it, so other modules can view what happened during this tick
	}
//==========================================================
	/**
	 * Remove the top element player commands queue
	 * @return the oldest player command in the queue, or null if there are no commands
	 */
	Command poll() {
		return playerCommands.poll();
	}
	
//==========================================================
	/**
	 * Move an object directly, helper for teleporter
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
	public void moveChipUp() {
		playerCommands.add(new MoveUp(playerEntity));
	}
	/**
	 * Enqueues a move down command for the playerEntity into the movement queue
	 */
	public void moveChipDown() {
		playerCommands.add(new MoveDown(playerEntity));
	}
	/**
	 * Enqueues a move left command for the playerEntity into the movement queue
	 */
	public void moveChipLeft() {
		playerCommands.add(new MoveLeft(playerEntity));
	}
	/**
	 * Enqueues a move right command for the playerEntity into the movement queue
	 */
	public void moveChipRight() {
		playerCommands.add(new MoveRight(playerEntity));
	}
//====================================================================================
	/**
	 * This method is called when chip collects a treasure
	 */
	public void collectedAChip() {
		playerEntity.treasureCollected++;
		if(board.getRemainingChips() == 0) board.openExit();
	}
	/**
	 * This method is called when chip enters the exit square
	 */
	public void enteredExit() {
//		worldState = new GameOver(); //TODO
	}
}
