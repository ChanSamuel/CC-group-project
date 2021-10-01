package nz.ac.vuw.ecs.swen225.gp21.app;

/**
 * Action which display the help message.

 * @author chansamu1 300545169
 *
 */
public class DisplayHelpAction implements Action {

  /**
   * The message to display.
   */
  private String msg;

  /**
   * Construct the Action.

   * @param msg : the message to display.
   */
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
