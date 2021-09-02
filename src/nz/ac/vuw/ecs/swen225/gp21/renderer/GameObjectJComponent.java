package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;

import javax.swing.JComponent;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;

/**
 * This is an abstract class for all the game object
 * @author mengli
 *
 */
public abstract class GameObjectJComponent extends JComponent{
	/**
	 * Constructor
	 * Take coordinates, size and filename as parameters
	 */
	GameObjectJComponent(Coord coord,int width, int height,String filename) {
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
