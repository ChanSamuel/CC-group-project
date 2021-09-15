package nz.ac.vuw.ecs.swen225.gp21.domain.commands;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;

/**
 * Encapsulates an instruction to an object up one tile Package-private
 * visibility.
 *
 * @author Benjamin
 *
 */
public class MoveUp extends GameObjectMove {
  /**
   * Create a new move up command.
   *
   * @param o the object being moved by this command
   */
  public MoveUp(GameObject o) {
    super(o);
  }

  // TODO should the command store a reference to the world which it receives from
  // the app?
  // Or should it get the reference when the World executes the command?
  @Override
  public void execute(World w) {
    w.moveUp(moved);
  }

  @Override
  public void undo(World w) {
    w.moveDown(moved);
  }

  @Override
  public String toString() {
    return super.toString() + "UP";
  }
}
