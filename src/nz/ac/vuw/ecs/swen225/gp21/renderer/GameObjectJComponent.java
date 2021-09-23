package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;

import javax.swing.JComponent;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;

/**
 * This is an abstract class for all the game object, 
 * game objects are items that can move on map, such as blocks,monsters.
 * @author mengli 300525081
 *
 */
public abstract class GameObjectJComponent extends JComponent{
	/**
	 * Constructor
	 * Take coordinates, size and filename as parameters
	 */
	GameObjectJComponent(Coord coord,Direction dir) {
		super();
		//TODO
	}
	/**
	 * override paintComponent method,draw game objects
	 */
	@Override
	public void paintComponent(Graphics g) {
		//TODO 
	}
}
