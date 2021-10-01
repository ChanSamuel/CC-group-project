package nz.ac.vuw.ecs.swen225.gp21.app;

/**
 * The Action that is used when the player loses.

 * @author chansamu1 300545169
 *
 */
public class PlayerLostAction implements Action {

  @Override
  public void execute(Controller control) {
    control.report("You have lost! Retrying the level...");
    control.clearInventory();
    if (control.levelNumber == 0) {
      new LoadTestLevelAction().execute(control);
    } else if (control.levelNumber == 1) {
      new LoadLevel1Action().execute(control);
    } else if (control.levelNumber == 2) {
      new LoadLevel2Action().execute(control);
    } else {
      throw new Error("There should only be three levels.");
    }
  }

  @Override
  public String actionName() {
    return "PlayerLostAction";
  }

}
