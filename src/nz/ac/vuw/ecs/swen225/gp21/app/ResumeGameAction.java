package nz.ac.vuw.ecs.swen225.gp21.app;

public class ResumeGameAction implements Action {

	@Override
	public void execute(Controller control) {
		
		if (!control.gLoop.getIsPlaying()) {
			// Don't do anything if not playing a game yet.
			return;
		}
		
		control.renderer.gameResumed();
		control.gLoop.setPause(false);
	}

	@Override
	public String actionName() {
		return "ResumeGameAction";
	}

}
