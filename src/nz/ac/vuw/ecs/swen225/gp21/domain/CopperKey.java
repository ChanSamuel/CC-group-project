package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * Gives Chip a blue key when entered
 * @author Benjamin
 *
 */
public final class CopperKey extends KeyTile {

	@Override
	public void entityEntered(GameObject o) {
		if(o instanceof Chip) {
			((Chip)o).addItem(new KeyItem("Silver"));
		}
	}
	
	@Override
	public String toString() { return super.toString()+"Silver"; }

}
