package nz.ac.vuw.ecs.swen225.gp21.app;

/**
 * The Action to be executed when the user needs to change tick speed.

 * @author chansamu1 300545169
 *
 */
public class ChangeTickSpeedAction implements Action {

  /**
   * The multiplier to change tick speed by.
   */
  private double multiplier;

  /**
   * Construct a ChangeTickSpeed Action.

   * @param m : the coefficent of change.
   */
  public ChangeTickSpeedAction(double m) {
    this.multiplier = m;
  }

  @Override
  public void execute(Controller control) {

    // Precondition checks.
    if (!control.gameLoop.getIsPlaying()) {
      control.warning("Cannot change tick speed unless playing a game");
      return;
    }

    if (!control.gameLoop.getIsReplay()) {
      control.warning("Cannot change tick speed unless in replay.");
      return;
    }

    if (multiplier > 5) {
      control.warning("The speed multiplier value that you "
          + "entered must be less than or equal to 5");
      return;
    } else if (multiplier < 0.2) {
      control.warning("The speed multiplier value that you "
          + "entered must be greater than or equal to 0.2");
      return;
    }

    // Set the speed.
    control.gameLoop.setSpeedMult(multiplier);
  }

  @Override
  public String actionName() {
    return "ChangeTickSpeedAction";
  }

}
