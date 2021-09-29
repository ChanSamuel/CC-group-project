package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JComponent;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.CopperDoor;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.GoldDoor;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.GreenDoor;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.SilverDoor;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;

/**
 * This is the door JComponent, update when door unlocked.
 * 
 * @author limeng7 300525081
 *
 */
@SuppressWarnings("serial")
public class DoorJComponent extends JComponent {
	protected MainJPanel mainJPanel;
	protected BufferedImage doorsImage;
	protected Map<Coord, Terrain> doorMap;
	protected int currentI;
	protected int currentJ;
	protected int offSet;
	protected boolean currentRunning;
	protected Terrain currentTerrain = null;
	private static volatile DoorJComponent doorJComponent = null;

	/**
	 * The constructor, Use singleton pattern so set constructor to private, then it
	 * won't get initialized by other classes.
	 */
	private DoorJComponent() {
	}

	/**
	 * Init the JPanel
	 * @param mainJPanel the main JPanel which holds all the data from other
	 *                   modules.
	 */
	void init(MainJPanel mainJPanel) {
		this.mainJPanel = mainJPanel;
		setVisible(true);
		setOpaque(false);
		setBounds(0, 0, this.mainJPanel.getBoard().getWidth() * WorldJPanel.TILE_WIDTH,
				this.mainJPanel.getBoard().getHeight() * WorldJPanel.TILE_HEIGHT);
		try {
			this.doorsImage = FileUtil.getBufferedImage("door.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		doorMap = new ConcurrentHashMap<>();
		for (int i = 0; i < mainJPanel.getBoard().getWidth(); i++) {
			for (int j = 0; j < mainJPanel.getBoard().getHeight(); j++) {
				Terrain terrain = mainJPanel.getBoard().getTileAt(new Coord(j, i)).getTerrain();
				if (terrain instanceof SilverDoor || terrain instanceof GoldDoor || terrain instanceof GreenDoor
						|| terrain instanceof CopperDoor) {
					doorMap.put(new Coord(j, i), terrain);
				}
			}
		}
	}

	/**
	 * Get the instance of this class, use thread safe lazy initialization.
	 * 
	 * @return the static instance of this class
	 */
	public static DoorJComponent getInstance() {
		if (doorJComponent == null) {
			synchronized (DoorJComponent.class) {
				if (doorJComponent == null) {
					doorJComponent = new DoorJComponent();
				}
			}
		}
		return doorJComponent;
	}

	/**
	 * start the door open animation
	 */
	public void doorOpen() {
		DoorMoving dm = new DoorMoving(this);
		dm.start();
	}

	/**
	 * Override the paintJComponent method
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// iterating through the board, draw image based on Tile's terrain type.
		for (Coord coord : doorMap.keySet()) {
			int i = coord.getColumn();
			int j = coord.getRow();
			if (this.mainJPanel.getHeroCoord().getColumn() == i && this.mainJPanel.getHeroCoord().getRow() == j
					&& doorMap.containsKey(coord)) {
				System.out.println("chap stands on silver door");
				currentI = coord.getColumn();
				currentJ = coord.getRow();
				currentTerrain = doorMap.get(coord);
				this.doorMap.remove(coord);
				if (!this.currentRunning)
					doorOpen();
			}
			drawDoor(g, doorMap.get(coord), 0, i, j);
		}
		drawDoor(g, currentTerrain, offSet, currentI, currentJ);
	}

	/**
	 * The method for draw a door
	 * @param g       the graphics
	 * @param terrain current terrain
	 * @param offset  offset of the door
	 * @param i       the door's row
	 * @param j       the door's col
	 */
	private void drawDoor(Graphics g, Terrain terrain, int offset, int i, int j) {
		if(terrain==null) return;
		switch (terrain.getClass().getSimpleName()) {
		case "SilverDoor":
			g.drawImage(doorsImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
					WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
					WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, offset, 0, 32 + offset, 32, this);
			break;
		case "GoldDoor":
			g.drawImage(doorsImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
					WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
					WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, offset, 32, 32 + offset, 32 + 32, this);
			break;
		case "GreenDoor":
			g.drawImage(doorsImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
					WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
					WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, offset, 64, 32 + offset, 64 + 32, this);
			break;
		case "CopperDoor":
			g.drawImage(doorsImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
					WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
					WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, offset, 96, 32 + offset, 96 + 32, this);
			break;
		default:
			break;
		}
	}
}

/**
 * This is a subclass used for implement the door animation.
 * @author limeng7 300525081
 *
 */
class DoorMoving extends Thread {
	private DoorJComponent doorJComponent;
	public DoorMoving(DoorJComponent doorJComponent) {
		this.doorJComponent = doorJComponent;
	}
	@Override
	public void run() {
		while(this.doorJComponent.offSet<this.doorJComponent.doorsImage.getWidth()) {
//			System.out.println("Current offset: "+this.doorJComponent.offSet);
			this.doorJComponent.currentRunning = true;
			this.doorJComponent.offSet+=32;
			this.doorJComponent.repaint();
			try {
				sleep(80);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.doorJComponent.currentRunning = false;
		this.doorJComponent.offSet=0;
		this.doorJComponent.currentTerrain = null;
	}
}
