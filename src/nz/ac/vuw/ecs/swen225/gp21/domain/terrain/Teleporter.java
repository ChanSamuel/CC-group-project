package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;

/**
 * The teleporter is a special tile that will move an entity to the tile one
 * after the link tile in the direction the GameObject was moving. when it is
 * entered. The board loader must ensure the teleporter links (map entries) have
 * been created Example teleport behavior:
 * <p>
 * Before: |_|_|_| |_|O|_| |#|#|#| |_|O|_| |^|C|_| After: |_|C|_| |_|O|_|
 * |#|#|#| |_|O|_| |_|_|_|
 * </p>
 *
 * @author Benjamin
 *
 */
public class Teleporter implements Terrain {
  /**
   * The location of the other teleporter, that this teleporter is linked to.
   */
  private final Coord linkLocation;

  /**
   * Get an instance of the teleporter terrain type.
   *
   * @param other The location this teleporter should send GameObjects to
   * @return instance of teleporter terrain
   */
  public static Teleporter makeInstance(Coord other) {
    return new Teleporter(other);
  }

  /**
   * Teleporter terrain constructor.
   *
   * @param other the location this teleporter will send GameObjects to.
   */
  private Teleporter(Coord other) {
    this.linkLocation = other;
  }

  @Override
  public Terrain nextType(GameObject o) {
    return this;
  }

  @Override
  public Terrain entityEntered(GameObject o) {
    if (o.dir == Direction.NONE) {
      throw new RuntimeException("Cannot teleport object facing NONE direction."
          + " Cannot deduce which square to send object to!");
    }
    Coord destination = o.dir.next(linkLocation);
    return o.getTile().board.getWorld().moveObject(o, destination);
  }

  @Override
  public void undoEntityActions(GameObject o) {
    // currently doing nothing, I think directMove handles this.
  }

  @Override
  public boolean canEntityGoOn(GameObject o) {
    if (o.dir == Direction.NONE) {
      throw new RuntimeException("Cannot teleport object facing NONE direction. "
          + "Cannot deduce which square to send object to!");
    }
    Coord destination = o.dir.next(linkLocation);
    if (!o.getTile().board.isCoordValid(destination)) {
      return false;
    }
    return o.getTile().board.getTileAt(destination).canEntityGoOnTile(o);
  }

  @Override
  public char boardChar() {
    return 'O';
  }

  @Override
  public String toString() {
    return "Teleporter";
  }

}
