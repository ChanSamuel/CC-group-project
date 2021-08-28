package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * The loading state represents a world that is not initialized
 * and cannot simulate the world
 * But it can load in level data
 * @author Benjamin
 *
 */
public class Loading implements State {
	
	@Override
	public void update(World w, double elapsedTime) {
		throw new IllegalStateException("Cannot simulate world while world is loading!");
	}

	@Override
	public int getBoardWidth(World w) {
		throw new IllegalStateException("Cannot get board width while world is loading!");
	}

	@Override
	public int getBoardHeight(World w) {
		throw new IllegalStateException("Cannot get board height while world is loading!");
	}

	@Override
	public boolean isCoordValid(World w, Coord c) {
		throw new IllegalStateException("Cannot verify coordinate while world is loading!");
	}

	@Override
	public boolean addObject(World w, GameObject e, Coord c) {
		if(e == null || c == null) throw new IllegalArgumentException("Cannot add null to the game!");
		w.getEntities().add(e);
		w.getBoard().addObject(e, c); //this method will throw an exception if it fails
		return true;
	}

	@Override
	public void moveChipLeft(World w) {
		throw new IllegalStateException("Cannot move chip while world is loading!");
	}

	@Override
	public void moveChipUp(World w) {
		throw new IllegalStateException("Cannot move chip while world is loading!");
	}

	@Override
	public void moveChipDown(World w) {
		throw new IllegalStateException("Cannot move chip while world is loading!");
	}

	@Override
	public void moveChipRight(World w) {
		throw new IllegalStateException("Cannot move chip while world is loading!");
	}

}
