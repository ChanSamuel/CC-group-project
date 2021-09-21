package nz.ac.vuw.ecs.swen225.gp21.app;

public class StartAction implements Action {

	@Override
	public void execute(Controller control) {
		control.gLoop.setIsPlaying(true);
	}

	@Override
	public String actionName() {
		return "StartAction";
	}

}
