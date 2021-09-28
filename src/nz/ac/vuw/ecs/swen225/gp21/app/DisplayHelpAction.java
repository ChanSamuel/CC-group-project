package nz.ac.vuw.ecs.swen225.gp21.app;

public class DisplayHelpAction implements Action {

	
	private String msg;
	
	public DisplayHelpAction(String msg) {
		this.msg = msg;
	}
	
	@Override
	public void execute(Controller control) {
		control.report(msg);
	}

	@Override
	public String actionName() {
		return "DisplayHelpAction";
	}

}
