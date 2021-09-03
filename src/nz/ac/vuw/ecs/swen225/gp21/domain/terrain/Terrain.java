package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;

/**
 * Terrain defines the 'floor' of a tile, that GameObjects rest upon.
 * Terrains must define behavior for when a game object enters a tile with this terrain type
 * @author Benjamin
 *
 */
public abstract class Terrain {
	/**
	 * When an object enters a tile with this terrain, the terrain may swap to another
	 * This method is invoked after entityEntered() is invoked.
	 * @param o the GameObject that entered a tile with this terrain type
	 * @return the terrain type the tile should now have
	 */
	public abstract Terrain nextType(GameObject o);
	/**
	 * Conduct activities when an object enters a tile of this type 
	 * This method is invoked before setting the Tile's terrain to this.nextType()
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
	/**
	 * Determine if a given GameObject would be allowed to 
	 * leave a tile with this terrain type
	 * @param o the object leaving a tile with this terrain type
	 * @return default approve. If you need to have conditional leaving of tiles you should override this method.
	 */
	public boolean canEntityLeave(GameObject o) {
		return true;
	}
	
	public String toString() {
		return "TerrainType: ";
	}
	/**
	 * Get the char that represents this terrain type in the board toString, for debugging
	 * @return the char that represents this terrain type in the board toString
	 */
	public abstract char boardChar();
}
