package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * Door tile is the base class the colored doors derive from
 * @author Benjamin
 *
 */
abstract class Door extends Terrain {

	@Override
	public Terrain nextType(GameObject o) {return new Free();}

	@Override
	public abstract void entityEntered(GameObject o);
	
	@Override
	public abstract boolean canEntityGoOn(GameObject o);

	@Override
	public char boardChar() {return 'D';}
	
	@Override
	public String toString() {
		return "Door";
	}
}
