package nz.ac.vuw.ecs.swen225.gp21.domain;

//import nz.ac.vuw.ecs.swen225.gp21.domain.command.Command; //This is a cheeky idea. reuse the Command type to move any entity. 
															//But the problem I forsee is how to deal with GameObjects that get deleted? 

/**
 * The controller interface defines the operations that a controller must be able to perform
 * A controller is a module that encapsulates the logic that a GameObject uses to decide how it will move during a game tick
 * @author Benjamin
 *
 */
public interface MovementController {
//	public Command update(double elapsedTime);
	/**
	 * Decide which direction the GameObject this movement controller is in change of, should move in.
	 * @param elapsedTime
	 * @return the direction the move 
	 */
	public Command update(double elapsedTime);
}
