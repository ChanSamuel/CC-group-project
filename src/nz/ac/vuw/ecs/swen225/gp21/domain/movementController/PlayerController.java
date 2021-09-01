package nz.ac.vuw.ecs.swen225.gp21.domain.movementController;

import nz.ac.vuw.ecs.swen225.gp21.domain.Command;
import nz.ac.vuw.ecs.swen225.gp21.domain.MovementController;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.NoMove;

/**
 * The controller is used to control the player entity
 * It works by getting the latest movement command from the player movement command queue
 * It is package private
 * @author Benjamin
 *
 */
public final class PlayerController implements MovementController {
	/**
	 * A reference back to the world 
	 * Used by the controller to cause movements to occur
	 */
	private final World w;
	/**
	 * Create a new Player controller.
	 * @param w the world which the entities this controller may move exists in 
	 */
	public PlayerController(World w){ this.w = w; }
	
	@Override
	public Command update(double elapsedTime) {
		Command c = w.poll();
		return c != null ? c : new NoMove();
	}

}
