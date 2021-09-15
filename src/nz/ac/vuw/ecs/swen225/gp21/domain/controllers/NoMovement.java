package nz.ac.vuw.ecs.swen225.gp21.domain.controllers;

import nz.ac.vuw.ecs.swen225.gp21.domain.Command;
import nz.ac.vuw.ecs.swen225.gp21.domain.MovementController;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.NoMove;

/**
 * The no movement controller does just that Creates No Move commands.
 *
 * @author Benjamin
 *
 */
public class NoMovement implements MovementController {

  @Override
  public Command update(World w, double elapsedTime) {
    Command response = new NoMove();
    w.event.saveEvent(response);
    return response;
  }

}
