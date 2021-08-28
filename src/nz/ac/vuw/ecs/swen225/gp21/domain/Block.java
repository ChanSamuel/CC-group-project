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
			if(dest.getCol() < 0 || dest.getCol() >= w.getBoardWidth() || dest.getRow() < 0 || dest.getRow() >= w.getBoardHeight()) return false;
			Tile t = w.getTileAt(dest);
			//provided the block itself can move in the direction Chip is trying to push it
			return t.isTileOccupied() ? t.getOccupier().canEntityGoOnTile(this) : true; 
		}
		return false;
	}

	@Override
	protected void entityEnteredTile(GameObject entity) {
		//chip entered the tile, move the block to the next square
		switch(entity.dir) {
		case NORTH:
			w.moveUp(this);
			return;
		case EAST:
			w.moveRight(this);
			return;
		case SOUTH:
			w.moveDown(this);
			return;
		case WEST:
			w.moveLeft(this);
			return;
		default:
			throw new RuntimeException("Unknown direction for block: "+entity.dir+" | "+entity.toString());
		}
	}

	@Override
	protected void update(double elapsedTime) {
		this.c.update(elapsedTime).execute(w);
	}

	@Override
	protected String getName() { return getClass().getSimpleName(); }

	@Override
	public char boardChar() { return 'B'; }
	/**
	 * Helper method to get the tile the block will move to if it is pushed by an entity
	 * @param o the object that will push the block
	 * @return the location of the tile the block would move to
	 */
	private Coord getNextLocation(GameObject o) {
		switch(o.dir) {
		case NORTH:
			return o.currentTile.location.up();
		case EAST:
			return o.currentTile.location.right();
		case WEST:
			return o.currentTile.location.left();
		case SOUTH:
			return o.currentTile.location.down();
		default:
			throw new RuntimeException("Entity facing unknow direction for block: ["+o.dir+"] e: ["+o.toString()+"]");
		}
	}

	@Override
	public String toString() {
		return super.toString()+" "+getClass().getName();
	}
}
