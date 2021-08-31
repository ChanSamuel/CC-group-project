package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * The block is an object that can be pushed by Chip into empty tiles
 * @author Benjamin
 *
 */
final class Block extends GameObject {
	/**
	 * Create a new movable block
	 * @param w
	 */
	Block(World w){
		super(w, new NoMovement());
	}

	@Override
	protected boolean canEntityGoOnTile(GameObject entity) {
		if(entity instanceof Chip) { //Only chip can enter the tile the block is on
			Coord dest = getNextLocation(entity);
			if(!w.isCoordValid(dest)) return false;
			Tile t = w.getTileAt(dest);
			//provided the block itself can move in the direction Chip is trying to push it
			this.dir = entity.dir; //borrow the direction from entity, incase the terrain type needs to know where we are going (it usually does)
			boolean answer = t.canEntityGoOnTile(this); 
			this.dir = Direction.NONE;
			return answer;
		}
		return false;
	}

	@Override
	protected void entityEnteredTile(GameObject entity) {
		//chip entered the tile, move the block to the next square
		this.dir = entity.dir;
		switch(dir) {
		case NORTH:
			w.moveUp(this);
			break;
		case EAST:
			w.moveRight(this);
			break;
		case SOUTH:
			w.moveDown(this);
			break;
		case WEST:
			w.moveLeft(this);
			break;
		default:
			throw new RuntimeException("Unknown direction for block: "+entity.dir+" | "+entity.toString());
		}
		this.dir = Direction.NONE;
	}

	@Override
	protected void update(double elapsedTime) {
		this.c.update(elapsedTime).execute(w);
	}

	@Override
	protected String getName() { return getClass().getSimpleName(); }

	@Override
	public char boardChar() { return '='; }
	/**
	 * Helper method to get the tile the block will move to if it is pushed by an entity
	 * @param o the object that will push the block
	 * @return the location of the tile the block would move to
	 */
	private Coord getNextLocation(GameObject o) {
		if(o.dir == Direction.NONE) throw new RuntimeException("Entity: e["+o.toString()+"] facing NONE direction, cannot move block:["+o.dir+"] in NONE direction");
		return o.dir.next(currentTile.location);
	}

	@Override
	public String toString() {
		return super.toString()+" "+getClass().getSimpleName();
	}
}
