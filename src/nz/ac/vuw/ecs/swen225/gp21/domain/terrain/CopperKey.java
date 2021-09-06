package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.items.KeyItem;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;

/**
 * Gives Chip a blue key when entered
 * @author Benjamin
 *
 */
public final class CopperKey extends KeyTile {
	/**
	 * Store the instance of Blue key terrain here
	 */
	private static CopperKey instance = new CopperKey();
	/**
	 * Get an instance of blue key terrain
	 * @return instance of blue key terrain
	 */
	public static CopperKey getInstance() { return instance; }
	/**
	 * Create blue key terrain
	 */
	private CopperKey() {}
	
	@Override
	public void entityEntered(GameObject o) {
		if(o instanceof Chip) ((Chip)o).addItem(new KeyItem("Blue"));
	}
	@Override
	public void undoEntityActions(GameObject o) {
		if(o instanceof Chip) ((Chip)o).removeItem(new KeyItem("Blue"));
	}
	
	@Override
	public String toString() { return super.toString()+"Blue"; }
}