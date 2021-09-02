package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * The running state represents a world that has completed its gameplay simulation 
 * and is in a finished state.
 * @author Benjamin
 *
 */
public final class GameOver implements State {

	@Override
	public void update(World w, double elapsedTime) {
		throw new IllegalStateException("World is in game over state! No more updates can be made!");
	}

	@Override
	public int getBoardWidth(World w) {
		return w.getBoard().getWidth();
	}

	@Override
	public int getBoardHeight(World w) {
		return w.getBoard().getHeight();
	}

	@Override
	public boolean isCoordValid(World w, Coord c) {
		return w.isCoordValid(c);
	}

	@Override
	public boolean addObject(World w, GameObject e, Coord c) {
		throw new IllegalStateException("World is in game over state! No more objects can be added");
	}

	@Override
	public void moveChipLeft(World w) {
		throw new IllegalStateException("World is in game over state! Cannot enqueue any more moves");
	}

	@Override
	public void moveChipUp(World w) {
		throw new IllegalStateException("World is in game over state! Cannot enqueue any more moves");
	}

	@Override
	public void moveChipDown(World w) {
		throw new IllegalStateException("World is in game over state! Cannot enqueue any more moves");
	}

	@Override
	public void moveChipRight(World w) {
		throw new IllegalStateException("World is in game over state! Cannot enqueue any more moves");
	}

	@Override
	public void loadLevel(World world, Level level) {
		throw new IllegalStateException("Cannot load level when game is not in loading state!");
	}

}