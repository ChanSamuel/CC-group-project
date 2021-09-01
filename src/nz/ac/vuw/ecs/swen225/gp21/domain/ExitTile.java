package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * The exit tile is the final goal tile
 * If Chip enters a tile of this type, they win!
 * @author Benjamin
 *
 */
public class ExitTile extends Terrain {

	@Override
	public Terrain nextType(GameObject o) { return this; }

	@Override
	public void entityEntered(GameObject o) {
		o.w.enteredExit();
	}

	@Override
	public boolean canEntityGoOn(GameObject o) {
		if(o instanceof Chip) return true;
		return false;
	}

	@Override
	public char boardChar() { return 'e'; }
	
	@Override
	public String toString() { return super.toString()+"Exit"; }

}
