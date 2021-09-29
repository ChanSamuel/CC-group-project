package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;

/**
 * a temp JFrame for playing with maze
 * @author limeng7 300525081
 *
 */
public class WorldJFrame extends JFrame{
	public static final int FOCUS_AREA_ROWS = 9;
	public static final int FOCUS_AREA_COLS = 9;
	public static final int MIDDLE_ROWS = FOCUS_AREA_ROWS/2;
	public static final int MIDDLE_COLS = FOCUS_AREA_COLS/2;
	public static final int WIDTH=WorldJPanel.TILE_WIDTH*FOCUS_AREA_COLS;
	public static final int HEIGHT=WorldJPanel.TILE_HEIGHT*FOCUS_AREA_ROWS;
	public WorldJFrame(Domain domain) {
		WrapperJPanel wrapperJPanel = WrapperJPanel.getInstance();
		add(wrapperJPanel);
		wrapperJPanel.addKeyListener(wrapperJPanel);
		wrapperJPanel.setFocusable(true);
		//initialize the wrapperJPanel, set up the domain and level
		wrapperJPanel.init(domain,1);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screen  = tk.getScreenSize();
		int x = screen.width/2-screen.width/4;
		int y = screen.height/2-screen.height/4;
		setLayout(null);
		setBounds(x,y,(int)(screen.getWidth()/2),(int)(screen.getHeight()/2));
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
