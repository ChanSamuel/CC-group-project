package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JPanel;
import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.*;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.*;


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
	
	
	private int id;
	
	public static boolean aFlag = false;
	
	public boolean special = false;
	
	public WorldJPanel wjp;
	
	/**
	 * The constructor
	 * 
	 * @param worldJPanel the parent JPanel
	 */
	ChangingElementsJPanel(WorldJPanel worldJPanel, int id) {
		// set panel properties
		setLayout(null);
		setBounds(0, 0, worldJPanel.getBoard().getWidth()*WorldJPanel.TILE_WIDTH, worldJPanel.getBoard().getHeight()*WorldJPanel.TILE_HEIGHT);
		setVisible(true);
		// initialize images
		initImages();
		// set the board.
		this.board = worldJPanel.getBoard();
		this.wjp = worldJPanel;
		this.id = id;
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
		System.out.println("Changing element ID =  " + this.id);
		if (aFlag) {
			if (!special) {
				System.out.println("Non-Special instance encountered with ID " + this.id);
				//System.out.println("WJP board hash is: "+ wjp.getBoard().hashCode());
				this.board = wjp.getBoard();
			} else {
				System.out.println("SPECIAL instance encountered with ID " + this.id);
				//System.out.println("WJP board hash is: "+ wjp.getBoard().hashCode());
			}
		}
		//NOTE add below would result in strange behavior.
//		super.paintComponent(g);
		System.out.println("Draw the changingTerrain JPanel");
		//iterating through the board, draw image based on Tile's terrain type.
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				Terrain terrain = board.getTileAt(new Coord(j, i)).getTerrain();
				Object object = board.getTileAt(new Coord(j, i)).getOccupier();
				if(terrain instanceof Treasure) {
					//draw the treasure
//					System.out.println("draw treasure here");
//					System.out.println("i: "+i);
//					System.out.println("j: "+j);
					g.drawImage(treasureImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH, WorldJPanel.TILE_HEIGHT,null);
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
				} else if (terrain instanceof SilverDoor) {
					// draw silver Door
					g.drawImage(doorsImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
							WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 0, 0, 32, 32, this);
				} else if (terrain instanceof GoldDoor) {
					// draw gold Door
					g.drawImage(doorsImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
							WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 0, 32, 32, 32+32, this);
				} else if (terrain instanceof GreenDoor) {
					// draw green Door
					g.drawImage(doorsImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
							WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 0, 64, 32, 64+32, this);
				} else if (terrain instanceof CopperDoor) {
					// draw copper Door
					g.drawImage(doorsImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
							WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, 0, 96, 32, 96+32, this);
				} else if (object instanceof Block) {
					// draw block
					g.drawImage(blockImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH, WorldJPanel.TILE_HEIGHT,null);
				} else if (terrain instanceof ExitLock) {
					// draw the exit lock
					g.drawImage(this.exitLockImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
							WorldJPanel.TILE_WIDTH, WorldJPanel.TILE_HEIGHT, null);
				}
			}
		}
	}
}
