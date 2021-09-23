package nz.ac.vuw.ecs.swen225.gp21.domain.controllers;

import nz.ac.vuw.ecs.swen225.gp21.domain.Command;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.MovementController;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.NoMove;

/**
 * The controller is used to control the player entity It works by getting the
 * latest movement command from the player movement command queue It is package
 * private.
 *
 * @author sansonbenj 300482847
 *
 */
public final class PlayerController implements MovementController {

  @Override
  public Command update(World w, GameObject o, double elapsedTime) {
    Command c = w.poll();
    if (c == null) {
      c = new NoMove();
      w.event.saveEvent(c);
    }
    return c;
  }

}
