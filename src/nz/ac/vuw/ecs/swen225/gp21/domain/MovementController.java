package nz.ac.vuw.ecs.swen225.gp21.domain;

//import nz.ac.vuw.ecs.swen225.gp21.domain.command.Command; //This is a cheeky idea. reuse the Command type to move any entity. 
															//But the problem I forsee is how to deal with GameObjects that get deleted? 

/**
 * The controller interface defines the operations that a controller must be able to perform
 * A controller is a module that encapsulates the logic that a GameObject uses to decide how it will move during a game tick
 * Classes that implement this interface effectively define the AI for this game.
 * In that sense the game offers a fixed number of behaviours to choose from
 * @author Benjamin
 *
 */
public interface MovementController {
//	Decide which direction the GameObject this movement controller is in change of, should move in.
//	@param elapsedTime
//	@return the direction the move 
//	public Direction update(double elapsedTime);
	/**
	 * Generate the command that represents what the object 
	 * this controller watches over wants to perform for this game tick.
	 * @param elapsedTime the time since the last game update - this may be changed in favour for a different game loop that uses fixed time steps
	 * @return the command the gameObject wants to perform
	 */
	public Command update(double elapsedTime);
}
