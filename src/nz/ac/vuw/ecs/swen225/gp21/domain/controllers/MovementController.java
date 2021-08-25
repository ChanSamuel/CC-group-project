package nz.ac.vuw.ecs.swen225.gp21.domain.controllers;

import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;

//import nz.ac.vuw.ecs.swen225.gp21.domain.command.Command; //This is a cheeky idea. reuse the Command type to move any entity

/**
 * The controller interface defines the operations that a controller must be able to perform
 * A controller is a module that encapsulates the logic that a GameObject uses to decide how it will move during a game tick
 * @author Benjamin
 *
 */
public interface MovementController {
//	public Command update(double elapsedTime);
	/**
	 * 
	 * @param elapsedTime
	 * @return the direction the move 
	 */
	public Direction update(double elapsedTime);
}
