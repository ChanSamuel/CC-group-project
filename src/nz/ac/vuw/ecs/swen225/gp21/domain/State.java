package nz.ac.vuw.ecs.swen225.gp21.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * The Game world can be in one of several states, this interface defines the
 * operations a state most perform.
 *
 * @author sansonbenj 300482847
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
public interface State {
  /**
   * Simulate the world for one tick.
   *
   * @param w           the world being simulated
   * @param elapsedTime the time since the last update
   * @return an object encapsulating all the events that happened during the
   *         update
   */
  public Tick update(World w, double elapsedTime);

  /**
   * Initialize the world from a level object.
   *
   * @param world the world being initialized
   * @param level the information it is being initialized with
   */
  public void loadLevel(World world, Level level);

  /**
   * Get the width of board.
   *
   * @param w the world chip is in
   * @return the height of the world
   */
  public int getBoardWidth(World w);

  /**
   * Get the height of the board.
   *
   * @param w the world
   * @return the height of the board
   */
  public int getBoardHeight(World w);

  /**
   * Determine if a Coordinate is valid.
   *
   * @param w the world the coordinate is being checked with
   * @param c the coordinate being checked
   * @return true if the coordinate is valid in the world
   */
  public boolean isCoordValid(World w, Coord c);

  /**
   * Add a new Object to the world.
   *
   * @param w the world the object is being added to
   * @param e the object being added
   * @param c the location the object is being added at
   * @return if the object was added successfully
   */
  public boolean addObject(World w, GameObject e, Coord c);

  /**
   * Try to move an object in a direction. When the world is running: If the move
   * succeeds, it is appended to the end of the world's current event. This event
   * is then saved to the update's tick after the object's update method returns.
   *
   * @param w the world the move is being made in
   * @param o the object that is being moved
   * @param d the direction it is being moved in.
   */
  public void makeMove(World w, GameObject o, Direction d);

  /**
   * Enqueue a move left command for chip.
   *
   * @param w the world chip is in
   */
  public void moveChipLeft(World w);

  /**
   * Enqueue a move up command for chip.
   *
   * @param w the world chip is in
   */
  public void moveChipUp(World w);

  /**
   * Enqueue a move down command for chip.
   *
   * @param w the world chip is being moved down in
   */
  public void moveChipDown(World w);

  /**
   * Enqueue a move right command for chip.
   *
   * @param w the world chip is in
   */
  public void moveChipRight(World w);

  /**
   * Get the coord of the tile that the player controlled object is on.
   *
   * @param w the world that chip is in
   * @return coord of Chip
   */
  public Coord getPlayerLocation(World w);

  /**
   * Apply the actions stored in a tick to the world.
   *
   * @param w the world the tick is being applied to
   * @param t the tick being played
   */
  public void forwardTick(World w, Tick t);

  /**
   * Undo the actions stored in the tick.
   *
   * @param w the world the tick is being applied to
   * @param t the tick being undone
   */
  public void backTick(World w, Tick t);

  /**
   * Unpacks the information in the WorldSave and initializes it into the world.
   *
   * @param world the world that is being initialized with save information
   * @param save  the save information
   */
  public void restoreGame(World world, WorldSave save);

  /**
   * Load world instance data into a wrapper object to be saved to disc.
   *
   * @return An object that contains the instance information of the world
   */
  public WorldSave generateSaveData(World w);

}
