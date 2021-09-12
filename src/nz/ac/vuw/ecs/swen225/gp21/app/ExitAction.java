package nz.ac.vuw.ecs.swen225.gp21.app;

public class ExitAction implements Action {

	@Override
	public void execute(Controller control) {
		control.gLoop.setTerminated(true);
	}

	@Override
	public String actionName() {
		return "ExitAction";
	}

}
