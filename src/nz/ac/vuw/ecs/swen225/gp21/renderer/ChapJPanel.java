package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JPanel;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;

/**
 * The hero chap and block's JPanel
 * 
 * @author mengli 300525081
 *
 */
//NOTE replace JComponent with JPanel, because if chap is a JComponent, then each time when JComponent update, 
//the JPanel it location on will also update, that's not ideal here because 
//chap is a gif, so chap will keep updating which lead to its parent JPanel keep repaint().
class ChapJPanel extends JPanel {
	/**
	 * Chap's image face left
	 */
	private BufferedImage chapImageLeft;
	/**
	 * Chap's image face right
	 */
	private BufferedImage chapImageRight;
	/**
	 * Chap's current image
	 */
	private Image chapImage;
	/**
	 * Chap's coordinates
	 */
	private Coord coord;
	/**
	 * Chap's direction
	 */
	private Direction dir;
	/**
	 * The mainJPanel
	 */
	private MainJPanel mainJPanel;
	/**
	 * The instance of ChapJPanel
	 */
	private volatile static ChapJPanel chapJPanel = null;
	/**
	 * Constructor for chap
	 */
	private ChapJPanel() {

	}
	/**
	 * get the instance of this class
	 */
	/**
	 * init this JPanel
	 */
	public void init(MainJPanel mainJPanel) {
		// -------------Set the coord and dir-----------------------------
		this.mainJPanel = mainJPanel;
		this.coord = mainJPanel.getHeroCoord();
		this.dir = mainJPanel.getBoard().getTileAt(this.coord).getOccupier().dir;

		// -------------Set the properties of this JPanel----------------
		setBounds(0, 0, mainJPanel.getBoard().getWidth() * WorldJPanel.TILE_WIDTH,
				mainJPanel.getBoard().getHeight() * WorldJPanel.TILE_HEIGHT);
		setVisible(true);
		setOpaque(false);
		// -------------Initialize the images-----------------------------
		initImages();
		// default direction set as left.
		chapImage = chapImageLeft;
	}
	/**
	 * Return the instance of this class
	 */
	public static ChapJPanel getInstance() {
		if(chapJPanel==null) {
			synchronized (ChapJPanel.class) {
				if(chapJPanel==null) {
					chapJPanel = new ChapJPanel();
				}
			}
		}
		return chapJPanel;
	}
	/**
	 * initialize the image
	 */
	void initImages() {
		try {
			// use gif and set background transparent will make the parent panel keep
			// repaint(), so use image here instead
			chapImageLeft = FileUtil.getBufferedImage("chap-3-left.gif");
			chapImageRight = FileUtil.getBufferedImage("chap-3-right.gif");
		} catch (IOException e) {
			System.out.println("chap image loading failed");
			e.printStackTrace();
		}
	}


	/**
	 * Override the paint method of chap
	 */
	@Override
	public void paintComponent(Graphics g) {
//		System.out.println("Draw the chap JPanel");
		super.paintComponent(g);
		// update the coord and dir.
		this.coord = mainJPanel.getHeroCoord();
		this.dir = mainJPanel.getBoard().getTileAt(this.coord).getOccupier().dir;
		// if chap's direction change to WEST OR EAST, change the current chapImage,
		// otherwise don't change.
		if (dir == Direction.WEST) {
			chapImage = chapImageLeft;
		} else if (dir == Direction.EAST) {
			chapImage = chapImageRight;
		}
		// NOTE the last parameter couldn't be null, if using gif, if just image, then
		// that's doesn't matter.
//		System.out.println("Chap's current col: "+coord.getCol());
//		System.out.println("Chap's current row: "+coord.getRow());
		g.drawImage(chapImage, coord.getColumn() * WorldJPanel.TILE_WIDTH, coord.getRow() * WorldJPanel.TILE_HEIGHT, WorldJPanel.TILE_WIDTH, WorldJPanel.TILE_HEIGHT, this);
	}
}
