package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
/**
 * This is the renderer interface for interacting with other modules.
 * It has an init method which takes domain and current level as parameters
 * and will initialize all the JPanels.
 * it offers three functions (gameStopped, gamePaused,gameResumed) to be called when related 
 * event occurs, and then it will stop, pause or resume the background music.
 * sound effects got played by APP directly call a static method playSound() in WrapperJPanel, not here.
 * @author limeng7 300525081
 *
 */
public interface Renderer {
	/**
	 * Set the domain and level of game, 
	 * it will build and initialize all the JPanels, 
	 * @param domain 
	 * @param level an int, in this case, only two levels, used for play level based music.
	 */
	public void init(Domain domain,int level);
	/**
	 * Get called when game stopped, it will the stop the background music and close the clip
	 */
	public void gameStopped();
	/**
	 * Get called when game paused, it will the pause the background music.
	 */
	public void gamePaused();
	/**
	 * Get called when game stopped, it will the resume the background music from where it paused.
	 */
	public void gameResumed();
	/**
	 * Redraw the world, it will get called on each update, 
	 * it will draw the auto moving element on each update, 
	 * and draw the other JPanels when chap moves.
	 * @param domain 
	 */
	public void redraw(Domain domain);
}
