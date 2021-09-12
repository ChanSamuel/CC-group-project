package nz.ac.vuw.ecs.swen225.gp21.renderer;

import javax.swing.JComponent;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;

/**
 * This is the door JComponent, update when door unlocked.
 * @author mengli
 *
 */
public class DoorJComponent extends JComponent{
	/**
	 * The constructor
	 */
	public DoorJComponent() {
		
	}
	/**
	 * start the door animation when door opened.
	 */
	void openDoor() {
		this.repaint();
		//TODO
		DoorMoving dm = new DoorMoving();
	}

}
//TODO
class DoorMoving extends Thread{
	
}
