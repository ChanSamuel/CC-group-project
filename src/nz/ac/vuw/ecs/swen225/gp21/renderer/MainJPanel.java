package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.domain.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;

public interface MainJPanel {
	/**
	 * This is the initialzation method for this class.
	 * It will create all the JPanels reside on it, set the properties of this JPanel
	 * @param domain
	 * @param level
	 */
	public void init(Domain domain, int level);
	/**
	 * This is the method been called when game stopped.
	 * It will stop the music currently playing.
	 */
	public void gameStopped();
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
}
