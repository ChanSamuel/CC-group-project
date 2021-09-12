package nz.ac.vuw.ecs.swen225.gp21.app;

public class MoveRightAction implements Action{

	@Override
	public void execute(Controller control) {
		control.domain.moveChipRight();
		return;
	}

	@Override
	public String actionName() {
		return "MoveRightAction";
	}

}
