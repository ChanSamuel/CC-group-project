package nz.ac.vuw.ecs.swen225.gp21.renderer;

import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp21.domain.World;

/**
 * A temp main function for play with maze.
 * 
 * @author mengli
 *
 */
public class tempMain {
	public static void main(String arg[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				World world = new World();
				WorldJFrame worldJFrame = new WorldJFrame(world);
			}
		});
	}
}
