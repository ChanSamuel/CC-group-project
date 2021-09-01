package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * Gold door can be opened by chip. 
 * Chip must have a gold key to open the gold door.
 * @author Benjamin
 *
 */
public final class GoldDoor extends Door {

	@Override
	public void entityEntered(GameObject o) {
		if(o instanceof Chip) {
			((Chip)o).invetory.remove(new KeyItem("Gold"));
		} else {
			throw new RuntimeException("Non Chip object entered locked door! ->"+this+" & "+o);
		}
	}

	@Override
	public boolean canEntityGoOn(GameObject o) {
		if(o instanceof Chip) return ((Chip)o).invetory.contains(new KeyItem("Gold"));
		return false;
	}

}
