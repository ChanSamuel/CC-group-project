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
	private final World w;
	/**
	 * Create a new GameObject
	 * @param w
	 */
	public GameObject(World w) {
		this.w = w;
	}
	/**
	 * GameObjects must state whether an entity can enter onto the same tile as them
	 * For instance, a bug can enter the same tile as Chip, but a bug cannot enter the same tile as a block
	 * @param entity the entity trying to move onto the same tile as 'this'
	 * @param w the game world, in case the entity needs to check some parameters to determine if it can be stepped on
	 * @return Whether or not the entity can enter the tile 'this' object is on, according to the game logic
	 */
	protected abstract boolean canEntityGoOnTile(GameObject entity);
	/**
	 * Execute this object's behavior when another GameObject enters the same tile it is on.
	 * For example, if Chip enters the same tile as a movable block, the block moves itself { w.moveUp(this) }
	 * @param entity the entity that just entered the tile 'this' object is on
	 * @param w the game world, in case the object needs to perform some behavior on the world
	 */
	protected abstract void entityEnteredTile(GameObject entity);
	/**
	 * Perform this objects behavior for one game simulation tick  { for example, the bug type moves in a random direction each second }
	 * @param elapsedTime time in milliseconds since the last update
	 */
	protected abstract void update(double elapsedTime);
}
