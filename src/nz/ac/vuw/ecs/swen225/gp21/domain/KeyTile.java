package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * Base class for the various key tiles to derive from
 * @author Benjamin
 *
 */
abstract class KeyTile extends Terrain {

	@Override
	public Terrain nextType(GameObject o) {
		if(o instanceof Chip) {
			return new Free();
		} else {
			return this;
		}
	}	
	
	@Override
	public abstract void entityEntered(GameObject o);

	@Override
	public boolean canEntityGoOn(GameObject o) {return true;}//anyone can go on this tile

	@Override
	public char boardChar() {return 'k';}
	
	@Override
	public String toString() { return super.toString()+"Key tile"; }

}
