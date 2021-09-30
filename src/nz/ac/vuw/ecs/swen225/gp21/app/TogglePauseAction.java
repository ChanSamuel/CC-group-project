package nz.ac.vuw.ecs.swen225.gp21.app;

public class TogglePauseAction implements Action {

	@Override
	public void execute(Controller control) {
		
		if (!control.gameLoop.getIsPlaying()) {
			// Don't do anything if not playing a game yet.
			return;
		}
		
		boolean p = control.gameLoop.getIsPaused();
		control.gameLoop.setPause(!p);
		if (!p) {
            control.renderer.gamePaused();
            control.pauseOperation();
        }else {
            control.renderer.gameResumed();
        }
	}

	@Override
	public String actionName() {
		return "TogglePauseAction";
	}

}
