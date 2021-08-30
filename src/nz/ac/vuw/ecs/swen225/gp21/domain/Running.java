package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * The running state represents a world that has been initialized 
 * and is capable of simulating the world.
 * @author Benjamin
 *
 */
public final class Running implements State{
	/**
	 * Check world isn't null
	 * @param w world parameter
	 */
	private void worldCheck(World w) {
		if(w == null) throw new RuntimeException("World is null!");
	}

	@Override
	public void update(World w, double elapsedTime) {
		worldCheck(w);
		//update all game objects
		for(GameObject e : w.getEntities()) e.update(elapsedTime);
		if(w.getBoard().getRemainingChips() == 0) w.getBoard().openExit();
	}

	@Override
	public int getBoardWidth(World w) {
		worldCheck(w);
		return w.getBoard().getWidth();
	}

	@Override
	public int getBoardHeight(World w) {
		worldCheck(w);
		return w.getBoard().getHeight();
	}

	@Override
	public boolean isCoordValid(World w, Coord c) {
		worldCheck(w);
		return w.getBoard().isCoordValid(c);
	}

	@Override
	public boolean addObject(World w, GameObject e, Coord c) {
		throw new IllegalStateException("Cannot add new entity while game is running");
	}

	@Override
	public void moveChipLeft(World w) {
		worldCheck(w);
		w.getCommandQueue().add(new MoveLeft(w.getPlayer()));
	}

	@Override
	public void moveChipUp(World w) {
		worldCheck(w);
		w.getCommandQueue().add(new MoveUp(w.getPlayer()));
	}

	@Override
	public void moveChipDown(World w) {
		worldCheck(w);
		w.getCommandQueue().add(new MoveDown(w.getPlayer()));
	}

	@Override
	public void moveChipRight(World w) {
		worldCheck(w);
		w.getCommandQueue().add(new MoveRight(w.getPlayer()));
	}

}
