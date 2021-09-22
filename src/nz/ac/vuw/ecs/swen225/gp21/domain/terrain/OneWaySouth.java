package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;

/**
 * The one way south terrain can only be travesed in a downwards direction of
 * travel.
 *
 * @author Benjamin
 *
 */
public final class OneWaySouth extends OneWay {
  /**
   * Store an instance of one way south.
   */
  private static OneWaySouth instance = new OneWaySouth();

  /**
   * Get an instance of one way south.
   *
   * @return an instance of one way south terrain
   */
  public static OneWaySouth getInstance() {
    return instance;
  }

  /**
   * Create a new one way south terrain.
   */
  private OneWaySouth() {
    super(Direction.SOUTH);
  }

  @Override
  public char boardChar() {
    return 'v';
  }

}
