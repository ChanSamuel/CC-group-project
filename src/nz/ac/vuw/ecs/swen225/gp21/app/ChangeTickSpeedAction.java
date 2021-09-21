package nz.ac.vuw.ecs.swen225.gp21.app;

public class ChangeTickSpeedAction implements Action {
	
	private double multiplier;
	
	public ChangeTickSpeedAction(double m) {
		this.multiplier = m;
	}
	
	@Override
	public void execute(Controller control) {
		control.requestFocus();
		
		if (!control.gLoop.getIsReplay()) {
			control.warning("Cannot change tick speed unless in replay.");
			return;
		}
		
		if (multiplier > 5) {
			control.warning("The speed multiplier value that you entered must be less than or equal to 5");
			return;
		} else if (multiplier < 0.2) {
			control.warning("The speed multiplier value that you entered must be greater than or equal to 0.2");
			return;
		}
		
		int tps = (int) (((double) GameLoop.BASE_TPS / multiplier) + 0.5);
		control.gLoop.setTps(tps);
	}

	@Override
	public String actionName() {
		return "ChangeTickSpeedAction";
	}

}
