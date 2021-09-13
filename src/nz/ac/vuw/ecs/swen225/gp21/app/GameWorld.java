package nz.ac.vuw.ecs.swen225.gp21.app;

import nz.ac.vuw.ecs.swen225.gp21.domain.Item;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;

public class GameWorld extends World {

	Controller control;
	
	public GameWorld(Controller control) {
		super();
		this.control = control;
	}
	
	@Override
	public void collectedAChip() {
		control.chipCollectedTrans();

	}

	@Override
	public void enteredExit() {
		control.enteredExitTrans();
	}

	@Override
	public void enteredInfo(String msg) {
		control.enteredInfoTrans();
	}

	@Override
	public void leftInfo() {
		control.leftInfoTrans();
	}

	@Override
	public void playerLost() {
		control.playerLostTrans();
	}

	@Override
	public void playerGainedItem(Item item) {
		control.playerGainedItemTrans();
	}

	@Override
	public void playerConsumedItem(Item item) {
		control.playerConsumedItemTrans();
	}

}
