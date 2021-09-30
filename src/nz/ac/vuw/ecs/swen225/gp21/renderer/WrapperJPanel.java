package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;

/**
 * This is the JPanel used for the implementation of focus area
 * 
 * @author limeng7 300525081
 *
 */
@SuppressWarnings("serial")
public class WrapperJPanel extends JPanel implements Renderer {
	public static final int FOCUS_AREA_ROWS = 9;
	public static final int FOCUS_AREA_COLS = 9;
	public static final int WIDTH = WorldJPanel.TILE_WIDTH * FOCUS_AREA_COLS;
	public static final int HEIGHT = WorldJPanel.TILE_HEIGHT * FOCUS_AREA_ROWS;
	private WorldJPanel worldJPanel;
	private BufferedImage tileImage;
	private static volatile WrapperJPanel wrapperJPanel = null;
	/**
	 * The constructor, Use singleton pattern so set constructor to private, then it
	 * won't get initialized by other classes.
	 */
	private WrapperJPanel() {
		worldJPanel = WorldJPanel.getInstance();
		add(worldJPanel);
		setFocusable(true);
		setLayout(null);
		setBounds(0, 0, WIDTH, HEIGHT);
		setVisible(true);
		initImage();
	}
	private void initImage() {
		try {
			this.tileImage = FileUtil.getBufferedImage("tiles.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Get the instance of this class, use thread safe lazy initialization.
	 * 
	 * @return the static instance of this class
	 */
	public static WrapperJPanel getInstance() {
		if(wrapperJPanel==null) {
			synchronized (WrapperJPanel.class) {
				if(wrapperJPanel==null) {
					wrapperJPanel = new WrapperJPanel();
				}
			}
		}
		return wrapperJPanel;
	}

	@Override
	public void paintComponent(Graphics g) {
		for (int i = 0; i < FOCUS_AREA_COLS; i++) {
			for (int j = 0; j < FOCUS_AREA_ROWS; j++) {
				// draw the grass tile.
				g.drawImage(tileImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
						WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
						WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 383, 30, 383 + 62, 30 + 62, this);
			}
		}
	}
	/**
	 * Play sound effect, this method should be called when event such as pick up a
	 * chip, pick up a key, open the door etc.
	 * @param soundtype an enum, refers to current event.
	 */
	public static void playSound(SoundType soundtype) {
		WorldJPanel.playSound(soundtype);
	}

	// --------------------Methods inherit from Renderer--------
	@Override
	public void redraw(Domain domain) {
		worldJPanel.redraw(domain);
	}

	@Override
	public void init(Domain domain,int level) {
		worldJPanel.init(domain,level);
	}

	@Override
	public void gameStopped() {
		worldJPanel.gameStopped();
	}
	@Override
	public void gameResumed() {
		worldJPanel.gameResumed();
	}
	@Override
	public void gamePaused() {
		worldJPanel.gamePaused();
		
	}
}
