package nz.ac.vuw.ecs.swen225.gp21.app;

public class ToggleAutoPlayAction implements Action {

	@Override
	public void execute(Controller control) {
		
		if (!control.gLoop.getIsPlaying()) {
			control.warning("Cannot toggle autoplay unless playing a game");
			return;
		}
		
		if (!control.gLoop.getIsReplay()) {
			control.warning("Cannot toggle autoplay unless in replay.");
			return;
		} 
		
		boolean a = control.gLoop.getIsAutoPlay();
		control.recorder.setAutoReplayRunning(!a);
		control.gLoop.setAutoPlay(!a);
	}

	@Override
	public String actionName() {
		return "ToggleAutoPlayAction";
	}

}
