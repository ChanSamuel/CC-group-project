package nz.ac.vuw.ecs.swen225.gp21.app;

public class SetAutoPlayAction implements Action {

	private boolean isAutoPlay;
	
	public SetAutoPlayAction(boolean isAutoPlay) {
		this.isAutoPlay = isAutoPlay;
	}
	
	@Override
	public void execute(Controller control) {
		control.recorder.setAutoReplayRunning(this.isAutoPlay);
		control.gLoop.setAutoPlay(true);
	}

	@Override
	public String actionName() {
		return "SetAutoPlayAction";
	}

}
