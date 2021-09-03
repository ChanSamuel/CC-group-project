package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.items.KeyItem;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;

/**
 * Gold keys can unlock gold doors
 * @author Benjamin
 *
 */
public final class GoldKey extends KeyTile {
	
	private static GoldKey instance = new GoldKey();
	
	public static GoldKey getInstance() { return instance; }
	
	private GoldKey() {}

	@Override
	public void entityEntered(GameObject o) {
		if(o instanceof Chip) {
			((Chip)o).addItem(new KeyItem("Gold"));
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + "Gold";
	}

}
