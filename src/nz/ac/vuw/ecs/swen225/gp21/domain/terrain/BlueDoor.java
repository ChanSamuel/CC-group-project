package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.items.KeyItem;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;

/**
 * Blue door is opened by keys that have the blue color attribute.
 * @author Benjamin
 *
 */
public final class BlueDoor extends Door {
	/**
	 * Store the instance of blue door terrain here
	 */
	private static BlueDoor instance = new BlueDoor();
	/**
	 * Get an instance of blue door terrain
	 * @return instance of blue door terrain
	 */
	public static BlueDoor getInstance() { return instance; }
	/**
	 * Create blue door terrain
	 */
	private BlueDoor() {}

	@Override
	public void entityEntered(GameObject o) {
		if(o instanceof Chip) {
			((Chip)o).removeItem(new KeyItem("Blue"));
		} else {
			throw new RuntimeException("Non Chip object entered locked door! ->"+this+" & "+o);
		}
	}
	@Override
	public void undoEntityActions(GameObject o) {
		if(o instanceof Chip) {
			((Chip)o).addItem(new KeyItem("Blue"));
		} else {
			throw new RuntimeException("Non Chip object was on locked door! ->"+this+" & "+o);
		}	
	}

	@Override
	public boolean canEntityGoOn(GameObject o) {
		if(o instanceof Chip) return ((Chip)o).hasItem(new KeyItem("Blue"));
		return false;
	}
	@Override
	public String toString() {
		return super.toString()+" Blue";
	}
}
