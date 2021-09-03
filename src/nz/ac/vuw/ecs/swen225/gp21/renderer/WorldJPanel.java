package nz.ac.vuw.ecs.swen225.gp21.renderer;

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
	 * The chap Jcomponent
	 */
	private ChapJComponent chapJComponent;
	/**
	 * Constructor
	 */
	public WorldJPanel(World w) {
		//TODO
		this.w = w;
		setLayout(null);
		setVisible(false);
		this.board = w.getBoard();
		//by changing the bounds of current JPanel, moving the focus area.
		setBounds(0,0,WorldJFrame.WIDTH,WorldJFrame.HEIGHT);
		System.out.println(this.board.getWidth());
		setVisible(true);
		chap = w.getPlayer();
		chapJComponent = new ChapJComponent(chap.getCurrentTile().getCoord(),chap.getDir());
		add(chapJComponent);
	}
	/**
	 * update the panel, once chap moves
	 */
	public void updateJPanel(Coord coord,Direction dir) {
		//pass the new coord and direction here to update chap's location
		chapJComponent.updateChap(coord,dir);
		//calculate the offset of chap's coord from center of the board.
		int diffX = TILE_WIDTH*((WorldJFrame.FOCUS_AREA_COLS-1)/2-coord.getCol());
		int diffY = TILE_HEIGHT*((WorldJFrame.FOCUS_AREA_ROWS-1)/2-coord.getRow());
		//change the location of panel to place chap in the center.
		setBounds(diffX,diffY,WorldJFrame.WIDTH,WorldJFrame.HEIGHT);
	}
	
}
