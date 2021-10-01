package nz.ac.vuw.ecs.swen225.gp21.app;

import java.io.File;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

/**
 * Action which attempts to save the game, then exits if successful.

 * @author chansamu1 300545169
 *
 */
public class ExitSaveAction implements Action {

  /**
   * The file to save to.
   */
  private File saveFile;

  /**
   * Construct this Action.

   * @param saveFile : the file to save to.
   */
  public ExitSaveAction(File saveFile) {
    this.saveFile = saveFile;
  }

  @Override
  public void execute(Controller control) {

    if (!control.gameLoop.getIsPlaying()) {
      control.warning(
          "Save failed because you aren't playing a game.\n If you would like to exit," 
          + " use the Exit button.");
      return;
    }

    try {
      control.report("Attempting to save " + saveFile.getPath() + "\nPress 'OK' and wait.");
      control.persister.saveCurrentGame(saveFile, control.levelNumber, 
          control.gameLoop.getTimeLeft());
    } catch (PersistException e) {
      control.warning(e.getMessage());
      return;
    }

    // Close all threads and exit.
    System.exit(0);
  }

  @Override
  public String actionName() {
    return "ExitSaveAction";
  }

}
