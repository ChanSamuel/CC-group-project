package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * The running state represents a world that has been initialized 
 * and is capable of simulating the world.
 * @author Benjamin
 *
 */
public final class Running implements State{

	@Override
	public void update(World w, double elapsedTime) {
		//update all game objects
		for(GameObject e : w.getEntities()) e.update(elapsedTime);
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
		return w.getBoard().isCoordValid(c);
	}

	@Override
	public boolean addObject(World w, GameObject e, Coord c) {
		throw new IllegalStateException("Cannot add new entity while game is running");
	}

	@Override
	public void moveChipLeft(World w) {
		w.getCommandQueue().add(new MoveLeft(w.getPlayer()));
	}

	@Override
	public void moveChipUp(World w) {
		w.getCommandQueue().add(new MoveUp(w.getPlayer()));
	}

	@Override
	public void moveChipDown(World w) {
		w.getCommandQueue().add(new MoveDown(w.getPlayer()));
	}

	@Override
	public void moveChipRight(World w) {
		w.getCommandQueue().add(new MoveRight(w.getPlayer()));
	}

}
