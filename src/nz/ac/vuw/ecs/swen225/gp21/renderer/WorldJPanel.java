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
public class WorldJPanel extends JPanel {
	/**
	 * tile width
	 */
	public final static int TILE_WIDTH = 40;
	/**
	 * tile height
	 */
	public final static int TILE_HEIGHT = 40;
	/**
	 * The rows of focusing area
	 */
	public final static int FOCUSING_ROWS = 9;
	/**
	 * The cols of focusing area
	 */
	public final static int FOCUSING_COLS = 9;
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
	private Music backgroundMusic;
//	private Music doorOpenSound;
	private Music gameStartSound;
//	private Music pickUpAKeySound;
//	private Music pickUpAChipSound;
//	private Music enterExitSound;
	/**
	 * Constructor
	 */
	public WorldJPanel() {
	}
	
	public void setDomain(Domain domain) {

		// -------- Set the world,board and chap----------
		this.domain = domain;
		this.board = domain.getBoard();
		this.coord = this.domain.getPlayerLocation();
		// -----------Add music---------------------------
		try {
			this.backgroundMusic = new Music(FileUtil.getAudioStream("music_level1.wav"));
			this.gameStartSound = new Music(FileUtil.getAudioStream("GAME_START.wav"));
//			this.doorOpenSound = new Music(FileUtil.getAudioStream("DOOR_OPEN.wav"));
//			this.pickUpAKeySound = new Music(FileUtil.getAudioStream("PICK_UP_A_KEY.wav"));
//			this.pickUpAChipSound = new Music(FileUtil.getAudioStream("PICK_UP_A_CHIP.wav"));
//			this.enterExitSound = new Music(FileUtil.getAudioStream("ENTER_EXIT.wav"));
		} catch (IOException e) {
			System.out.println("Music loading failed");
			e.printStackTrace();
		}
		//----------Game Music----------------------------------------------
		// modify volumn, positive means increase, negative means decrease.
		this.backgroundMusic.modifyVolumn(-5);
		//start background music
		this.backgroundMusic.start();
		//loop background music
		this.backgroundMusic.loop();
		//play game start music
		this.gameStartSound.start();
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
	
	//--------------------Methods inherit from Renderer--------
		public void redraw(Domain domain) {
			if (this.coord.getCol() != this.domain.getPlayerLocation().getCol()
					|| this.coord.getRow() != this.domain.getPlayerLocation().getRow()) {
				this.coord = this.domain.getPlayerLocation();
				this.updateJPanel();
			}
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public static void playSound(SoundType soundType) {
			try {
				Music gameStartSound = new Music(FileUtil.getAudioStream("GAME_START.wav"));
				Music doorOpenSound = new Music(FileUtil.getAudioStream("DOOR_OPEN.wav"));
				Music pickUpAKeySound = new Music(FileUtil.getAudioStream("PICK_UP_A_KEY.wav"));
				Music pickUpAChipSound = new Music(FileUtil.getAudioStream("PICK_UP_A_CHIP.wav"));
				Music enterExitSound = new Music(FileUtil.getAudioStream("ENTER_EXIT.wav"));
				switch(soundType) {
				case GAME_START:
					gameStartSound.start();
					break;
				case DOOR_OPEN:
					doorOpenSound.start();
					break;
				case PICK_UP_A_KEY:
					pickUpAKeySound.start();
					break;
				case PICK_UP_A_CHIP:
					pickUpAChipSound.start();
					break;
				case ENTER_EXIT:
					enterExitSound.start();
					break;
				default:
					throw new RuntimeException("Not a valid sound effect");
				}
			} catch (IOException e) {
				System.out.println("Music loading failed");
				e.printStackTrace();
			}
			
			
		}
}

