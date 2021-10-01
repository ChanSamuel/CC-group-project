package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Monster;

/**
 * This is the monsterJComponent JPanel draws the second actor, 
 * it will iterate through all the tiles of the board to find the monster tile, and then 
 * get the inputstream(left and right) to generate an image of the second actor.
 * it will got drawn on every update, (unlike other elements, which will only get drawn when chap moves.)
 * @author limeng7 300565081
 */
@SuppressWarnings("serial")
public class MonsterJComponent extends JComponent {
	private MainJPanel mainJPanel;
	private volatile static MonsterJComponent monsterJComponent = null;
	private Monster monster;
	private BufferedImage leftDragonImage;
	private BufferedImage rightDragonImage;
	/**
	 * The constructor, Use singleton pattern so set constructor to private, then it
	 * won't get initialized by other classes.
	 */
	private MonsterJComponent() {

	}

	/**
	 * Get the instance of this class, use thread safe lazy initialization.
	 * 
	 * @return the static instance of this class
	 */
	public static MonsterJComponent getInstance() {
		if (monsterJComponent == null) {
			synchronized (MonsterJComponent.class) {
				monsterJComponent = new MonsterJComponent();
			}
		}
		return monsterJComponent;
	}
	/**
	 * initialize this JPanel, it will set the panel properties, get the monster object, 
	 * and read the image from inputstream.
	 * @param mainJPanel
	 */
	public void init(MainJPanel mainJPanel) {
//		if(mainJPanel.getLevel()==1)return;
		this.mainJPanel = mainJPanel;
		// set panel properties
		setLayout(null);
		setBounds(0, 0, mainJPanel.getBoard().getWidth() * WorldJPanel.TILE_WIDTH,
				mainJPanel.getBoard().getHeight() * WorldJPanel.TILE_HEIGHT);
		setVisible(true);
		for (int i = 0; i < this.mainJPanel.getBoard().getWidth(); i++) {
			for (int j = 0; j < this.mainJPanel.getBoard().getHeight(); j++) {
				Object object = this.mainJPanel.getBoard().getTileAt(new Coord(j, i)).getOccupier();
				if (object instanceof Monster) {
					monster = (Monster) object;
				}
			}
		}
		if(monster==null) return;
		if(monster.leftStream==null||monster.rightStream==null) {
			throw new RuntimeException("inputStream is null");
		}
		InputStream ls = monster.leftStream;
		try {
			leftDragonImage = ImageIO.read(ls);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream rs = monster.rightStream;
		try {
			rightDragonImage = ImageIO.read(rs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(leftDragonImage==null||rightDragonImage==null) {
			throw new RuntimeException("left dragon image or right dragon image is null");
		}
	}
	/**
	 * This will draw dragon facing different directions based on the dragon's direction.
	 * 
	 */
	@Override
	public void paintComponent(Graphics g) {
		if(monster==null) return;
//		super.paintComponent(g);
//		System.out.println("draw dragon col= " + monster.currentTile.location.getColumn() + "row = "
//				+ monster.currentTile.location.getRow());
		if (monster.dir == Direction.WEST||monster.dir == Direction.NORTH) {
//				System.out.println("draw dragon left col= " + monster.currentTile.location.getColumn() + "row = "
//						+ monster.currentTile.location.getRow());
				g.drawImage(leftDragonImage,
						WorldJPanel.TILE_WIDTH * monster.getTile().location.getColumn(),
						WorldJPanel.TILE_HEIGHT * monster.getTile().location.getRow(), WorldJPanel.TILE_WIDTH,
						WorldJPanel.TILE_HEIGHT, null);
			
		} else if (monster.dir == Direction.EAST||monster.dir == Direction.SOUTH) {
//				System.out.println("draw dragon right col= " + monster.currentTile.location.getColumn() + "row = "
//						+ monster.currentTile.location.getRow());
				g.drawImage(rightDragonImage,
						WorldJPanel.TILE_WIDTH * monster.getTile().location.getColumn(),
						WorldJPanel.TILE_HEIGHT * monster.getTile().location.getRow(), WorldJPanel.TILE_WIDTH,
						WorldJPanel.TILE_HEIGHT, null);
		}
	}
}

