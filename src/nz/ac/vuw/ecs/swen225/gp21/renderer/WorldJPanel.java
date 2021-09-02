package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;

import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.domain.World;

/**
 * The worldJPanel provides the main interface of the renderer package, for other modules to interact with.
 * @author mengli
 *
 */
public class WorldJPanel extends JPanel{
	/**
	 * tile width
	 */
	final static int TILE_WIDTH = 35;
	/**
	 * tile height
	 */
	final static int TILE_HEIGHT = 35;
	/**
	 * The world object, including all info of this game world
	 */
	private World w;
	/**
	 * Constructor
	 */
	public WorldJPanel(World w) {
		//TODO
		this.w = w;
		//TODO create all JComponents and add to this.
	}
	/**
	 * Override the paint method, draw the world
	 */
	@Override
	public void paint(Graphics g) {
		
	}
}
