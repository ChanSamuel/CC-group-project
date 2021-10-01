package nz.ac.vuw.ecs.swen225.gp21.app;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.awt.CardLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import nz.ac.vuw.ecs.swen225.gp21.app.controllers.GuiController;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Loading;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Replaying;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Running;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;
import nz.ac.vuw.ecs.swen225.gp21.recorder.RecorderException;

/**
 * Action for loading a replay.

 * @author chansamu1 300545169
 *
 */
public class LoadReplayAction implements Action, StartAction {

  /**
   * The file to load.
   */
  private File replayFile;

  /**
   * The XMLMapper needed to pass to recorder.
   */
  private XmlMapper xmlMapper;

  /**
   * Construct a LoadReplayAction which will load the given file.

   * @param f : the file to load.
   */
  public LoadReplayAction(File f) {
    this.replayFile = f;
    this.xmlMapper = SaveReplayAction.registerXml();
  }

  @Override
  public void execute(Controller control) {

    // Reset the current recording.
    control.recorder.clear();

    try {
      control.recorder.load(new FileInputStream(replayFile), xmlMapper);
    } catch (RecorderException e1) {
      control.warning("Something went wrong when loading the replay:\n" + e1.getMessage());
      return;
    } catch (FileNotFoundException e) {
      throw new Error("File was not found, but file should always exist!:\n" + e.getMessage());
    }

    // Make domain the right state so that loading will work.
    if (control.world.getDomainState() instanceof Running) {
      control.world.setState(new Loading());
    }

    int levelNumber = control.recorder.getLevel();

    try {
      Persister.loadLevel(levelNumber, control.world);
    } catch (PersistException e) {
      control.warning("Something went wrong when persisting the level:\n" + e.getMessage());
      return;
    }

    // Make domain the right state so that Renderer.init() will work.
    if (control.world.getDomainState() instanceof Loading) {
      control.world.doneLoading();
    }

    try {
      SwingUtilities.invokeAndWait(() -> {
        control.renderer.gameStopped();
        control.renderer.init(control.world, levelNumber);
        if (control instanceof GuiController) {
          JFrame frame = ((GuiController) control).getFrame();
          CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
          cl.show(frame.getContentPane(), "Game page");
        }
      });
    } catch (InvocationTargetException e) {
      control.warning("Renderer intialisation interrputed");
      return;
    } catch (InterruptedException e) {
      control.warning("Renderer intialisation interrputed");
      return;
    }

    control.gameLoop.setToInitialPlayState();
    control.gameLoop.setIsReplay(true);
    control.world.setState(new Replaying());
  }

  @Override
  public String actionName() {
    return "LoadReplayAction";
  }

}
