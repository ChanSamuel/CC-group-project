package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * Board interface defines the operations a Chip Challenge board must provide
 * to enable gameplay
 * @author Benjamin
 *
 */
public interface Board {
	/**
	 * Add a new game object to the board during initialization
	 * @param o the object being added
	 * @param location the location the object should be placed at
	 */
	public void addObject(GameObject o, Coord location);
	/**
	 * Get the number of treasure that still needs to be collected
	 * @return the number of treasure that has not been picked up
	 */
	int getRemainingChips();
	/**
	 * Attempt to move a GameObject up one tile
	 * @param o the object the board will try to move
	 */
	void moveUp(GameObject o);
	/**
	 * Attempt to move a GameObject up one tile
	 * @param o the object the board will try to move
	 */
	void moveDown(GameObject o);
	/**
	 * Attempt to move a GameObject up one tile
	 * @param o the object the board will try to move
	 */
	void moveLeft(GameObject o);
	/**
	 * Attempt to move a GameObject up one tile
	 * @param o the object the board will try to move
	 */
	void moveRight(GameObject o);
	/**
	 * Open the exit tile
	 */
	public void openExit();
	/**
	 * Move an object straight to a destination tile. 
	 * Doesn't notify the terrain type of the destination
	 * @param o
	 * @param destination
	 */
	public void moveObject(GameObject o, Coord destination);
}
