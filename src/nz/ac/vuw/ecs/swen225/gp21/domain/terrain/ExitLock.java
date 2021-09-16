package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;

/**
 * The exit lock terrain type blocks the player from reaching the exit until all
 * treasure has been collected.
 *
 * @author Benjamin
 *
 */
public class ExitLock implements Terrain {
  /**
   * Store the instance of exit lock terrain here.
   */
  private static ExitLock instance = new ExitLock();

  /**
   * Get an instance of the exit lock terrain type.
   *
   * @return instance of exit lock terrain.
   */
  public static ExitLock getInstance() {
    return instance;
  }

  /**
   * Create exit lock terrain.
   */
  private ExitLock() {
  }

  @Override
  public Terrain nextType(GameObject o) {
    return Free.getInstance();
  }

  @Override
  public void entityEntered(GameObject o) {
    throw new RuntimeException("Entity " + o + " entered the exit lock tile");
  }

  @Override
  public void undoEntityActions(GameObject o) {
    throw new RuntimeException("Entity " + o + " was on the exit lock tile");
  }

  @Override
  public boolean canEntityGoOn(GameObject o) {
    return false;
  } // no objects can enter this type of tile

  @Override
  public char boardChar() {
    return 'X';
  }

  @Override
  public String toString() {
    return "Exit Lock";
  }

}
