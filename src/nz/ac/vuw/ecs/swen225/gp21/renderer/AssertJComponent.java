package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
/**
 * The assert JComponent,such as key and chip(the treasure), update when picked up
 * @author mengli
 *
 */
public class AssertJComponent extends GameObjectJComponent{

	AssertJComponent(Coord coord, int width, int height, String filename) {
		super(coord, width, height, filename);
	}
	/**
	 * start the key animation when key picked up
	 * @param x destination x
	 * @param y destination y
	 */
	void AssertPickedUp(int x, int y) {
		this.repaint();
		//TODO create a new thread AssertMoving for animation
		AssertMoving am = new AssertMoving();
	}
}

/**
 * a sub class extends thread handling the picking up key animation
 * @author mengli
 *
 */
class AssertMoving extends Thread{
	
}