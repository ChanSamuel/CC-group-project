package nz.ac.vuw.ecs.swen225.gp21.renderer;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.domain.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.Chip;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;

/**
 * The worldJPanel provides the main interface of the renderer package, for other modules to interact with.
 * @author mengli
 *
 */
public class WorldJPanel extends JPanel{
	/**
	 * tile width
	 */
	final static int TILE_WIDTH = 40;
	/**
	 * tile height
	 */
	final static int TILE_HEIGHT = 40;
	/**
	 * The Board.
	 */
	private Board board;
	/**
	 * The world object, including all info of this game world
	 */
	private World w;
	/**
	 * The chap
	 */
	private Chip chap;
	/**
	 * The chap JPanel
	 */
	private ChapJPanel ChapJPanel;
	/**
	 * The changingTerrainJPanel, this is the JPanel holding those changing terrain types.
	 */
	private ChangingTerrainJPanel changingTerrainJPanel;
	/**
	 * Constructor
	 */
	public WorldJPanel(World w) {
		//-------- Set the world and board ----------------------------------------------
				this.w = w;
				this.board = w.getBoard();
		//-------- Set the properties of this JPanel -----------------------------------
		setLayout(null);
		setVisible(true);
		setBounds(0,0,WorldJFrame.WIDTH,WorldJFrame.HEIGHT);
		//-------- Start creating elements on this JPanel--------------------------------
		//The background JPanel
		BackgroundJPanel backgroundJPanel = new BackgroundJPanel(this);
		//The changingTerrain JPanel
		this.changingTerrainJPanel = new ChangingTerrainJPanel(this);
		//The chap JPanel
		chap = w.getPlayer();
		this.ChapJPanel = new ChapJPanel(this);
		//--------- Create a layered pane and add elements to this pane ------------------
		JLayeredPane lp = new JLayeredPane();
		int index = 1;
		lp.setLayout(null);
		//arrange the layer, smaller index on top.
		lp.add(ChapJPanel,index++);
		lp.add(changingTerrainJPanel,index++);
		lp.add(backgroundJPanel,1000);
		lp.setVisible(true);
		lp.setBounds(0, 0, WorldJFrame.WIDTH,WorldJFrame.HEIGHT);
		//add this JPanel to worldJPanel.
		add(lp);
	}
	/**
	 * update the panel, once chap moves
	 */
	void updateJPanel() {
		//-------------Update all the changed JPanels---------------------
		//pass the new coord and direction here to update chap's location
		this.ChapJPanel.updateChap();
		//repaint the changingTerrainJPanel.
		this.changingTerrainJPanel.repaint();
		
		//calculate the offset of chap's coord from center of the board.
		int diffX = TILE_WIDTH*((WorldJFrame.FOCUS_AREA_COLS-1)/2-chap.getCurrentTile().getCoord().getCol());
		int diffY = TILE_HEIGHT*((WorldJFrame.FOCUS_AREA_ROWS-1)/2-chap.getCurrentTile().getCoord().getRow());
		//change the location of panel to place chap in the center.
		setBounds(diffX,diffY,WorldJFrame.WIDTH,WorldJFrame.HEIGHT);
	}
	/**
	 * Get the board
	 */
	Board getBoard() {
		return this.board;
	}
	/**
	 * Get the chap
	 */
	Chip getChap() {
		return this.chap;
	} 
	
}
