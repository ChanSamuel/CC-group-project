package nz.ac.vuw.ecs.swen225.gp21.app;

/**
 * The Action to move Chap down.

 * @author chansamu1 300545169
 *
 */
public class MoveDownAction implements Action, MovementAction {

  @Override
  public void execute(Controller control) {
    control.world.moveChipDown();
    return;
  }

  @Override
  public String actionName() {
    return "MoveDownAction";
  }

}
