package nz.ac.vuw.ecs.swen225.gp21.app;

/**
 * Actions change the state of modules on invocation of their execute() method.
 * The idea is that all changes to the state of any module is done within the game loop, OR
 * is done in the execute method of some Action, which is queued up to be invoked by the game loop.
 * 
 * Queued up Actions are an example of the command pattern
 * 
 * @author Sam
 *
 */
public interface Action {
	
	public void execute(Controller control);
	
	public String actionName();
}
