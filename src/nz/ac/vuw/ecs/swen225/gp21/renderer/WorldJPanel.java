package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.domain.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.Chip;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;

/**
 * The worldJPanel provides the main interface of the renderer package, for
 * other modules to interact with.
 * 
 * @author mengli
 *
 */
public class WorldJPanel extends JPanel implements KeyListener {
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
	 * The changingTerrainJPanel, this is the JPanel holding those changing terrain
	 * types.
	 */
	private ChangingTerrainJPanel changingTerrainJPanel;

	/**
	 * Constructor
	 */
	public WorldJPanel(World w) {
		// -------- Set the world,board and chap----------
		this.w = w;
		this.board = w.getBoard();
		this.chap = w.getPlayer();
		// -------- Set the properties of this JPanel------
		setLayout(null);
		setVisible(true);
		//place chap in center of the view.
		updateFocusArea();
		
		// ------- Start creating elements on this JPanel-------
		// The background JPanel
		BackgroundJPanel backgroundJPanel = new BackgroundJPanel(this);
		// The changingTerrain JPanel
		this.changingTerrainJPanel = new ChangingTerrainJPanel(this);
		// The chap JPanel
		this.ChapJPanel = new ChapJPanel(this);
		// ---Create a layered pane and add elements to this pane-------
		JLayeredPane lp = new JLayeredPane();
		int index = 1;
		lp.setLayout(null);
		// arrange the layer, smaller index on top.
		lp.add(ChapJPanel, index++);
		lp.add(changingTerrainJPanel, index++);
		lp.add(backgroundJPanel, 1000);
		lp.setVisible(true);
		lp.setBounds(0, 0, WorldJFrame.WIDTH, WorldJFrame.HEIGHT);
		// add this JPanel to worldJPanel.
		add(lp);
	}

	/**
	 * update the panel, once chap moves
	 */
	void updateJPanel() {
		
		// -------------Update all the changed JPanels---------------------
		chap = w.getPlayer();
		updateFocusArea();
		// update chap's location
		this.ChapJPanel.updateChap();
		// repaint the changingTerrainJPanel.
		this.changingTerrainJPanel.repaint();
		
		this.repaint();
	}

	/**
	 * Update focus area, place chap in the middle
	 */
	void updateFocusArea() {
		// calculate the offset of chap's coord from center of the board.
		int diffX = TILE_WIDTH * ((WorldJFrame.FOCUS_AREA_COLS - 1) / 2 - chap.getCurrentTile().getCoord().getCol());
		int diffY = TILE_HEIGHT * ((WorldJFrame.FOCUS_AREA_ROWS - 1) / 2 - chap.getCurrentTile().getCoord().getRow());
		// change the location of panel to place chap in the center.
		setBounds(diffX, diffY, WorldJFrame.WIDTH, WorldJFrame.HEIGHT);
	}

	// -----------------The getters-------------------------------------
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

	// -------------------The Key listeners------------------------------
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			w.moveChipUp();
			w.update(200);
			System.out.println("move chap up");
			System.out.println("chap's location is: "+w.getPlayer().getCurrentTile().getCoord());
			this.updateJPanel();
			break;
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			w.moveChipDown();
			w.update(200);
			System.out.println("move chap down");
			System.out.println("chap's location is: "+w.getPlayer().getCurrentTile().getCoord());
			this.updateJPanel();
			break;
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			w.moveChipLeft();
			w.update(200);
			System.out.println("move chap left");
			System.out.println("chap's location is: "+w.getPlayer().getCurrentTile().getCoord());
			this.updateJPanel();
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			w.moveChipRight();
			w.update(200);
			System.out.println("move chap right");
			System.out.println("chap's location is: "+w.getPlayer().getCurrentTile().getCoord());
			this.updateJPanel();
			break;
		default:
			break;
		}

	}
}
