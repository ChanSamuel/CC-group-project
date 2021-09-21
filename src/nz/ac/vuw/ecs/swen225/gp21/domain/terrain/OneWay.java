package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;

/**
 * All one way tiles restrict object movement. They all share these common
 * attributes You can enter a one way tile as long as you are not facing its
 * direction. You can only leave a one way tile if you are facing its direction.
 * Note: Be careful of pushing blocks onto one way tiles, they will probably get
 * stuck.
 *
 * @author Benjamin
 *
 */
abstract class OneWay implements Terrain {
  /**
   * The direction the tile can be traversed across.
   */
  protected final Direction dir;

  /**
   * OneWay tiles must be given the direction they can be traversed in.
   *
   * @param d the direction this tile can be traversed in
   */
  protected OneWay(Direction d) {
    this.dir = d;
  }

  @Override
  public Terrain nextType(GameObject o) {
    return this;
  }

  @Override
  public Terrain entityEntered(GameObject o) {
    return this;
  }

  @Override
  public void undoEntityActions(GameObject o) {
  }

  @Override
  public boolean canEntityGoOn(GameObject o) {
    return o.dir != dir.opposite();
  }

  @Override
  public boolean canEntityLeave(GameObject o) {
    return o.dir == dir;
  }

  @Override
  public abstract char boardChar();

  @Override
  public String toString() {
    return "One way tile: " + dir;
  }
}
