package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JPanel;
import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.*;


/**
 * This is the JPanel which holds all the elements which can move, such as key,treasure.door
 * This will update when chap moves.
 * 
 * @author mengli
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
	 * The constructor
	 * 
	 * @param worldJPanel the parent JPanel
	 */
	ChangingElementsJPanel(WorldJPanel worldJPanel) {
		// set panel properties
		setLayout(null);
		setBounds(0, 0, WorldJFrame.WIDTH, WorldJFrame.HEIGHT);
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
			this.treasureImage = FileUtil.getBufferedImage("treasure.png");
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
		//NOTE add below would result in strange behavior.
//		super.paintComponent(g);
		System.out.println("Draw the changingTerrain JPanel");
		//iterating through the board, draw image based on Tile's terrain type.
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				Terrain terrain = board.getTileAt(new Coord(i, j)).getTerrain();
				if(terrain instanceof Treasure) {
					//draw the treasure
					g.drawImage(this.treasureImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH, WorldJPanel.TILE_HEIGHT, null);
				}else if (terrain instanceof SilverKey) {
					// draw silver key
					g.drawImage(keysImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
							WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 0, 0, 80, 80, this);
				} else if (terrain instanceof GoldKey) {
					// draw gold key
					g.drawImage(keysImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
							WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 80, 0, 80+80, 80, this);
				} else if (terrain instanceof GreenKey) {
					// draw green key
					g.drawImage(keysImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
							WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 160, 0, 160+80, 80, this);
				} else if (terrain instanceof CopperKey) {
					// draw copper key
					g.drawImage(keysImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
							WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 240, 0, 240+80, 80, this);
				} else if (terrain instanceof GoldDoor) {
					//TODO
				}
			}
		}
	}
}
