package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 * The background for the GamePage.

 * @author chansamu1 300545169
 *
 */
@SuppressWarnings("serial")
public class GamePageBackground extends JPanel {

  /**
   * Construct a GamePageBackground.
   */
  public GamePageBackground() {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int height = screenSize.height;
    int width = screenSize.width;

    Dimension size = new Dimension((int) (0.85 * width), (int) (0.75 * height));
    setPreferredSize(size);
    setMinimumSize(size);
    setMaximumSize(size);
  }
}
