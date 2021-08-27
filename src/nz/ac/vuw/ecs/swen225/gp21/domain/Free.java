package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * The free terrain type is the simplest. 
 * Any GameObject can enter a free terrain type.
 * @author Benjamin
 *
 */
public class Free extends Terrain {

	@Override
	public Terrain nextType(GameObject o) { return this; }

	@Override
	public void entityEntered(GameObject o) {} //simple behavior for free tiles 

	@Override
	public boolean canEntityGoOn(GameObject o) { return true; } //any object can go on a free tile type

	@Override
	public char boardChar() { return '_'; }

}
