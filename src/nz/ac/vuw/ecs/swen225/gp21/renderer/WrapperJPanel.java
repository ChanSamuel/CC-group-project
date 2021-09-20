package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;

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

	public WrapperJPanel(Domain domain) {
		this.domain = domain;
		worldJPanel = new WorldJPanel();
		worldJPanel.setDomain(domain);
		add(worldJPanel);
		setFocusable(true);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screen = tk.getScreenSize();
		int x = screen.width / 2 - WIDTH / 2;
		int y = screen.height / 2 - HEIGHT / 2;
		setLayout(null);
		setBounds(x, y, WIDTH, HEIGHT);
		setVisible(true);
		try {
			this.tileImage = FileUtil.getBufferedImage("tiles.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * APP MODIFICATIONS TO MAKE IT WORK.
	 */
	
	public WrapperJPanel() {
		
	}
	
	public void initialise(Domain domain) {
		this.domain = domain;
		worldJPanel = new WorldJPanel();
		worldJPanel.setDomain(domain);
		add(worldJPanel);
		
		setFocusable(true);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screen = tk.getScreenSize();
		int x = screen.width / 2 - WIDTH / 2;
		int y = screen.height / 2 - HEIGHT / 2;
		setLayout(null);
		setBounds(x, y, WIDTH, HEIGHT);
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
			domain.update(200);
			System.out.println("move chap up");
			System.out.println("chap's location is: " + domain.getPlayerLocation());
			break;
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			domain.moveChipDown();
			domain.update(200);
			System.out.println("move chap down");
			System.out.println("chap's location is: " + domain.getPlayerLocation());
			break;
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			domain.moveChipLeft();
			domain.update(200);
			System.out.println("move chap left");
			System.out.println("chap's location is: " + domain.getPlayerLocation());
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			domain.moveChipRight();
			domain.update(200);
			System.out.println("move chap right");
			System.out.println("chap's location is: " + domain.getPlayerLocation());
			break;
		default:
			break;
		}
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

	// --------------------Methods inherit from Renderer--------
	@Override
	public void redraw(Domain domain) {
		worldJPanel.redraw(domain);
	}

	@Override
	public void setLevel(int level) {
		worldJPanel.setLevel(level);
	}

	@Override
	public void playSound(SoundType soundtype) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDomain(Domain domain) {
		worldJPanel.setDomain(domain);
	}
}
