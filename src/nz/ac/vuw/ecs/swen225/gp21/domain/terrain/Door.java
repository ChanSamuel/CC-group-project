package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;

/**
 * Door tile is the base class the colored doors derive from
 * @author Benjamin
 *
 */
abstract class Door implements Terrain {

	@Override
	public Terrain nextType(GameObject o) { o.w.openedADoor(); return Free.getInstance();}

	@Override
	public abstract void entityEntered(GameObject o);
	
	@Override
	public abstract boolean canEntityGoOn(GameObject o);

	@Override
	public char boardChar() {return 'D';}
	
	@Override
	public String toString() {
		return "Door ";
	}
}
