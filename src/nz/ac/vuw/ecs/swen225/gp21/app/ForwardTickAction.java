package nz.ac.vuw.ecs.swen225.gp21.app;

import nz.ac.vuw.ecs.swen225.gp21.domain.Tick;

public class ForwardTickAction implements Action {
	
	@Override
	public void execute(Controller control) {
		
		if (!control.gLoop.getIsReplay()) {
			control.warning("Cannot get step to next tick when not in replay.");
			return;
		}
		
		if (control.gLoop.getIsAutoPlay()) {
			control.warning("Can't manually do next tick during autoplay");
			return;
		}
		Tick t = control.recorder.nextTick();
		control.world.forwardTick(t);
	}

	@Override
	public String actionName() {
		return "ForwardTickAction";
	}

}
