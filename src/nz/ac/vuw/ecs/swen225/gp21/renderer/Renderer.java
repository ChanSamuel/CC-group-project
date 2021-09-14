package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
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
	 * Set the domain
	 */
	public void setDomain(Domain domain);
	/**
	 * Set the level of game
	 */
	public void setLevel(int level);
	/**
	 * Play sound effect, this method should be called when event such as pick up a chip, pick up a key, open the door etc. 
	 */
	public void playSound(SoundType soundtype);
	/**
	 * Redraw the world
	 */
	public void redraw(Domain domain);
}
