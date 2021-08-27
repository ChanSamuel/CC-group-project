package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * The game world is comprised of tiles
 * Tiles contain a location, possible game object, and Terrain type
 * @author Benjamin
 *
 */
final class Tile {
	/**
	 * The location of this tile on the board
	 */
	final Coord location;
	/**
	 * Reference to the object that is on this tile
	 */
	private GameObject occupier;
	/**
	 * Reference to the type of terrain this tile has
	 */
	private Terrain terrain;
	/**
	 * Create a Tile with no terrain type {uninitialized}
	 * @param location
	 */
	Tile(Coord location){
		this.location = location;
		this.terrain = null;
		occupier = null;
	}
	/**
	 * Remove the GameObject reference for this tile
	 */
	void removeOccupier() {
		this.occupier = null;
	}
	/**
	 * Called when manually moving an object around the board
	 * for example, when an object teleports
	 * @param o 
	 */
	void setOccupier(GameObject o) {
		if(isTileOccupied()) occupier.entityEnteredTile(o);
		o.setTile(this);
		occupier = o;
	}
	/**
	 * Follow the complete move procedure
	 * Update the occupier and the terrain type
	 * @param o
	 */
	void addOccupier(GameObject o){
		setOccupier(o);
		terrain.entityEntered(o);
		setTerrain(terrain.nextType(o));
	}
	/**
	 * Gets the GameObject on this tile
	 * @return The GameObject on this tile
	 */
	GameObject getOccupier() {
		return occupier;
	}
	/**
	 * Returns whether this tile has a GameObject on it or not
	 * @return true if there is a gameObject on this tile
	 */
	boolean isTileOccupied() {
		return getOccupier() != null;
	}
	/**
	 * Change the type of terrain this tile has
	 * @param t the type of terrain
	 */
	public void setTerrain(Terrain t) {
		if(t == null) throw new IllegalArgumentException("Terrain cannot be null!");
		this.terrain = t;
	}
	/**
	 * Get the type of terrain this tile has
	 * @return the type of terrain of this tile
	 */
	public Terrain getTerrain() {
		if(this.terrain == null) throw new IllegalStateException("This tile has not been given a terrain type! "+this.location);
		return this.terrain;
	}
}
