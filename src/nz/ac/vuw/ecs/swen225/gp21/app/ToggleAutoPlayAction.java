package nz.ac.vuw.ecs.swen225.gp21.app;

/**
 * Action which toggles the auto-playback mode of the current replay.

 * @author chansamu1 300545169
 *
 */
public class ToggleAutoPlayAction implements Action {

  @Override
  public void execute(Controller control) {

    // Check preconditions.
    if (!control.gameLoop.getIsPlaying()) {
      control.warning("Cannot toggle autoplay unless playing a game");
      return;
    }

    if (!control.gameLoop.getIsReplay()) {
      control.warning("Cannot toggle autoplay unless in replay.");
      return;
    }

    // Execute since valid.
    boolean a = control.gameLoop.getIsAutoPlay();
    control.gameLoop.setAutoPlay(!a);
  }

  @Override
  public String actionName() {
    return "ToggleAutoPlayAction";
  }

}
