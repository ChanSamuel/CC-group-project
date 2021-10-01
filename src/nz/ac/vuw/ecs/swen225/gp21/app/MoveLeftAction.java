package nz.ac.vuw.ecs.swen225.gp21.app;

/**
 * The Action to move Chap left.

 * @author chansamu1 300545169
 *
 */
public class MoveLeftAction implements Action, MovementAction {

  @Override
  public void execute(Controller control) {
    control.world.moveChipLeft();
    return;
  }

  @Override
  public String actionName() {
    return "MoveLeftAction";
  }

}
