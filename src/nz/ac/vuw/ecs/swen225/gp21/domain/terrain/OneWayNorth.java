package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;

/**
 * One way tiles can only be traversed in a single direction So they can only be
 * entered of the object is facing the right direction and they can only be left
 * if facing the right direction.
 *
 * @author Benjamin
 *
 */
public final class OneWayNorth extends OneWay {
  /**
   * Store the instance of one way north
   */
  private static OneWayNorth instance = new OneWayNorth();

  /**
   * Get an instance of one way north
   *
   * @return the instance of one way north
   */
  public static OneWayNorth getInstance() {
    return instance;
  }

  /**
   * Create a new One way North terrain.
   */
  private OneWayNorth() {
    super(Direction.NORTH);
  }

  @Override
  public char boardChar() {
    return '^';
  }
}
