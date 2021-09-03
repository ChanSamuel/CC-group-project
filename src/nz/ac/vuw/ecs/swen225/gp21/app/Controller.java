package nz.ac.vuw.ecs.swen225.gp21.app;

import java.io.File;

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
	 * Constructs the Controller by initialising the the main game loop of this Controller by opening 
	 * a new Swing worker thread.
	 * After construction, the Controller can be used to make requests to the game by calling this object's
	 * methods.
	 */
	public Controller() {
		// TODO: First, construct all the objects, then open the new thread.
	}
	
	/**
	 * Attempts to move Chap up.
	 * The move may not be attempted immediately.
	 * @return if move was valid or not.
	 */
	public boolean moveUp() {
		return false;
	}
	
	/**
	 * Attempts to move Chap down.
	 * The move may not be attempted immediately.
	 * @return if move was valid or not.
	 */
	public boolean moveDown() {
		return false;
	}
	
	/**
	 * Attempts to move Chap left.
	 * The move may not be attempted immediately.
	 * @return if move was valid or not.
	 */
	public boolean moveLeft() {
		return false;
	}
	
	/**
	 * Attempts to move Chap right.
	 * The move may not be attempted immediately.
	 * @return if move was valid or not.
	 */
	public boolean moveRight() {
		return false;
	}
	
	/**
	 * Exits the game without saving.
	 * The game should resume at the last unfinished level.
	 */
	public void exit() {
		
	}
	
	/**
	 * Exits the game and saves the latest state into a file.
	 * The game can be resumed later on.
	 */
	public void exitAndSave() {
		
	}
	
	/**
	 * Loads a file to be replayed. After calling this method, the game should be in a 'replay' state.
	 * @param f
	 */
	public void loadReplay(File f) {
		
	}
	
	/**
	 * Loads a new game at level 1.
	 */
	public void loadLevel1() {
		
	}
	
	/**
	 * Loads a new game at level 2.
	 */
	public void loadLevel2() {
		
	}
	
	/**
	 * Pauses the game loop and sets the Controller to refuse all requests other than resumeGame().
	 */
	public void pauseGame() {
		
	}
	
	/**
	 * Resumes the game loop if it is paused.
	 */
	public void resumeGame() {
		
	}
	
}
