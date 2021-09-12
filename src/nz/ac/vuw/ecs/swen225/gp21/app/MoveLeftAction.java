package nz.ac.vuw.ecs.swen225.gp21.app;

public class MoveLeftAction implements Action {

	@Override
	public void execute(Controller control) {
		control.domain.moveChipLeft();
		return;
	}

	@Override
	public String actionName() {
		return "MoveLeftAction";
	}

}
