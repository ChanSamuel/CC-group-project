package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import nz.ac.vuw.ecs.swen225.gp21.app.Controller;

/**
 * GUI is an abstract class which exposes all components available in the GUI as
 * a field. It's run method constructs the JFrame on the Swing EventQueue.

 * @author chansamu1 300545169
 *
 */
public abstract class GUI extends Controller {
  /**
   * The JFrame of the GUI.
   */
  GUIFrame frame;

  /**
   * A JPanel which Contains the Game page components.
   */
  GamePage gamePage;

  /**
   * A JPanel which contains the home page components.
   */
  HomePage homePage;

  /**
   * Construct a GUI. Nothing is initialised until run() is called.
   */
  public GUI() {
    super();
  }

  @Override
  public void run() {
    try {
      SwingUtilities.invokeAndWait(() -> {
        gamePage = new GamePage(renderer);
        homePage = new HomePage();

        List<Page> pages = new ArrayList<Page>();
        pages.add(homePage);
        pages.add(gamePage);

        frame = new GUIFrame(this, pages);

      });
    } catch (InvocationTargetException e) {
      throw new Error("Frame Construction invocation error!", e);
    } catch (InterruptedException e) {
      throw new Error("Frame construction interrupted!", e);
    }
  }

}
