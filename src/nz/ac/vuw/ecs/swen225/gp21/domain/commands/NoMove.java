package nz.ac.vuw.ecs.swen225.gp21.domain.commands;

import nz.ac.vuw.ecs.swen225.gp21.domain.Command;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;

/**
 * The no move command represents no movement.
 *
 * @author sansonbenj 300482847
 *
 */
public class NoMove implements Command {

  @Override
  public void execute(World w) {
    return;
  }

  @Override
  public void undo(World w) {
    return;
  }

  @Override
  public String toString() {
    return "NO MOVE";
  }
}
