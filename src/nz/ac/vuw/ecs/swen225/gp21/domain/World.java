package nz.ac.vuw.ecs.swen225.gp21.domain;

import java.util.List;

public class World implements Domain {

	@Override
	public State getDomainState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Board getBoard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addGameObject(GameObject o, Coord c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void LoadLevelData(Level level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restoreGame(Level level, List<Tick> updates) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restoreDomain(Domain d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Domain getDomain(Domain d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(double elapsedTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forwardTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void goToTick(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Tick> getAllTicks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tick getCurrentTick() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void moveChipUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveChipDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveChipLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveChipRight() {
		// TODO Auto-generated method stub
		
	}

}
