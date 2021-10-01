package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.CardLayout;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import nz.ac.vuw.ecs.swen225.gp21.app.controllers.GUIController;

/**
 * An Action which exits to the main menu. For GUIController, this will also
 * change the page.

 * @author chansamu1 300545169
 *
 */
public class ExitToMenuAction implements Action {

  @Override
  public void execute(Controller control) {

    control.gameLoop.setToMenuState();
    control.clearInventory();

    try {
      SwingUtilities.invokeAndWait(() -> {

        control.renderer.gameStopped();

        if (control instanceof GUIController) {
          JFrame frame = ((GUIController) control).getFrame();
          CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
          cl.show(frame.getContentPane(), "Home page");
        }

      });
    } catch (InvocationTargetException e) {
      control.warning("Page change interrputed");
    } catch (InterruptedException e) {
      control.warning("Page change interrputed");
    }
  }

  @Override
  public String actionName() {
    return "ExitToMenuAction";
  }

}
