package nz.ac.vuw.ecs.swen225.gp21.app;

/**
 * Action to update the timer display.

 * @author chansamu1 300545169
 *
 */
public class UpdateTimerAction implements Action {

  /**
   * The time in seconds left.
   */
  private int timeSecs;

  /**
   * Construct an UpdateTimerAction.

   * @param timeSecs : the time in seconds left.
   */
  public UpdateTimerAction(int timeSecs) {
    this.timeSecs = timeSecs;
  }

  @Override
  public void execute(Controller control) {
    control.updateTimerOperation(timeSecs);
  }
  
  /**
   * A method specifically for static execution of updating the timer. This is
   * useful since we don't want to be creating an entire object just to update the
   * timer as it will slow the game down.

   * @param control : the controller object.
   * @param timeLeft : the time left in seconds.
   */
  public static void execute(Controller control, int timeLeft) {
    control.updateTimerOperation(timeLeft);
  }

  @Override
  public String actionName() {
    return "UpdateTimerAction";
  }

}
