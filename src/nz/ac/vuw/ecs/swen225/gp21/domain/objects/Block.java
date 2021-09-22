package nz.ac.vuw.ecs.swen225.gp21.domain.objects;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.domain.controllers.NoMovement;

/**
 * The block is an object that can be pushed by Chip into empty tiles.
 *
 * @author Benjamin
 *
 */
public final class Block extends GameObject {

  /**
   * Create a new movable block.
   */
  public Block() {
    super(new NoMovement(), "block.png", "block.png");
  }

  @Override
  public boolean canEntityGoOnTile(GameObject entity) {
    if (entity instanceof Chip) { // Only chip can enter the tile the block is on
      Coord dest = getNextLocation(entity);
      if (!currentTile.board.isCoordValid(dest)) {
        return false;
      }
      Tile t = currentTile.board.getTileAt(dest);
      // provided the block itself can move in the direction Chip is trying to push it
      this.dir = entity.dir;
      // borrow the direction from entity, incase the terrain type needs to
      // know where we are going (it usually does)
      boolean answer = t.canEntityGoOnTile(this);
      this.dir = Direction.NONE;
      return answer;
      // return currentTile.canOccupierMove(entity.dir);
    }
    return false;
  }

  @Override
  public void entityEnteredTile(GameObject entity) {
    // chip entered the tile, move the block to the next square
    currentTile.board.getWorld().objectPushed();
    switch (entity.dir) {
      case NORTH:
        currentTile.board.getWorld().moveUp(this);
        break;
      case EAST:
        currentTile.board.getWorld().moveRight(this);
        break;
      case SOUTH:
        currentTile.board.getWorld().moveDown(this);
        break;
      case WEST:
        currentTile.board.getWorld().moveLeft(this);
        break;
      default:
        throw new RuntimeException(
            "Unknown direction for block: " + entity.dir + " | " + entity.toString());
    }
  }

  @Override
  public void update(double elapsedTime, World w) {
    this.controller.update(w, elapsedTime).execute(w);
  }

  @Override
  public void doneMoving() {
    this.dir = Direction.NONE;
  }

  @Override
  public String getName() {
    return getClass().getSimpleName();
  }

  @Override
  public char boardChar() {
    return '=';
  }

  /**
   * Helper method to get the tile the block will move to if it is pushed by an
   * entity.
   *
   * @param o the object that will push the block
   * @return the location of the tile the block would move to
   */
  private Coord getNextLocation(GameObject o) {
    if (o.dir == Direction.NONE) {
      throw new RuntimeException("Entity: e[" + o.toString()
          + "] facing NONE direction, cannot move block:[" + o.dir + "] in NONE direction");
    }
    return o.dir.next(currentTile.location);
  }

  @Override
  public String toString() {
    return super.toString() + " " + getClass().getSimpleName();
  }
}
