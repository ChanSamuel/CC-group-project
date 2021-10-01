package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.domain.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.State;
/**
 * This is an interface for the manager JPanel which supposed to hold all the other JPanels.
 * @author limeng7 300525081
 *
 */
public interface MainJPanel {
	/**
	 * This is the initialzation method for this class.
	 * It will create all the JPanels reside on it, set the properties of this JPanel
	 * @param domain
	 * @param level
	 */
	public void init(Domain domain, int level);
	/**
	 * This is the method been called when game stopped
	 * It will stop the music currently playing.
	 */
	public void gameStopped();
	/**
	 * This is the method been called when game paused
	 * It will stop the music currently playing.
	 */
	void gamePaused();
	/**
	 * This is the method been called when game resumed.
	 * It will resume the music from last place where it stopped
	 */
	public void gameResumed();
	/**
	 * Repaint all the changed JPanels.
	 */
	public void updateJPanel();
	/**
	 * This is used for get the game board.
	 * @return board
	 */
	public Board getBoard();
	/**
	 * This is used for get the hero's coordinate.
	 * @return hero's coordinate
	 */
	public Coord getHeroCoord();
	/**
	 * This will be called every short interval(200ms),
	 * it will check if the hero has moved, if moved, redraw the JPanels, otherwise neglect.
	 * @param domain
	 */
	public void redraw(Domain domain);
	/**
	 * Set the level of current game
	 * @param level an int
	 */
	public void setLevel(int level);
	/**
	 * Get the level of current game
	 * @return the level
	 */
	public int getLevel();
	/**
	 * Get the game state of current game
	 * @return the game state
	 */
	public State getState();
	
}
