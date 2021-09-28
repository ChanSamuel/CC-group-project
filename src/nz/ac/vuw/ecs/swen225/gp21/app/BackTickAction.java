package nz.ac.vuw.ecs.swen225.gp21.app;

import nz.ac.vuw.ecs.swen225.gp21.domain.Tick;

public class BackTickAction implements Action, AdvanceTickAction {

	
	@Override
	public void execute(Controller control) {
		
		if (!control.gLoop.getIsPlaying()) {
			control.warning("Cannot step through replay unless playing a game");
			return;
		}
		
		if (!control.gLoop.getIsReplay()) {
			control.warning("Cannot step through replay when not in replay.");
			return;
		}
		
		if (control.gLoop.getIsAutoPlay()) {
			control.warning("Can't manually do next tick during autoplay");
			return;
		}
		Tick t = control.recorder.prevTick();
		control.world.backTick(t);
	}

	@Override
	public String actionName() {
		return "BackTickAction";
	}

}
