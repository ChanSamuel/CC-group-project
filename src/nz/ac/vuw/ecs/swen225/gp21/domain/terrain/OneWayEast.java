package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;

/**
 * One Way right tile.
 *
 * @author Benjamin
 *
 */
public final class OneWayEast extends OneWay {
  /**
   * The instance of one way east
   */
  private static OneWayEast instance = new OneWayEast();

  /**
   * Get an instance of one way east terrain
   *
   * @return incetance of one way east
   */
  public static OneWayEast getInstance() {
    return instance;
  }

  /**
   * Create a one way tile.
   */
  private OneWayEast() {
    super(Direction.EAST);
  }

  @Override
  public char boardChar() {
    return '>';
  }

}
