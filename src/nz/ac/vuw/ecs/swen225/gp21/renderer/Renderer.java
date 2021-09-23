package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
/**
 * This is the renderer interface for interacting with other modules.
 * @author mengli 300525081
 *
 */
public interface Renderer {
	/**
	 * Set the domain and level of game
	 */
	public void init(Domain domain,int level);
	/**
	 * Inform renderer that game stopped
	 */
	public void gameStopped();
	/**
	 * Redraw the world
	 */
	public void redraw(Domain domain);
}
