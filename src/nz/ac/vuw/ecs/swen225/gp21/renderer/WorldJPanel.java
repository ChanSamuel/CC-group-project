package nz.ac.vuw.ecs.swen225.gp21.renderer;

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
 * @author mengli 300525081
 *
 */
public class WorldJPanel extends JPanel {
	/**
	 * tile width
	 */
	public final static int TILE_WIDTH = 55;
	/**
	 * tile height
	 */
	public final static int TILE_HEIGHT = 55;
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
	private Domain domain;
	/**
	 * Chap's current coord
	 */
	protected Coord playerCoord;
	/**
	 * Chap's current dir
	 */
	protected Direction dir;
	/**
	 * The chap JPanel
	 */
	private ChapJPanel chapJPanel;
	/**
	 * The changingTerrainJPanel, this is the JPanel holding those changing terrain
	 * types.
	 */
	private ChangingElementsJPanel changingElementsJPanel;
	/**
	 * The current game level
	 */
	private int level = -1;
	boolean playerMoved = true;
	private DoorJComponent doorJComponent;
	Music level1Music;
	Music level2Music;
	private static WorldJPanel worldJPanel = new WorldJPanel();
	/**
	 * Constructor
	 */
	private WorldJPanel() {
	}
	/**
	 * Get instance
	 */
	public static WorldJPanel getInstance() {
		return worldJPanel;
	}
	/**
	 * Set the domain and call init to initialize WorldJpanel
	 * 
	 * @param domain
	 */
	public void init(Domain domain, int level) {
		this.domain = domain;
		this.level = level;
		// remove all old JPanels if exit, then revalidate.
		this.removeAll();
		this.revalidate();
		// ---------Play music of current level---------------------
		try {
			if (this.level == 1) {
				level1Music = new Music(FileUtil.getAudioStream("music_level1.wav"));
				// modify volumn, positive means increase, negative means decrease.
				level1Music.modifyVolumn(-5);
				// start background music
				level1Music.start();
				// loop background music
				level1Music.loop();
			} else if (this.level == 2) {
				level2Music = new Music(FileUtil.getAudioStream("music_level2.wav"));
				// modify volumn, positive means increase, negative means decrease.
				level2Music.modifyVolumn(-5);
				// start background music
				level2Music.start();
				// loop background music
				level2Music.loop();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ---------Set the board and coord of player----------------------
		this.board = domain.getBoard();
		this.playerCoord = this.domain.getPlayerLocation();
		// -------- Set the properties of this JPanel------
		setLayout(null);
		setVisible(true);
		// place chap in center of the view.
		updateFocusArea();
		// ------- Start creating elements on this JPanel-------
		// The background JPanel
		BackgroundJPanel backgroundJPanel = BackgroundJPanel.getInstance();
		backgroundJPanel.init(board);
		// The doors JComponent
		this.doorJComponent = DoorJComponent.getInstance();
		doorJComponent.init(this);
		// The changingTerrain JPanel
		this.changingElementsJPanel = ChangingElementsJPanel.getInstance();
		changingElementsJPanel.init(this);
		// The chap JPanel
		this.chapJPanel = ChapJPanel.getInstance();
		this.chapJPanel.init(this);
		// ---Create a layered pane and add elements to this pane-------
		JLayeredPane lp = new JLayeredPane();
		int index = 1;
		lp.setLayout(null);
		// arrange the layer, smaller index on top.
		lp.add(chapJPanel, index++);
		lp.add(this.doorJComponent, index++);
		lp.add(changingElementsJPanel, index++);
		lp.add(backgroundJPanel, 1000);
		lp.setVisible(true);
		lp.setBounds(0, 0, this.board.getWidth() * TILE_WIDTH, this.board.getHeight() * TILE_HEIGHT);
		// add this JPanel to worldJPanel.
		add(lp);
	}
	/**
	 * called when game stopped.
	 */
	void gameStopped() {
		if (level1Music != null) {
			level1Music.stop();
		} else if (level2Music != null) {
			level2Music.stop();
		}
	}

	/**
	 * update the panel, once chap moves
	 */
	void updateJPanel() {
		// ---------check if domain and level have been set----------------
		if (domain == null || level == -1)
			throw new RuntimeException("Please set domain and level");
		// -------------Update all the changed JPanels---------------------
		updateFocusArea();
		// update chap's location
		this.chapJPanel.repaint();
		// repaint the changingTerrainJPanel.
		this.changingElementsJPanel.repaint();
		// repaint the doorJComponent.
		this.doorJComponent.repaint();
	}

	/**
	 * Update focus area, place chap in the middle
	 */
	void updateFocusArea() {
		if (domain == null)
			return;
		// calculate the offset of chap's coord from center of the board.
		int diffX = TILE_WIDTH * ((WorldJFrame.FOCUS_AREA_COLS - 1) / 2 - domain.getPlayerLocation().getColumn());
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

	/**
	 * Redraw the maze if chap moves.
	 * 
	 * @param domain
	 */
	public void redraw(Domain domain) {
		if (this.playerCoord.getColumn() != this.domain.getPlayerLocation().getColumn()
				|| this.playerCoord.getRow() != this.domain.getPlayerLocation().getRow()) {
			this.playerCoord = this.domain.getPlayerLocation();
			this.playerMoved = true;
			this.updateJPanel();
		} else {
			this.playerMoved = false;
		}
	}

	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Play sound effect, this method should be called when event such as pick up a
	 * chip, pick up a key, open the door etc.
	 */
	public static void playSound(SoundType soundType) {
		try {
			
				switch (soundType) {
//				case BGM_LEVEL_1:
//					level1Music = new Music(FileUtil.getAudioStream("music_level1.wav"));
//					// modify volumn, positive means increase, negative means decrease.
//					level1Music.modifyVolumn(-5);
//					// start background music
//					level1Music.start();
//					// loop background music
//					level1Music.loop();
//					break;
//				case BGM_LEVEL_2:
//					level2Music = new Music(FileUtil.getAudioStream("music_level2.wav"));
//					// modify volumn, positive means increase, negative means decrease.
//					level2Music.modifyVolumn(-5);
//					// start background music
//					level2Music.start();
//					// loop background music
//					level2Music.loop();
//					break;
				case GAME_START:
					new Music(FileUtil.getAudioStream("GAME_START.wav")).start();
					;
					break;
				case DOOR_OPEN:
					new Music(FileUtil.getAudioStream("DOOR_OPEN.wav")).start();
					;
					break;
				case SHOW_INFO:
					new Music(FileUtil.getAudioStream("SHOW_INFO.wav")).start();
					;
					break;
				case TELEPORT:
					new Music(FileUtil.getAudioStream("TELEPORT.wav")).start();
					break;
				case PUSH_BLOCK:
					new Music(FileUtil.getAudioStream("PUSH_BLOCK.wav")).start();
					;
					break;
				case PICK_UP_A_KEY:
					new Music(FileUtil.getAudioStream("PICK_UP_A_KEY.wav")).start();
					;
					break;
				case PICK_UP_A_CHIP:
					new Music(FileUtil.getAudioStream("PICK_UP_A_CHIP.wav")).start();
					;
					break;
				case ENTER_EXIT:
					new Music(FileUtil.getAudioStream("ENTER_EXIT.wav")).start();
					;
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
