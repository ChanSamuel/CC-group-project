package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * Gives Chip a blue key when entered
 * @author Benjamin
 *
 */
public final class BlueKey extends KeyTile {

	@Override
	public void entityEntered(GameObject o) {
		if(o instanceof Chip) {
			((Chip)o).addItem(new KeyItem("Blue"));
		}
	}
	
	@Override
	public String toString() { return super.toString()+"Blue"; }

}
