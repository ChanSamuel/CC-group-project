package nz.ac.vuw.ecs.swen225.gp21.app;

import nz.ac.vuw.ecs.swen225.gp21.domain.Tick;

public class ForwardTickAction implements Action {
	
	private Tick t;
	
	public ForwardTickAction(Tick t) {
		this.t = t;
	}
	
	@Override
	public void execute(Controller control) {
		control.world.forwardTick(t);
	}

	@Override
	public String actionName() {
		return "ForwardTickAction";
	}

}
