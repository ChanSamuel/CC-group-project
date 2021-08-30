package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * The wall terrain type does not allow anyone past
 * @author Benjamin
 *
 */
public final class Wall extends Terrain {

	@Override
	public Terrain nextType(GameObject o) {return this;}

	@Override
	public void entityEntered(GameObject o) {
		throw new RuntimeException("Entity: ["+o+"] entered a wall tile!");
	}

	@Override
	public boolean canEntityGoOn(GameObject o) {return false;}

	@Override
	public char boardChar() {return '#';}

}
