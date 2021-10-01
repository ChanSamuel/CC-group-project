package nz.ac.vuw.ecs.swen225.gp21.app;

import nz.ac.vuw.ecs.swen225.gp21.app.controllers.GuiController;

/**
 * The Main class which contains the main method to run the game using a
 * GUIController.

 * @author chansamu1 300545169
 *
 */
public class Main {

  /**
   * The main method.

   * @param args : terminal arguments.
   */
  public static void main(String[] args) {
    Controller c = new GuiController();
    c.run();
  }

}
