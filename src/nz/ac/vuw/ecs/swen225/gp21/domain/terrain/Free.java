package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;

/**
 * The free terrain type is the simplest. 
 * Any GameObject can enter a free terrain type.
 * @author Benjamin
 *
 */
public class Free implements Terrain {
	
	private static Free instance = new Free();
	
	public static Free getInstance() { return instance; }
	
	private Free() {}

	@Override
	public Terrain nextType(GameObject o) { return this; }

	@Override
	public void entityEntered(GameObject o) {} //simple behavior for free tiles 

	@Override
	public boolean canEntityGoOn(GameObject o) { return true; } //any object can go on a free tile type

	@Override
	public char boardChar() { return '_'; }
	
	@Override
	public String toString() {return super.toString()+"Free";}

}
