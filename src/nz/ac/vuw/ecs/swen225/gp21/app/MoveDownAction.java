package nz.ac.vuw.ecs.swen225.gp21.app;

import java.util.Optional;

public class MoveDownAction implements Action {

	@Override
	public void execute(Controller control) {
		control.domain.moveChipDown();
		return;
	}

	@Override
	public String actionName() {
		return "MoveDownAction";
	}

}
