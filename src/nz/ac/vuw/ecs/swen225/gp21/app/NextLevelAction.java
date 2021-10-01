package nz.ac.vuw.ecs.swen225.gp21.app;

/**
 * The Action that is used when proceeding to the next level upon completion of
 * the previous.

 * @author chansamu1 300545169
 *
 */
public class NextLevelAction implements Action {

  @Override
  public void execute(Controller control) {
    // Assume we are in the replay state temporarily.
    control.gameLoop.setToMenuState();

    // Depending on the level, issue the corresponding action to move to that level.
    if (control.levelNumber == 0) { // On test level
      control.report("You completed this level congratulations!\n" + "Proceeding to level 1.");
      new LoadLevel1Action().execute(control);
    } else if (control.levelNumber == 1) {
      control.report("You completed this level congratulations!\n" + "Proceeding to level 2.");
      new LoadLevel2Action().execute(control);
    } else if (control.levelNumber == 2) {
      control.report("You completed the game congratulations!\nExiting to menu now.");
      new ExitToMenuAction().execute(control);
    } else {
      throw new Error("There should only be 2 real levels and a test level!");
    }
  }

  @Override
  public String actionName() {
    return "NextLevelAction";
  }

}
