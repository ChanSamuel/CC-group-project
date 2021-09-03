package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * The Game world can be in one of several states, this interface defines the operations a state 
 * most perform.
 * @author Benjamin
 *
 */
public interface State {
	/**
	 * Simulate the world for one tick
	 * @param w
	 * @param elapsedTime 
	 * @return an object encapsulating all the events that happened during the update
	 */
	public Tick update(World w, double elapsedTime);
	/**
	 * Initialize the world from a level object
	 * @param world the world being initialized
	 * @param level the information it is being initialized with
	 */
	public void loadLevel(World world, Level level);
	/**
	 * Get the width of board
	 * @param w 
	 * @return the height of the world
	 */
	public int getBoardWidth(World w);
	/**
	 * Get the height of the board
	 * @param w
	 * @return the height of the board
	 */
	public int getBoardHeight(World w);
	/**
	 * Determine if a Coordinate is valid
	 * @param w the world the coordinate is being checked with
	 * @param c the coordinate being checked
	 * @return true if the coordinate is valid in the world
	 */
	public boolean isCoordValid(World w, Coord c);
	/**
	 * Add a new Object to the world
	 * @param w the world the object is being added to
	 * @param e the object being added
	 * @param c the location the object is being added at
	 * @return if the object was added successfully
	 */
	public boolean addObject(World w, GameObject e, Coord c);
	/**
	 * Enqueue a move left command for chip
	 * @param w
	 */
	public void moveChipLeft(World w);
	/**
	 * Enqueue a move up command for chip
	 * @param w
	 */
	public void moveChipUp(World w);
	/**
	 * Enqueue a move down command for chip
	 * @param w
	 */
	public void moveChipDown(World w);
	/**
	 * Enqueue a move right command for chip
	 * @param w
	 */
	public void moveChipRight(World w);
	
}
