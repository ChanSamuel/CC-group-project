package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * Board interface defines the operations a Chip Challenge board must provide
 * to enable gameplay
 * @author Benjamin
 *
 */
public interface Board {
	/**
	 * Add a new game object to the board
	 * @param o the object being added
	 * @param location the location the object should be placed at
	 */
	public void addObject(GameObject o, Coord location);
	/**
	 * Get the number of treasure that still needs to be collected
	 * @return the number of treasure that has not been picked up
	 */
	int getRemainingChips();
	
	void moveUp(GameObject o);
	void moveDown(GameObject o);
	void moveLeft(GameObject o);
	void moveRight(GameObject o);
}
