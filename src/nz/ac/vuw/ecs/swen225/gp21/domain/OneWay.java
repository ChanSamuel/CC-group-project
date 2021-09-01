package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * All one way tiles restrict object movement.
 * They all share these common attributes
 * 	You can enter a one way tile as long as you are not facing
 * 	its direction.
 * 	You can only leave a one way tile if you are facing its direction.
 * Note: Be careful of pushing blocks onto one way tiles, they will probably get stuck.
 * @author Benjamin
 *
 */
abstract class OneWay extends Terrain {
	/**
	 * The direction the tile can be traversed across
	 */
	protected final Direction d;
	/**
	 * OneWay tiles must be given the direction they can be traversed in
	 * @param d the direction this tile can be traversed in
	 */
	protected OneWay(Direction d){
		this.d = d;
	}
	
	@Override
	public Terrain nextType(GameObject o) { return this; }

	@Override
	public void entityEntered(GameObject o) {}

	@Override
	public boolean canEntityGoOn(GameObject o) {
		return o.dir != d.opposite();
	}
	
	@Override
	public boolean canEntityLeave(GameObject o) {
		return o.dir == d;
	}

	@Override
	public abstract char boardChar();

	@Override 
	public String toString() {
		return super.toString()+"One way tile: "+d;
	}
}
