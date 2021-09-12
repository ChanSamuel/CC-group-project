package nz.ac.vuw.ecs.swen225.gp21.app;

public class PauseAction implements Action {

	@Override
	public void execute(Controller control) {
		control.gLoop.setPause(true);
	}

	@Override
	public String actionName() {
		return "PauseAction";
	}

}
