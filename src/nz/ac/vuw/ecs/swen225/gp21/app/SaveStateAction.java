package nz.ac.vuw.ecs.swen225.gp21.app;

import java.io.File;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

/**
 * The Action used when trying to save the current game state to a file.

 * @author chansamu1 300545169
 *
 */
public class SaveStateAction implements Action {

  /**
   * The file to save to.
   */
  private File saveFile;

  /**
   * Construct a SaveStateAction.

   * @param saveFile : the file to save to.
   */
  public SaveStateAction(File saveFile) {
    this.saveFile = saveFile;
  }

  @Override
  public void execute(Controller control) {

    if (!control.gameLoop.getIsPlaying()) {
      control.warning("Cannot save unless a game is being played.");
      return;
    }

    try {
      control.persister.saveCurrentGame(saveFile,
          control.levelNumber, control.gameLoop.getTimeLeft());
    } catch (PersistException e) {
      control.warning("Something went wrong when persisting the state:\n" + e.getMessage());
      return;
    }
    control.report("Save successful");
  }

  @Override
  public String actionName() {
    return "SaveAction";
  }

}
