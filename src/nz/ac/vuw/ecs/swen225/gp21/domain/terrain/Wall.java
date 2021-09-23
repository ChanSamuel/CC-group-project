package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;

/**
 * The wall terrain type does not allow anyone past.
 *
 * @author sansonbenj 300482847
 *
 */
public final class Wall implements Terrain {
  /**
   * Hold the sole instance of wall, to save memory.
   */
  private static Wall instance = new Wall();

  /**
   * Get the instance of the wall terrain.
   *
   * @return instance of wall terrain
   */
  public static Wall getInstance() {
    return instance;
  }

  /**
   * wall doesn't need any initialization.
   */
  private Wall() {
  }

  @Override
  public Terrain nextType(GameObject o) {
    return this;
  }

  @Override
  public Terrain entityEntered(GameObject o) {
    throw new RuntimeException("Entity: [" + o + "] entered a wall tile!");
  }

  @Override
  public void undoEntityActions(GameObject o) {
    throw new RuntimeException("Entity: [" + o + "] was in a wall tile!");
  }

  @Override
  public boolean canEntityGoOn(GameObject o) {
    return false;
  }

  @Override
  public char boardChar() {
    return '#';
  }

  @Override
  public String toString() {
    return "Wall";
  }
}
