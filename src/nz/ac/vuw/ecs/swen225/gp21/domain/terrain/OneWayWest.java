package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;

/**
 * One way left tile.
 *
 * @author sansonbenj 300482847
 *
 */
public final class OneWayWest extends OneWay {
  /**
   * Store the instance of one way west.
   */
  private static OneWayWest instance = new OneWayWest();

  /**
   * Get an instance of one way west.
   *
   * @return an instance of one way west
   */
  public static OneWayWest getInstance() {
    return instance;
  }

  /**
   * create a one way left tile.
   */
  private OneWayWest() {
    super(Direction.WEST);
  }

  @Override
  public char boardChar() {
    return '<';
  }

}
