package nz.ac.vuw.ecs.swen225.gp21.app;

public class ExitReplayAction implements Action {

	@Override
	public void execute(Controller control) {
		control.gLoop.setIsReplay(false);
		control.gLoop.setAutoPlay(false);
	}

	@Override
	public String actionName() {
		return "ExitReplayAction";
	}

}
