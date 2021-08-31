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
	public void moveObject(Coord destination, GameObject o);
	/**
	 * Get the tile at a location
	 * Can be used by game objects if they need that information to make a decision
	 * @param location the location of tile being seeked
	 * @return the tile at the location
	 */
	public Tile getTileAt(Coord location);
	/**
	 * Returns the number of columns the board has
	 * @return the number of columns
	 */
	public int getWidth();
	/**
	 * Returns the number of rows the board has
	 * @return the number of rows
	 */
	public int getHeight();
	/**
	 * Determine if a coordinate is valid within the board (i.e. it won't lead to a broken program state)
	 * @param c the coordinate
	 * @return if it is valid in the board
	 */
	public boolean isCoordValid(Coord c);
}
