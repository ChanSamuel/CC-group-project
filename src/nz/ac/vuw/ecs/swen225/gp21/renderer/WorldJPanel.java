package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.domain.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;

/**
 * The worldJPanel provides the main interface of the renderer package, for
 * other modules to interact with.
 * 
 * @author mengli
 *
 */
public class WorldJPanel extends JPanel implements KeyListener, Renderer {
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
	 * The domain object
	 */
	protected Domain domain;
	/**
	 * Chap's current coord
	 */
	protected Coord coord;
	/**
	 * Chap's current dir
	 */
	protected Direction dir;
	/**
	 * The chap JPanel
	 */
	private ChapJPanel ChapJPanel;
	/**
	 * The changingTerrainJPanel, this is the JPanel holding those changing terrain
	 * types.
	 */
	private ChangingElementsJPanel changingTerrainJPanel;
	/**
	 * The current game level
	 */
	private int level = -1;
	/**
	 * The music
	 */
	private Music backgroundMusic;

	/**
	 * Constructor
	 */
	public WorldJPanel() {
	}
	
	@Override
	public void setDomain(Domain domain) {

		// -------- Set the world,board and chap----------
		this.domain = domain;
		this.board = domain.getBoard();
		this.coord = this.domain.getPlayerLocation();
		// -----------Add music---------------------------
		try {
			this.backgroundMusic = new Music(FileUtil.getAudioStream("music_level1.wav"));
		} catch (IOException e) {
			System.out.println("Music loading failed");
			e.printStackTrace();
		}
		// modify volumn, positive means increase, negative means decrease.
		this.backgroundMusic.modifyVolumn(-5);
		this.backgroundMusic.start();
		this.backgroundMusic.loop();
		// -------- Set the properties of this JPanel------
		setLayout(null);
		setVisible(true);
		// place chap in center of the view.
		updateFocusArea();
		// ------- Start creating elements on this JPanel-------
		// The background JPanel
		BackgroundJPanel backgroundJPanel = new BackgroundJPanel(this);
		// The changingTerrain JPanel
		this.changingTerrainJPanel = new ChangingElementsJPanel(this);
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
		lp.setBounds(0, 0, this.board.getWidth() * TILE_WIDTH, this.board.getHeight() * TILE_HEIGHT);
		// add this JPanel to worldJPanel.
		add(lp);
//		// create a new thread keep checking if chap's location has changed, if changed,
//		// call updateJPanel()
//		CheckUpdate checkUpdate = new CheckUpdate(this);
//		checkUpdate.start();
	}

	/**
	 * update the panel, once chap moves
	 */
	void updateJPanel() {
		if(domain==null) return;
		// -------------Update all the changed JPanels---------------------
		updateFocusArea();
//		// update chap's location
//		this.ChapJPanel.updateChap();
//		// repaint the changingTerrainJPanel.
//		this.changingTerrainJPanel.repaint();
		this.repaint();
	}

	/**
	 * Update focus area, place chap in the middle
	 */
	void updateFocusArea() {
		if(domain==null) return;
		// calculate the offset of chap's coord from center of the board.
		int diffX = TILE_WIDTH * ((WorldJFrame.FOCUS_AREA_COLS - 1) / 2 - domain.getPlayerLocation().getCol());
		int diffY = TILE_HEIGHT * ((WorldJFrame.FOCUS_AREA_ROWS - 1) / 2 - domain.getPlayerLocation().getRow());
		// change the location of panel to place chap in the center.
		setBounds(diffX, diffY, this.board.getWidth() * TILE_WIDTH, this.board.getHeight() * TILE_HEIGHT);
	}

	// -----------------The getters-------------------------------------
	/**
	 * Get the board
	 */
	Board getBoard() {
		return this.board;
	}

	/**
	 * Get chap's current location
	 */
	Coord getChapCoord() {
		return domain.getPlayerLocation();
	}
	

	// TODO Those are TEMP key listeners just for testing GUI.
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
		if(domain==null) return;
		int code = e.getKeyCode();
		switch (code) {
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			domain.moveChipUp();
			domain.update(200);
			System.out.println("move chap up");
			System.out.println("chap's location is: " + domain.getPlayerLocation());
			break;
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			domain.moveChipDown();
			domain.update(200);
			System.out.println("move chap down");
			System.out.println("chap's location is: " + domain.getPlayerLocation());
			break;
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			domain.moveChipLeft();
			domain.update(200);
			System.out.println("move chap left");
			System.out.println("chap's location is: " + domain.getPlayerLocation());
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			domain.moveChipRight();
			domain.update(200);
			System.out.println("move chap right");
			System.out.println("chap's location is: " + domain.getPlayerLocation());
			break;
		default:
			break;
		}
		redraw(domain);
	}
//--------------------Methods inherit from Renderer--------
	@Override
	public void redraw(Domain domain) {
		if (this.coord.getCol() != this.domain.getPlayerLocation().getCol()
				|| this.coord.getRow() != this.domain.getPlayerLocation().getRow()) {
			this.coord = this.domain.getPlayerLocation();
			this.updateJPanel();
		}
	}

	@Override
	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public void playSound(SoundType soundtype) {
		// TODO Auto-generated method stub
		
	}
}

///**
// * This is a sub class extends thread to keep check if there is chap moves.
// * Because when chap moves is unpredictable, so trying to check movement
// * frequently and refresh view.
// * 
// * @author mengli
// *
// */
//class CheckUpdate extends Thread {
//	/**
//	 * The parent JPanel
//	 * 
//	 * @param worldJPanel
//	 */
//	private WorldJPanel worldJPanel;
//
//	public CheckUpdate(WorldJPanel worldJPanel) {
//		this.worldJPanel = worldJPanel;
//	}
//
//	@Override
//	public void run() {
//		if(this.worldJPanel.domain==null) return;
//		while (true) {
//			if (worldJPanel.coord.getCol() != worldJPanel.domain.getPlayerLocation().getCol()
//					|| worldJPanel.coord.getRow() != worldJPanel.domain.getPlayerLocation().getRow()) {
//				worldJPanel.coord = worldJPanel.domain.getPlayerLocation();
//				worldJPanel.updateJPanel();
//			}
//			try {
//				Thread.sleep(20);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//}
