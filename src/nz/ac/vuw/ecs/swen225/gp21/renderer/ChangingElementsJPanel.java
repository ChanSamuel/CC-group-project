package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.domain.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Block;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;

/**
 * This is the JPanel which holds all the elements which can move, such as
 * key,treasure.door This will update when chap moves.
 * 
 * @author limeng7 300525081
 *
 */
@SuppressWarnings("serial")
class ChangingElementsJPanel extends JPanel {
	/**
	 * The board object.
	 */
	private Board board;
	/**
	 * The keys image.
	 */
	private BufferedImage keysImage;
	/**
	 * The treasure image.
	 */
	private BufferedImage treasureImage;
	/**
	 * The block image.
	 */
	private BufferedImage blockImage;
	/**
	 * The exit lock image
	 */
	private BufferedImage exitLockImage;
	private volatile static ChangingElementsJPanel changingElementsJPanel = null;

	/**
	 * The constructor, Use singleton pattern so set constructor to private, then it won't get initialized by other classes.
	 */
	private ChangingElementsJPanel() {

	}
	/**
	 * Get the instance of this class, use thread safe lazy initialization.
	 * @return the static instance of this class
	 */
	public static ChangingElementsJPanel getInstance() {
		if(changingElementsJPanel==null) {
			synchronized (ChangingElementsJPanel.class) {
				if(changingElementsJPanel==null) {
					changingElementsJPanel = new ChangingElementsJPanel();
				}
			}
		}
		return changingElementsJPanel;
	}

	/**
	 * initialize this JPanel
	 * @param mainJPanel the mainJPanel
	 */
	void init(MainJPanel mainJPanel) {
		// set panel properties
		setLayout(null);
		setBounds(0, 0, mainJPanel.getBoard().getWidth() * WorldJPanel.TILE_WIDTH,
				mainJPanel.getBoard().getHeight() * WorldJPanel.TILE_HEIGHT);
		setVisible(true);

		// initialize images
		initImages();
		// set the board.
		this.board = mainJPanel.getBoard();
	}

	/**
	 * initialize the images
	 */
	void initImages() {
		try {
			this.keysImage = FileUtil.getBufferedImage("keys.png");
			this.exitLockImage = FileUtil.getBufferedImage("exitLock2.png");
			this.treasureImage = FileUtil.getBufferedImage("treasure.png");
			this.blockImage = FileUtil.getBufferedImage("block.png");
		} catch (IOException e) {
			System.out.println("image loading failed");
			e.printStackTrace();
		}
	}

	/**
	 * Override the paint method.
	 */
	@Override
	public void paintComponent(Graphics g) {
		// NOTE Don't use super, it would result in strange behavior.
//		super.paintComponent(g);
//		System.out.println("Draw the changingTerrain JPanel");
		// iterating through the board, draw image based on Tile's terrain type.
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				Terrain terrain = board.getTileAt(new Coord(j, i)).getTerrain();
				Object object = board.getTileAt(new Coord(j, i)).getOccupier();
				switch(terrain.getClass().getSimpleName()) {
				case "Treasure":
					g.drawImage(treasureImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH, WorldJPanel.TILE_HEIGHT, null);
					break;
				case "SilverKey":
					g.drawImage(keysImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
							WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 0, 0, 80, 80, this);
					break;
				case "GoldKey":
					g.drawImage(keysImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
							WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 80, 0, 80 + 80, 80, this);
					break;
				case "GreenKey":
					g.drawImage(keysImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
							WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 160, 0, 160 + 80, 80, this);
					break;
				case "CopperKey":
					g.drawImage(keysImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
							WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 240, 0, 240 + 80, 80, this);
					break;
				case "ExitLock":
					g.drawImage(this.exitLockImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH, WorldJPanel.TILE_HEIGHT, null);
					break;
				default:
					break;
				}
				if (object instanceof Block) {
					// draw block
					g.drawImage(blockImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH, WorldJPanel.TILE_HEIGHT, null);
				}
			}
		}
	}
}
