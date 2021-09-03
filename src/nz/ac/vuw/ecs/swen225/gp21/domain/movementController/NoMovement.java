package nz.ac.vuw.ecs.swen225.gp21.domain.movementController;

import nz.ac.vuw.ecs.swen225.gp21.domain.Command;
import nz.ac.vuw.ecs.swen225.gp21.domain.MovementController;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.NoMove;

/**
 * The no movement controller does just that
 * Creates No Move commands
 * @author Benjamin
 *
 */
public class NoMovement implements MovementController {

	@Override
	public Command update(double elapsedTime) {
		return new NoMove();
	}

}
