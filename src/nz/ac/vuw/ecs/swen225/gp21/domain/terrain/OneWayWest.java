package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;

/**
 * One way left tile.
 *
 * @author Benjamin
 *
 */
public final class OneWayWest extends OneWay {

  private static OneWayWest instance = new OneWayWest();

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
