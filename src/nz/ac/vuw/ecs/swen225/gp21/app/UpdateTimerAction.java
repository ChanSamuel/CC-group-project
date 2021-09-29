package nz.ac.vuw.ecs.swen225.gp21.app;

public class UpdateTimerAction implements Action {
	
	private int timeSecs;
	
	public UpdateTimerAction(int timeSecs) {
		this.timeSecs = timeSecs;
	}
	
	@Override
	public void execute(Controller control) {
		control.updateTimerOperation(timeSecs);
	}

	@Override
	public String actionName() {
		return "UpdateTimerAction";
	}
	
	/**
	 * A method specifically for static execution of updating the timer.
	 * This is useful since we don't want to be creating an entire object just to update the timer as it
	 * will slow the game down.
	 * @param control
	 * @param timeLeft
	 */
	public static void execute(Controller control, int timeLeft) {
		control.updateTimerOperation(timeLeft);
	}
}
