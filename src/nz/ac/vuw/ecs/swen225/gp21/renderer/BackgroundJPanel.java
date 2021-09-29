package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.domain.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;

/**
 * This is the backgound JPanel which draws the tiles and walls, exit tiles,
 * exit locks and teleporter. This panel won't repaint() when chap moves.;
 * 
 * @author limeng7 300525081
 *
 */
@SuppressWarnings("serial")
class BackgroundJPanel extends JPanel {
	private Board board;
	private BufferedImage tileImage;
	private BufferedImage telePorterImage;
	private BufferedImage exitTileImage;
	private BufferedImage oneWayEastImage;
	private BufferedImage oneWayWestImage;
	private BufferedImage oneWayNorthImage;
	private BufferedImage oneWaySouthImage;
	private BufferedImage infoImage;
	/**
	 * The padding for one way tiles
	 */
	private final int PADDING = 10;
	private static volatile BackgroundJPanel backgroundJPanel = null;

	/**
	 * The constructor, set to private so it won't get initialized.
	 */
	private BackgroundJPanel() {

	}
	
	/**
	 * Get the instance
	 * @return an instance of current class
	 */
	public static BackgroundJPanel getInstance() {
		if (backgroundJPanel == null) {
			synchronized (BackgroundJPanel.class) {
				if (backgroundJPanel == null) {
					backgroundJPanel = new BackgroundJPanel();
				}
			}
		}
		return backgroundJPanel;
	}
	
	/**
	 * Set the board
	 * @param mainJPanel the main JPanel which holds all the data from other modules.
	 */
	void init(MainJPanel mainJPanel) {
		// Set the board
		this.board = mainJPanel.getBoard();
		//Set the properties of this JPanel
		setLayout(null);
		setBounds(0, 0, this.board.getWidth() * WorldJPanel.TILE_WIDTH,
				this.board.getHeight() * WorldJPanel.TILE_HEIGHT);
		setVisible(true);
		//Initialize images
		initImages();
	}

	/**
	 * initialize the pictures
	 */
	private void initImages() {
		try {
			this.tileImage = FileUtil.getBufferedImage("tiles.png");
			this.telePorterImage = FileUtil.getBufferedImage("teleporter.png");
			this.infoImage = FileUtil.getBufferedImage("info.png");
			this.exitTileImage = FileUtil.getBufferedImage("exitTile.png");
			this.oneWayEastImage = FileUtil.getBufferedImage("oneWayEast.png");
			this.oneWayWestImage = FileUtil.getBufferedImage("oneWayWest.png");
			this.oneWayNorthImage = FileUtil.getBufferedImage("oneWayNorth.png");
			this.oneWaySouthImage = FileUtil.getBufferedImage("oneWaySouth.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		// iterating through the board, draw image based on Tile's terrain type.
		super.paintComponent(g);
//		System.out.println("Draw the background JPanel");
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				Terrain terrain = board.getTileAt(new Coord(j, i)).getTerrain();
				// draw the grass tile.
				g.drawImage(tileImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
						WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
						WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 383, 30, 383 + 62, 30 + 62, this);
				switch (terrain.getClass().getSimpleName()){
				case "Wall":
					g.drawImage(tileImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
							WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 320, 30, 320 + 62, 30 + 62, this);
					break;
				case "Teleporter":
					g.drawImage(this.telePorterImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH, WorldJPanel.TILE_HEIGHT, null);
					break;
				case "Info":
					g.drawImage(this.infoImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH, WorldJPanel.TILE_HEIGHT, null);
					break;
				case "OneWayEast":
					g.drawImage(this.oneWayEastImage, WorldJPanel.TILE_WIDTH * i + PADDING / 2,
							WorldJPanel.TILE_HEIGHT * j + PADDING / 2, WorldJPanel.TILE_WIDTH - PADDING,
							WorldJPanel.TILE_HEIGHT - PADDING, null);
					break;
				case "OneWayWest":
					g.drawImage(this.oneWayWestImage, WorldJPanel.TILE_WIDTH * i + PADDING / 2,
							WorldJPanel.TILE_HEIGHT * j + PADDING / 2, WorldJPanel.TILE_WIDTH - PADDING,
							WorldJPanel.TILE_HEIGHT - PADDING, null);
					break;
				case "OneWayNorth":
					g.drawImage(this.oneWayNorthImage, WorldJPanel.TILE_WIDTH * i + PADDING / 2,
							WorldJPanel.TILE_HEIGHT * j + PADDING / 2, WorldJPanel.TILE_WIDTH - PADDING,
							WorldJPanel.TILE_HEIGHT - PADDING, null);
					break;
				case "OneWaySouth":
					g.drawImage(this.oneWaySouthImage, WorldJPanel.TILE_WIDTH * i + PADDING / 2,
							WorldJPanel.TILE_HEIGHT * j + PADDING / 2, WorldJPanel.TILE_WIDTH - PADDING,
							WorldJPanel.TILE_HEIGHT - PADDING, null);
					break;
				case "ExitTile":
					g.drawImage(this.exitTileImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH, WorldJPanel.TILE_HEIGHT, null);
					break;
				default:
					break;
				}
			}
		}
	}
}
