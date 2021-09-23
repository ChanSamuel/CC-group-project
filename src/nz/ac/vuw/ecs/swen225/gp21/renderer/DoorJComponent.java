package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JComponent;

import nz.ac.vuw.ecs.swen225.gp21.domain.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.CopperDoor;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.GoldDoor;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.GreenDoor;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.SilverDoor;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;

/**
 * This is the door JComponent, update when door unlocked.
 * 
 * @author mengli 300525081
 *
 */
public class DoorJComponent extends JComponent {
	protected WorldJPanel worldJPanel;
	private BufferedImage doorsImage;
	protected Map<Coord,Terrain> doorMap;
	protected int currentI;
	protected int currentJ;
	protected int offSet;
	protected boolean currentRunning;
	protected Terrain currentTerrain = null;

	/**
	 * The constructor
	 */
	public DoorJComponent(WorldJPanel worldJPanel) {
		this.worldJPanel = worldJPanel;
		setVisible(true);
		setOpaque(false);
		setBounds(0, 0,this.worldJPanel.getBoard().getWidth()*WorldJPanel.TILE_WIDTH, this.worldJPanel.getBoard().getHeight()*WorldJPanel.TILE_HEIGHT);
		try {
			this.doorsImage = FileUtil.getBufferedImage("door.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		doorMap = new ConcurrentHashMap<>();
		for (int i = 0; i < worldJPanel.getBoard().getWidth(); i++) {
			for (int j = 0; j < worldJPanel.getBoard().getHeight(); j++) {
				Terrain terrain = worldJPanel.getBoard().getTileAt(new Coord(j, i)).getTerrain();
				if (terrain instanceof SilverDoor||terrain instanceof GoldDoor||terrain instanceof GreenDoor||terrain instanceof CopperDoor){
					doorMap.put(new Coord(j,i),terrain);
				}
			}
		}
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
		// set the board.
		Board board = worldJPanel.getBoard();
		// iterating through the board, draw image based on Tile's terrain type.
		for(Coord coord:doorMap.keySet()) {
			int i = coord.getCol();
			int j = coord.getRow();
			if(this.worldJPanel.playerCoord.getCol()==i&&this.worldJPanel.playerCoord.getRow()==j&&doorMap.containsKey(coord)) {
				System.out.println("chap stands on silver door");
				currentI = coord.getCol();
				currentJ = coord.getRow();
				currentTerrain  = doorMap.get(coord);
				this.doorMap.remove(coord);
				if(!this.currentRunning) doorOpen();
			}
			drawDoor(g,doorMap.get(coord),0,i,j);
		}
		drawDoor(g,currentTerrain,offSet,currentI,currentJ);
	}
	private void drawDoor(Graphics g, Terrain terrain, int offset,int i,int j) {
		if (terrain instanceof SilverDoor) {
			// draw silver Door
			g.drawImage(doorsImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
					WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
					WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, offset, 0, 32+offset, 32, this);
		} else if (terrain instanceof GoldDoor) {
			// draw gold Door
			g.drawImage(doorsImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
					WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
					WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, offset, 32, 32+offset, 32 + 32, this);
		} else if (terrain instanceof GreenDoor) {
			// draw green Door
			g.drawImage(doorsImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
					WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
					WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, offset, 64, 32+offset, 64 + 32, this);
		} else if (terrain instanceof CopperDoor) {
			// draw copper Door
			g.drawImage(doorsImage, WorldJPanel.TILE_WIDTH * i, WorldJPanel.TILE_HEIGHT * j,
					WorldJPanel.TILE_WIDTH * i + WorldJPanel.TILE_WIDTH,
					WorldJPanel.TILE_HEIGHT * j + WorldJPanel.TILE_HEIGHT, offset, 96, 32+offset, 96 + 32, this);
		}
	}
}

/**
 * This is a subclass used for implement the door animation.
 * @author mengli
 *
 */
class DoorMoving extends Thread {
	private DoorJComponent doorJComponent;
	public DoorMoving(DoorJComponent doorJComponent) {
		this.doorJComponent = doorJComponent;
	}
	@Override
	public void run() {
		while(this.doorJComponent.offSet<128) {
			System.out.println("Current offset: "+this.doorJComponent.offSet);
			this.doorJComponent.currentRunning = true;
			this.doorJComponent.offSet+=32;
			this.doorJComponent.repaint();
			try {
				sleep(80);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.doorJComponent.currentRunning = false;
		this.doorJComponent.offSet=0;
		this.doorJComponent.currentTerrain = null;
	}
}
