package nz.ac.vuw.ecs.swen225.gp21.app;

public class ResumeAction implements Action{

	@Override
	public void execute(Controller control) {
		control.gLoop.setPause(false);
	}

	@Override
	public String actionName() {
		return "ResumeAction";
	}

}
