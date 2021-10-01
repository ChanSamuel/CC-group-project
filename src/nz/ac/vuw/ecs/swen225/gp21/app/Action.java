package nz.ac.vuw.ecs.swen225.gp21.app;

/**
 * Actions change the state of modules on invocation of their execute() method.
 * The idea is that all changes to the state of any module is done within the
 * game loop, OR is done in the execute method of some Action, which is queued
 * up to be invoked by the game loop.
 * 
 * <p>Queued up Actions are an example of the 'command' pattern.

 * @author chansamu1 300545169
 *
 */
public interface Action {

  /**
   * The execute method for this action. GameLoop is meant to call this when it is
   * ready.

   * @param control : the controller object used to change the state of the game.
   */
  public void execute(Controller control);

  /**
   * The informal name of the action.

   * @return name of the action.
   */
  public String actionName();
}
