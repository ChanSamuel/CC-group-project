package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.items.KeyItem;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;

/**
 * Gold keys can unlock gold doors
 * @author Benjamin
 *
 */
public final class SilverKey extends KeyTile {
	/**
	 * Store the instance of gold key terrain here
	 */
	private static SilverKey instance = new SilverKey();
	/**
	 * Get an instance of Gold key terrain
	 * @return an instance of gold key terrain
	 */
	public static SilverKey getInstance() { return instance; }
	/**
	 * Create a new gold key terrain
	 */
	private SilverKey() {}

	@Override
	public void entityEntered(GameObject o) {
		if(o instanceof Chip) ((Chip)o).addItem(new KeyItem("Gold"));
	}
	@Override
	public void undoEntityActions(GameObject o) {
		if(o instanceof Chip) ((Chip)o).removeItem(new KeyItem("Gold"));
	}
	
	@Override
	public String toString() {
		return super.toString() + "Gold";
	}
}
