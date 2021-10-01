package nz.ac.vuw.ecs.swen225.gp21.app;

/**
 * The Action to move Chap up.

 * @author chansamu1 300545169
 *
 */
public class MoveUpAction implements Action, MovementAction {

  @Override
  public void execute(Controller control) {
    control.world.moveChipUp();
  }

  @Override
  public String actionName() {
    return "MoveUpAction";
  }

}
