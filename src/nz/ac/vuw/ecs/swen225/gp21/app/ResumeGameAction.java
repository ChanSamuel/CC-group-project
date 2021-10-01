package nz.ac.vuw.ecs.swen225.gp21.app;

/**
 * The Action that is used when trying to resume the game from pause.

 * @author chansamu1 300545169
 *
 */
public class ResumeGameAction implements Action {

  @Override
  public void execute(Controller control) {

    if (!control.gameLoop.getIsPlaying()) {
      // Don't do anything if not playing a game yet.
      return;
    }

    control.renderer.gameResumed();
    control.gameLoop.setPause(false);
  }

  @Override
  public String actionName() {
    return "ResumeGameAction";
  }

}
