package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.CardLayout;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import nz.ac.vuw.ecs.swen225.gp21.app.controllers.GuiController;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Loading;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Running;

/**
 * Action for loading the first level.

 * @author chansamu1 300545169
 *
 */
public class LoadLevel1Action implements Action, StartAction {

  @Override
  public void execute(Controller control) {

    // Force domain to be in the right state so that loading a level works.
    if (control.world.getDomainState() instanceof Running) {
      control.world.setState(new Loading());
    }

    try {
      Persister.loadLevel(1, control.world);
    } catch (Exception e) {
      control.warning("Something went wrong when loading level 1:\n" + e.getMessage());
      return;
    }

    // Reset the current recording.
    control.recorder.clear();

    // Reset the state back to running so that Renderer.init() will work.
    if (control.world.getDomainState() instanceof Loading) {
      control.world.doneLoading();
    }

    // Try updating the renderer and GUI.
    try {
      SwingUtilities.invokeAndWait(() -> {

        control.renderer.gameStopped();
        control.renderer.init(control.world, 1);
        if (control instanceof GuiController) {

          GuiController gui = (GuiController) control;
          gui.clearTextPanel();

          JFrame frame = gui.getFrame();
          CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
          cl.show(frame.getContentPane(), "Game page");
        }
      });
    } catch (InvocationTargetException e) {
      throw new Error(e.getMessage(), e);
      //control.warning("Renderer intialisation interrputed");
      //return;
    } catch (InterruptedException e) {
      throw new Error(e.getMessage(), e);
      //control.warning("Renderer intialisation interrputed");
      //return;
    }

    control.levelNumber = 1;
    control.gameLoop.setLevelStartTime(60);
    control.gameLoop.setToInitialPlayState();
  }

  @Override
  public String actionName() {
    return "LoadLevel1Action";
  }

}
