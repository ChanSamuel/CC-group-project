package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JPanel;
import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.*;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.*;

/**
 * This is the JPanel which holds all the elements which can move, such as
 * key,treasure.door This will update when chap moves.
 * 
 * @author mengli 300525081
 *
 */
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
	 * The dooors image.
	 */
	private BufferedImage doorsImage;
	/**
	 * The exit lock image
	 */
	private BufferedImage exitLockImage;
	private volatile static ChangingElementsJPanel changingElementsJPanel = null;

	/**
	 * The constructor
	 * 
	 * @param worldJPanel the parent JPanel
	 */

	private ChangingElementsJPanel() {

	}

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
	 */
	void init(WorldJPanel worldJPanel) {
		// set panel properties
		setLayout(null);
		setBounds(0, 0, worldJPanel.getBoard().getWidth() * WorldJPanel.TILE_WIDTH,
				worldJPanel.getBoard().getHeight() * WorldJPanel.TILE_HEIGHT);
		setVisible(true);
		// initialize images
		initImages();
		// set the board.
		this.board = worldJPanel.getBoard();
	}

	/**
	 * initialize the images
	 */
	void initImages() {
		try {
			this.keysImage = FileUtil.getBufferedImage("keys.png");
			this.doorsImage = FileUtil.getBufferedImage("door.png");
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
		// NOTE add below would result in strange behavior.
//		super.paintComponent(g);
//		System.out.println("Draw the changingTerrain JPanel");
		// iterating through the board, draw image based on Tile's terrain type.
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				Terrain terrain = board.getTileAt(new Coord(j, i)).getTerrain();
				Object object = board.getTileAt(new Coord(j, i)).getOccupier();
				if (terrain instanceof Treasure) {
					// draw the treasure
//					System.out.println("draw treasure here");
//					System.out.println("i: "+i);
//					System.out.println("j: "+j);
					g.drawImage(treasureImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH, WorldJPanel.TILE_HEIGHT, null);
				} else if (terrain instanceof SilverKey) {
					// draw silver key
					g.drawImage(keysImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
							WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 0, 0, 80, 80, this);
				} else if (terrain instanceof GoldKey) {
					// draw gold key
					g.drawImage(keysImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
							WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 80, 0, 80 + 80, 80, this);
				} else if (terrain instanceof GreenKey) {
					// draw green key
					g.drawImage(keysImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
							WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 160, 0, 160 + 80, 80, this);
				} else if (terrain instanceof CopperKey) {
					// draw copper key
					g.drawImage(keysImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
							WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 240, 0, 240 + 80, 80, this);
				} else if (terrain instanceof ExitLock) {
					// draw the exit lock
					g.drawImage(this.exitLockImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH, WorldJPanel.TILE_HEIGHT, null);
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
