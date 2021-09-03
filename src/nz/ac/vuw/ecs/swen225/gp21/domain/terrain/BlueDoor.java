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
	
	private static BlueDoor instance = new BlueDoor();
	
	public static BlueDoor getInstance() { return instance; }
	
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
	public boolean canEntityGoOn(GameObject o) {
		if(o instanceof Chip) return ((Chip)o).hasItem(new KeyItem("Blue"));
		return false;
	}
	@Override
	public String toString() {
		return super.toString()+" Blue";
	}
}
