package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;

/**
 * This is the door Jcomponent, update when door unlocked.
 * @author mengli
 *
 */
public class DoorJComponent extends GameObjectJComponent{

	DoorJComponent(Coord coord,Direction dir) {
		super(coord,dir);
		
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
