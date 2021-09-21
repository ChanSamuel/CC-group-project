package nz.ac.vuw.ecs.swen225.gp21.app;

public class TogglePauseAction implements Action {

	@Override
	public void execute(Controller control) {
		boolean p = control.gLoop.getIsPaused();
		control.gLoop.setPause(!p);
		
		// Need to request back focus otherwise key listener will go numb.
		// This may be due to an issue regarding acquiring the lock for Keyboard.
		control.requestFocus();
	}

	@Override
	public String actionName() {
		return "TogglePauseAction";
	}

}
