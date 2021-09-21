package nz.ac.vuw.ecs.swen225.gp21.app;

public interface Action {
	
	public void execute(Controller control);
	
	public String actionName();
}
