package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
/**
 * This is the renderer interface for interacting with other modules.
 * @author limeng7 300525081
 *
 */
public interface Renderer {
	/**
	 * Set the domain and level of game
	 * @param domain 
	 * @param level an int, in this case, only two levels
	 */
	public void init(Domain domain,int level);
	/**
	 * Inform renderer that game stopped/paused.
	 */
	public void gameStopped();
	/**
	 * Inform renderer that game paused.
	 */
	public void gamePaused();
	/**
	 * Inform renderer that game resumed.
	 */
	public void gameResumed();
	/**
	 * Redraw the world
	 * @param domain 
	 */
	public void redraw(Domain domain);
}
