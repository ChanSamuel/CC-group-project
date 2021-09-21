package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;

/**
 * This is the JPanel used for the implementation of focus area
 * 
 * @author mengli
 *
 */
public class WrapperJPanel extends JPanel implements KeyListener, Renderer {
	public static final int FOCUS_AREA_ROWS = 9;
	public static final int FOCUS_AREA_COLS = 9;
	public static final int WIDTH = WorldJPanel.TILE_WIDTH * FOCUS_AREA_COLS;
	public static final int HEIGHT = WorldJPanel.TILE_HEIGHT * FOCUS_AREA_ROWS;
	private Domain domain;
	private WorldJPanel worldJPanel;
	private BufferedImage tileImage;
	/**
	 * Constructor of WrapperJPanel
	 * @param domain
	 */
	public WrapperJPanel() {
		worldJPanel = new WorldJPanel();
		add(worldJPanel);
		setFocusable(true);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screen = tk.getScreenSize();
		setLayout(null);
		setBounds(0, 0, WIDTH, HEIGHT);
		setVisible(true);
		try {
			this.tileImage = FileUtil.getBufferedImage("tiles.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// TODO Those are TEMP key listeners just for testing GUI.
	// -------------------The Key listeners------------------------------
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (domain == null)
			return;
		int code = e.getKeyCode();
		switch (code) {
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			domain.moveChipUp();
			System.out.println("move chap up");
			System.out.println("chap's location is: " + domain.getPlayerLocation());
			break;
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			domain.moveChipDown();
			System.out.println("move chap down");
			System.out.println("chap's location is: " + domain.getPlayerLocation());
			break;
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			domain.moveChipLeft();
			System.out.println("move chap left");
			System.out.println("chap's location is: " + domain.getPlayerLocation());
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			domain.moveChipRight();
			System.out.println("move chap right");
			System.out.println("chap's location is: " + domain.getPlayerLocation());
			break;
		default:
			break;
		}
		domain.update(200);
		redraw(domain);
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
	 * Play sound effect, this method should be called when event such as pick up a chip, pick up a key, open the door etc. 
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
		this.domain = domain;
		worldJPanel.init(domain,level);
		this.repaint();
	}
}
