package nz.ac.vuw.ecs.swen225.gp21.app;

public class HaltAction implements Action {

	@Override
	public void execute(Controller control) {
		control.gLoop.setIsPlaying(false);
	}

	@Override
	public String actionName() {
		return "HaltAction";
	}

}
