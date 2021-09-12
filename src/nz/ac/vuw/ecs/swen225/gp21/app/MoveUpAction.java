package nz.ac.vuw.ecs.swen225.gp21.app;

public class MoveUpAction implements Action {

	@Override
	public void execute(Controller control) {
		control.domain.moveChipUp();
	}

	@Override
	public String actionName() {
		return "MoveUpAction";
	}

}
