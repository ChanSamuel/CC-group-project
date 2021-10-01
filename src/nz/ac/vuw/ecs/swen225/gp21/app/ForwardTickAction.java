package nz.ac.vuw.ecs.swen225.gp21.app;

import java.util.List;
import nz.ac.vuw.ecs.swen225.gp21.recorder.GameUpdate;
import nz.ac.vuw.ecs.swen225.gp21.recorder.RecorderException;

/**
 * Action to be done to go forward through the replay.

 * @author chansamu1 300545169
 *
 */
public class ForwardTickAction implements Action, AdvanceTickAction {

  @Override
  public void execute(Controller control) {

    // Precondition checks which return if not in right state.
    if (!control.gameLoop.getIsPlaying()) {
      control.warning("Cannot step through replay when not playing a game.");
      return;
    }

    if (!control.gameLoop.getIsReplay()) {
      control.warning("Cannot step through replay when not in replay.");
      return;
    }

    if (control.gameLoop.getIsAutoPlay()) {
      control.warning("Can't manually do next tick during autoplay");
      return;
    }

    // Get the updates to apply for this tick.
    List<GameUpdate> gameUpdates = null;
    try {
      gameUpdates = control.recorder.next();
    } catch (RecorderException e) {
      control.warning("Stepping forward through replay failed because:\n" + e.getMessage());
      return;
    }

    // Apply the updates.
    for (int i = 0; i < gameUpdates.size(); i++) {
      GameUpdateProxy gup = null;
      if (gameUpdates.get(i) instanceof GameUpdateProxy) {
        gup = (GameUpdateProxy) gameUpdates.get(i);
      } else {
        throw new Error("Other GameUpdate instance type not supported!");
      }

      control.world.forwardTick(gup.getGameEvent());
    }

  }

  @Override
  public String actionName() {
    return "ForwardTickAction";
  }

}
