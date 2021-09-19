package nz.ac.vuw.ecs.swen225.gp21.app;

import nz.ac.vuw.ecs.swen225.gp21.domain.Tick;

public class BackTickAction implements Action {

	private Tick t;
	
	public BackTickAction(Tick t) {
		this.t = t;
	}
	
	@Override
	public void execute(Controller control) {
		control.world.backTick(t);
	}

	@Override
	public String actionName() {
		return "BackTickAction";
	}

}
