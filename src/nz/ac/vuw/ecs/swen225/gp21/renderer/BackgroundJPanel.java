package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.domain.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Teleporter;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.ExitLock;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.ExitTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Wall;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.*;

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
	 * The one way east image
	 */
	private BufferedImage oneWayEastImage;
	/**
	 * The one way west image
	 */
	private BufferedImage oneWayWestImage;
	/**
	 * The one way north image
	 */
	private BufferedImage oneWayNorthImage;
	/**
	 * The one way south image
	 */
	private BufferedImage oneWaySouthImage;
	/**
	 * The parent JPanel
	 */
	private WorldJPanel worldJPanel;
	/**
	 * The padding
	 */
	private final int PADDING = 10;
	/**
	 * The constructor Take the board list as parameter to create the backgound.
	 * 
	 * @param board the board.
	 */
	BackgroundJPanel(WorldJPanel worldJPanel) {
		// ----------------Set the board.------------------------------------
		this.board = worldJPanel.getBoard();
		// ---------------Set the properties of this JPanel------------------
		setLayout(null);
		setBounds(0, 0, this.board.getWidth()*WorldJPanel.TILE_WIDTH, this.board.getHeight()*WorldJPanel.TILE_HEIGHT);
		setVisible(true);
		// ---------------Initialize images----------------------------------
		initImages();
	}

	/**
	 * initialize the pictures
	 */
	private void initImages() {
		try {
			this.tileImage = FileUtil.getBufferedImage("tiles.png");
			this.telePorterImage = FileUtil.getBufferedImage("teleporter.png");
			this.exitLockImage = FileUtil.getBufferedImage("exitLock2.png");
			this.exitTileImage = FileUtil.getBufferedImage("exitTile.png");
			this.oneWayEastImage = FileUtil.getBufferedImage("oneWayEast.png");
			this.oneWayWestImage = FileUtil.getBufferedImage("oneWayWest.png");
			this.oneWayNorthImage = FileUtil.getBufferedImage("oneWayNorth.png");
			this.oneWaySouthImage = FileUtil.getBufferedImage("oneWaySouth.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Override the paint method, for drawing the board.
	 */
	@Override
	public void paintComponent(Graphics g) {
		// iterating through the board, draw image based on Tile's terrain type.
		super.paintComponent(g);
		System.out.println("Draw the background JPanel");
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				Terrain terrain = board.getTileAt(new Coord(j, i)).getTerrain();
				// draw the grass tile.
				g.drawImage(tileImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
						WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
						WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 383, 30, 383 + 62, 30 + 62, this);
				// draw the wall
				if (terrain instanceof Wall) {
					g.drawImage(tileImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
							WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 320, 30, 320 + 62, 30 + 62, this);
				} else if (terrain instanceof Teleporter) {
					// draw the teleporter
					g.drawImage(this.telePorterImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH, WorldJPanel.TILE_HEIGHT, null);
				} else if (terrain instanceof OneWayEast) {
					// draw the one way east
					g.drawImage(this.oneWayEastImage, WorldJPanel.TILE_WIDTH * i+PADDING/2, WorldJPanel.TILE_HEIGHT * j+PADDING/2,
							WorldJPanel.TILE_WIDTH-PADDING, WorldJPanel.TILE_HEIGHT-PADDING, null);
				} else if (terrain instanceof OneWayWest) {
					// draw the one way west
					g.drawImage(this.oneWayWestImage, WorldJPanel.TILE_WIDTH * i+PADDING/2, WorldJPanel.TILE_HEIGHT * j+PADDING/2,
							WorldJPanel.TILE_WIDTH-PADDING, WorldJPanel.TILE_HEIGHT-PADDING, null);
				} else if (terrain instanceof OneWaySouth) {
					// draw the one way south
					g.drawImage(this.oneWaySouthImage, WorldJPanel.TILE_WIDTH * i+PADDING/2, WorldJPanel.TILE_HEIGHT * j+PADDING/2,
							WorldJPanel.TILE_WIDTH-PADDING, WorldJPanel.TILE_HEIGHT-PADDING, null);
				} else if (terrain instanceof OneWayNorth) {
					// draw the one way north
					g.drawImage(this.oneWayNorthImage, WorldJPanel.TILE_WIDTH * i+PADDING/2, WorldJPanel.TILE_HEIGHT * j+PADDING/2,
							WorldJPanel.TILE_WIDTH-PADDING, WorldJPanel.TILE_HEIGHT-PADDING, null);
				} else if (terrain instanceof ExitTile) {
					// draw the exit tile
					g.drawImage(this.exitTileImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH, WorldJPanel.TILE_HEIGHT, null);
				} else if (terrain instanceof ExitLock) {
					// draw the exit lock
					g.drawImage(this.exitLockImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH, WorldJPanel.TILE_HEIGHT, null);
				}
			}
		}
	}
}
