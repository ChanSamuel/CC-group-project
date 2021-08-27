package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * A Game object is something that exists in the game world that can move.
 * @author Benjamin
 *
 */
abstract class GameObject {
	/**
	 * This entities reference back to the world.
	 * Since the world is what moves the objects [ for now :^/ ], if an object needs to move itself
	 * it needs a reference back up to the top.
	 */
	protected final World w;
	/**
	 * The logic that GameObjects use to choose how to move is encapsulated away into a movement controller
	 */
	protected MovementController c;
	/**
	 * The direction this object is facing
	 */
	protected Direction dir;
	/**
	 * The tile that this GameObject is currently on
	 */
	protected Tile currentTile;
	/**
	 * Create a new GameObject, defaults to no direction
	 * NOTE: 	Changed visibility to package-private, might need to be changed later?
	 * 			how will persistence module load in new GameObjects? 
	 * @param w A reference to the world, so the object can tell the game to move it on the board {not sure how we can get the object to move itself?}
	 * @param c The movement controller component that controls this object 
	 */
	GameObject(World w, MovementController c) {
		this.w = w; this.c = c; dir = Direction.NONE;
	}
	/**
	 * Create a new GameObject with a direction
	 * @param w A reference to the world
	 * @param c The movement controller component that controls this object 
	 * @param d The direction this object has
	 */
	GameObject(World w, MovementController c, Direction d) {
		this.w = w; this.c = c; dir = d;
	}
	/**
	 * GameObjects must state whether an entity can enter onto the same tile as them
	 * For instance, a bug can enter the same tile as Chip, but a bug cannot enter the same tile as a block
	 * @param entity the entity trying to move onto the same tile as 'this'
	 * @return Whether or not the entity can enter the tile 'this' object is on, according to the game logic
	 */
	protected abstract boolean canEntityGoOnTile(GameObject entity);
	/**
	 * Execute this object's behavior when another GameObject enters the same tile it is on.
	 * For example, if Chip enters the same tile as a movable block, the block moves itself { w.moveUp(this) }
	 * @param entity the entity that just entered the tile 'this' object is on
	 */
	protected abstract void entityEnteredTile(GameObject entity);
	/**
	 * Perform this objects behavior for one game simulation tick  { for example, the bug type moves in a random direction each second }
	 * @param elapsedTime time in milliseconds since the last update
	 */
	protected abstract void update(double elapsedTime);
	/**
	 * Get the name of this object type.
	 * For example, the player controlled entity will return "Chip"
	 * 				the bug entity will return "Bug"
	 * @return the name of this entity
	 */
	protected abstract String getName();
	/**
	 * Update the direction of the GameObject, provided it has one.
	 * @param d the new direction
	 */
	protected void updateDirection(Direction d) {
		if(dir != Direction.NONE) this.dir = d;
	}
	/**
	 * Updates the old tile reference to have no occupier
	 * Updates this GameObject's tile reference
	 * @param tile the new tile this object is on
	 */
	protected void setTile(Tile tile) {
		if(currentTile != null) this.currentTile.removeOccupier();
		this.currentTile = tile;
	}
	/**
	 * Get the char that represents this terrain type in the board toString, for debugging
	 * @return the char that represents this terrain type in the board toString
	 */
	public abstract char boardChar();
	
	@Override
	public String toString() {
		return "GameObject: "+getClass().getSimpleName()+" facing->"+dir+" at->"+currentTile.location;
	}
}
