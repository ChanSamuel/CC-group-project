package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

/**
 * Transition's are actions performed by the GUI. Their sole purpose is to perform an action which may
 * or may not mutate the GUI in the process.
 * This could mean transitioning to the next page, making a button invisible, or notifying the user that
 * they cannot do something.
 * Transitions can also issue actions.
 * 
 * @author Sam
 *
 */
public interface Transition {
	
	/**
	 * Transforms the given GUI.
	 * @param gui
	 */
	public void transform(GUI gui);
	
}
