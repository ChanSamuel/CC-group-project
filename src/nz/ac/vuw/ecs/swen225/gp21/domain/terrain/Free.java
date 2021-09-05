package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;

/**
 * The free terrain type is the simplest. 
 * Any GameObject can enter a free terrain type.
 * @author Benjamin
 *
 */
public class Free implements Terrain {
	/**
	 * Store the instance of free terrain here.
	 */
	private static Free instance = new Free();
	/**
	 * Get an instance of free terrain
	 * @return instance of free terrain
	 */
	public static Free getInstance() { return instance; }
	/**
	 * Create free terrain
	 */
	private Free() {}

	@Override
	public Terrain nextType(GameObject o) { return this; }

	@Override
	public void entityEntered(GameObject o) {} //simple behavior for free tiles 
	
	@Override
	public void undoEntityActions(GameObject o) {}

	@Override
	public boolean canEntityGoOn(GameObject o) { return true; } //any object can go on a free tile type

	@Override
	public char boardChar() { return '_'; }
	
	@Override
	public String toString() {return super.toString()+"Free";}
}
