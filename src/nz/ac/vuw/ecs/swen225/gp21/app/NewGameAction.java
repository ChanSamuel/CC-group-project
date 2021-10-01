package nz.ac.vuw.ecs.swen225.gp21.app;

/**
 * The Action that is used when starting a new game.

 * @author chansamu1 300545169
 *
 */
public class NewGameAction implements Action, StartAction {

  /**
   * The array of possible levels that can be played.
   */
  private static final String[] levels = { "Level 1", "Level 2", "TEST LEVEL" };
  /**
   * The chosen level to start.
   */
  private String levelChoice;
  
  /**
   * Construct a NewGameAction.

   * @param levelChoice : the choice of level.
   */
  public NewGameAction(String levelChoice) {
    this.levelChoice = levelChoice;
  }

  @Override
  public void execute(Controller control) {

    if (levelChoice.equals(levels[0])) {
      new LoadLevel1Action().execute(control);
    } else if (levelChoice.equals(levels[1])) {
      new LoadLevel2Action().execute(control);
    } else if (levelChoice.equals(levels[2])) {
      new LoadTestLevelAction().execute(control);
    } else {
      control.warning("Level not recognised!");
    }

  }

  @Override
  public String actionName() {
    return "NewGameAction";
  }

}
