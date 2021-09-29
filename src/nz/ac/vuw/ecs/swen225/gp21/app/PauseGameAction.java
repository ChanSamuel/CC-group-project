package nz.ac.vuw.ecs.swen225.gp21.app;

public class PauseGameAction implements Action {

	@Override
	public void execute(Controller control) {
		
		if (!control.gLoop.getIsPlaying()) {
			// Don't do anything if not playing a game yet.
			return;
		}
		
		control.gLoop.setPause(true);
		control.pauseOperation();
		
	}

	@Override
	public String actionName() {
		return "PauseGameAction";
	}

}
