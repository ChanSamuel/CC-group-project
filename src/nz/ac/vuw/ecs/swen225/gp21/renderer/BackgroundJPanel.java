package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.domain.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Teleporter;
import nz.ac.vuw.ecs.swen225.gp21.domain.ExitLock;
import nz.ac.vuw.ecs.swen225.gp21.domain.ExitTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.Terrain;
import nz.ac.vuw.ecs.swen225.gp21.domain.Wall;

/**
 * This is the backgound JPanel which draws the tiles and walls, exit tiles,
 * exit locks and teleporter. This panel won't repaint() when chap moves.;
 * 
 * @author mengli
 *
 */
@SuppressWarnings("serial")
class BackgroundJPanel extends JPanel {
	/**
	 * The board
	 */
	Board board;
	/**
	 * The tiles image
	 */
	BufferedImage tileImage;
	/**
	 * The teleporter image
	 */
	private BufferedImage telePorterImage;
	/**
	 * The exit lock image
	 */
	private BufferedImage exitLockImage;
	/**
	 * The exit tile image
	 */
	private BufferedImage exitTileImage;

	/**
	 * The constructor Take the board list as parameter to create the backgound.
	 * @param board the board.
	 */
	BackgroundJPanel(Board board) {
		setLayout(null);
		setBounds(0, 0, WorldJFrame.WIDTH, WorldJFrame.HEIGHT);
		setVisible(true);
		this.board = board;
		initImages();
	}

	/**
	 * initialize the pictures
	 */
	private void initImages() {
		try {
			this.tileImage = FileUtil.getBufferedImage("tiles.png");
			this.telePorterImage = FileUtil.getBufferedImage("teleporter.png");
			this.exitLockImage = FileUtil.getBufferedImage("exitLock.png");
			this.exitTileImage = FileUtil.getBufferedImage("exitTile.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Override the paint method, for drawing the board.
	 */
	@Override
	public void paint(Graphics g) {
		// iterating through the board, draw image based on Tile type.
		System.out.println("board width" + board.getWidth());
		System.out.println("board height" + board.getHeight());
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				Terrain terrain = board.getTileAt(new Coord(i, j)).getTerrain();
				//draw the grass tile.
				g.drawImage(tileImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
						WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
						WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 383, 30, 383 + 62, 30 + 62, this);
				//draw the wall
				if (terrain instanceof Wall) {
					g.drawImage(tileImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
							WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 320, 30, 320 + 62, 30 + 62, this);
				} else if (terrain instanceof Teleporter) {
				//draw the teleporter
					g.drawImage(this.telePorterImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH, WorldJPanel.TILE_HEIGHT, null);
				} else if (terrain instanceof ExitTile) {
				//draw the exit tile
					g.drawImage(this.exitTileImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH, WorldJPanel.TILE_HEIGHT, null);
				} else if (terrain instanceof ExitLock) {
				//draw the exit lock
					g.drawImage(this.exitLockImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH, WorldJPanel.TILE_HEIGHT, null);
				}
			}
		}
	}

}
