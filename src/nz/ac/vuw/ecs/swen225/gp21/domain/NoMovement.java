package nz.ac.vuw.ecs.swen225.gp21.domain;
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
