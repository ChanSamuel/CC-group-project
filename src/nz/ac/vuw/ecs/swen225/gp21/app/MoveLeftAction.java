package nz.ac.vuw.ecs.swen225.gp21.app;

public class MoveLeftAction implements Action {

	@Override
	public void execute(Controller control) {
		control.world.moveChipLeft();
		return;
	}

	@Override
	public String actionName() {
		return "MoveLeftAction";
	}

}
