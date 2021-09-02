package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;

import javax.swing.JComponent;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
/**
 * The hero chap's JComponent
 * @author mengli
 *
 */
public class ChapJComponent extends GameObjectJComponent{
	/**
	 * Constructor for chap
	 */
	ChapJComponent(Coord coord,int width, int height,String filename) {
		super(coord, height, height, filename);
	}
	/**
	 * Update chap when moving
	 * @param x x coordinate of chap
	 * @param y y coordinate of chap
	 */
	void updateChap(int x,int y) {
		//create a new chapMoving thread for the animation
		this.repaint();
		//TODO create a new thread ChapMoving for animation
		ChapMoving cm = new ChapMoving();
	}
}
/**
 * a subClass extends thread handling the chap moving animation
 * @author mengli
 *
 */
//TODO
class ChapMoving extends Thread{
	
}
