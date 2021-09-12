package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tick;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
/**
 * This is the renderer interface for interacting with other modules.
 * @author mengli
 *
 */
public interface Renderer {
	/**
	 * Set the world
	 */
	void setWorld(World world);
	/**
	 * Draw the current level
	 * @param level
	 */
	void drawLevel(Level level);
	/**
	 * draw all the changes that happened in the tick
	 */
	void drawUpdateApplied(Tick t);
	/**
	 * draw all the changes being undone in the tick to support game replays
	 */
	void drawUpdateUndone(Tick t);

}
