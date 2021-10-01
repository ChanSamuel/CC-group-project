package nz.ac.vuw.ecs.swen225.gp21.app;

/**
 * The Action to move Chap right.

 * @author chansamu1 300545169
 *
 */
public class MoveRightAction implements Action, MovementAction {

  @Override
  public void execute(Controller control) {
    control.world.moveChipRight();
    return;
  }

  @Override
  public String actionName() {
    return "MoveRightAction";
  }

}
