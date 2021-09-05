package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * Blue door is opened by keys that have the blue color attribute.
 * @author Benjamin
 *
 */
public final class SilverDoor extends Door {

	@Override
	public void entityEntered(GameObject o) {
		if(o instanceof Chip) {
			((Chip)o).invetory.remove(new KeyItem("Silver"));
		} else {
			throw new RuntimeException("Non Chip object entered locked door! ->"+this+" & "+o);
		}
	}

	@Override
	public boolean canEntityGoOn(GameObject o) {
		if(o instanceof Chip) return ((Chip)o).hasItem(new KeyItem("Silver"));
		return false;
	}
	@Override
	public String toString() {
		return super.toString()+"Silver";
	}
}
