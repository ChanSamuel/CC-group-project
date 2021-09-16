package nz.ac.vuw.ecs.swen225.gp21.app;

import java.util.Optional;

public class MoveDownAction implements Action {

	@Override
	public void execute(Controller control) {
		control.world.moveChipDown();
		System.out.println("chap's location is: " + control.world.getPlayerLocation());
		return;
	}

	@Override
	public String actionName() {
		return "MoveDownAction";
	}

}
