package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * Gold keys can unlock gold doors
 * @author Benjamin
 *
 */
public final class GoldKey extends KeyTile {

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
