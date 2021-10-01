package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.CardLayout;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import nz.ac.vuw.ecs.swen225.gp21.app.controllers.GuiController;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Loading;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Running;

/**
 * Action for loading a previously saved game state from a file.

 * @author chansamu1 300545169
 *
 */
public class LoadGameAction implements Action, StartAction {

  /**
   * The file to load.
   */
  private File loadFile;

  /**
   * Construct the Action.

   * @param f : the file to load.
   */
  public LoadGameAction(File f) {
    this.loadFile = f;
  }

  @Override
  public void execute(Controller control) {

    if (control.world.getDomainState() instanceof Running) {
      control.world.setState(new Loading());
    }

    int[] levelAndTime = null;
    try {
      levelAndTime = control.persister.loadGame(loadFile);
    } catch (Exception e) {
      control.warning("Something went wrong when loading a previously saved game:\n" 
          + e.getMessage());
      return;
    }

    // Reset the current recording.
    control.recorder.clear();

    control.levelNumber = levelAndTime[0];
    int timeLeft = levelAndTime[1];

    try {
      SwingUtilities.invokeAndWait(() -> {
        control.renderer.gameStopped();
        control.renderer.init(control.world, control.levelNumber);
        if (control instanceof GuiController) {
          JFrame frame = ((GuiController) control).getFrame();
          CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
          cl.show(frame.getContentPane(), "Game page");
        }
      });
    } catch (InvocationTargetException e) {
      throw new Error("Renderer intialisation interrputed:\n" + e.getMessage(), e);
    } catch (InterruptedException e) {
      throw new Error("Renderer intialisation interrputed:\n" + e.getMessage(), e);
    }

    control.gameLoop.setToInitialPlayState();
    control.gameLoop.setLevelStartTime(timeLeft);
  }

  @Override
  public String actionName() {
    return "LoadGameAction";
  }

}
