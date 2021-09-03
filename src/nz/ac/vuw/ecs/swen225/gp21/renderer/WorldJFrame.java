package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;

/**
 * a temp JFrame for playing with maze
 * @author mengli
 *
 */
public class WorldJFrame extends JFrame{
	public static final int FOCUS_AREA_ROWS = 9;
	public static final int FOCUS_AREA_COLS = 9;
	public static final int WIDTH=WorldJPanel.TILE_WIDTH*FOCUS_AREA_COLS;
	public static final int HEIGHT=WorldJPanel.TILE_HEIGHT*FOCUS_AREA_ROWS;
	public WorldJFrame(World world) {
		WorldJPanel worldJPanel = new WorldJPanel(world);
		add(worldJPanel);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screen  = tk.getScreenSize();
		int x = screen.width/2-WIDTH/2;
		int y = screen.height/2-HEIGHT/2;
		setLayout(null);
		setBounds(x,y,WIDTH,HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//TODO temp
		worldJPanel.updateJPanel(new Coord(4,2), Direction.EAST);
	}
}
