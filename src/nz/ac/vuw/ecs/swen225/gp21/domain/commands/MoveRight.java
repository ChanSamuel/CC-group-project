package nz.ac.vuw.ecs.swen225.gp21.domain.commands;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;

/**
 * Encapsulates an instruction to move an object right one tile.
 *
 * @author sansonbenj 300482847
 *
 */
public class MoveRight extends GameObjectMove {
  /**
   * Create a new move right command.
   *
   * @param o the object being moved by this command
   */
  public MoveRight(GameObject o) {
    super(o);
  }

  @Override
  public void execute(World w) {
    w.moveRight(moved);
  }

  @Override
  public void undo(World w) {
    w.moveLeft(moved);
  }

  @Override
  public String toString() {
    return super.toString() + "RIGHT";
  }
}
