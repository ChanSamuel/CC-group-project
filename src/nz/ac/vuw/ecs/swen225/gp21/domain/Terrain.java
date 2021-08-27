package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * Terrain defines the 'floor' of a tile, that GameObjects rest upon.
 * Terrains must define behavior for when a game object enters a tile with this terrain type
 * @author Benjamin
 *
 */
public abstract class Terrain {
	/**
	 * When an object enters a tile with this terrain, the terrain may swap to another
	 * @param o the GameObject that entered a tile with this terrain type
	 * @return the terrain type the tile should now have
	 */
	public abstract Terrain nextType(GameObject o);
	/**
	 * Conduct activities when an object enters a tile of this type 
	 * @param o the object that entered a tile with this type
	 */
	public abstract void entityEntered(GameObject o);
	/**
	 * Determines if a given GameObject is allowed to enter a tile 
	 * with this terrain type
	 * @param o the object trying to enter a tile with this terrain type
	 * @return whether or not this object is allowed to enter the tile
	 */
	public abstract boolean canEntityGoOn(GameObject o);
	
}
