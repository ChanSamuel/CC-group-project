package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import nz.ac.vuw.ecs.swen225.gp21.app.Controller;

/**
 * The GUIFrame is a JFrame which upon construction will add the corresponding components
 * to itself, and set the right layout.

 * @author chansamu1 300545169
 *
 */
public class GUIFrame extends JFrame {

  /**
   * The menu bar on the frame.
   */
  JMenuBar menuBar = new JMenuBar();
  /**
   * The file menu which holds all file menu items.
   */
  JMenu fileMenu = new JMenu("File");
  /**
   * The help menu which holds all help menu items.
   */
  JMenu helpMenu = new JMenu("Help");
  /**
   * The help rules menu item which brings up the help dialog for rules.
   */
  JMenuItem helpRules = new JMenuItem("Rules");
  /**
   * The menu item for bringing up the controls dialog.
   */
  JMenuItem helpControls = new JMenuItem("Controls");
  /**
   * The menu item which brings up the file loader for loading game.
   */
  JMenuItem fileLoadGame = new JMenuItem("Load game");
  /**
   * The menu item which brings up the file loader for loading a replay.
   */
  JMenuItem fileLoadReplay = new JMenuItem("Load replay");
  /**
   * The menu item which exits to the main menu upon clicking.
   */
  JMenuItem fileExitToMenu = new JMenuItem("Exit to menu");
  /**
   * The menu item which immediately exits on clicking.
   */
  JMenuItem fileQuit = new JMenuItem("Quit game");
  /**
   * The menu item which brings up the file loader for saving the game, then
   * exits.
   */
  JMenuItem fileExitSave = new JMenuItem("Exit and save");
  /**
   * The menu item which brings up the file loader for saving the state of the
   * game into a file.
   */
  JMenuItem fileSaveState = new JMenuItem("Save state");
  /**
   * The menu item which brings up the file loader for saving the game into a
   * replay.
   */
  JMenuItem fileSaveReplay = new JMenuItem("Save replay");

  /**
   * The Controller object.
   */
  Controller control;

  /**
   * Construct the GUIFrame. This adds all the menu and page components and sets
   * the help text for the help menu items.

   * @param control : The Controller object.
   * @param pages   : The List of pages that can be possibly displayed.
   */
  public GUIFrame(Controller control, List<Page> pages) {
    this.control = control;

    menuBar.add(fileMenu);
    menuBar.add(helpMenu);
    fileMenu.add(fileLoadGame);
    fileMenu.add(fileLoadReplay);
    fileMenu.add(fileExitToMenu);
    fileMenu.add(fileQuit);
    fileMenu.add(fileExitSave);
    fileMenu.add(fileSaveState);
    fileMenu.add(fileSaveReplay);
    helpMenu.add(helpRules);
    helpMenu.add(helpControls);

    this.setJMenuBar(menuBar);

    setHelpText();

    setLayout(new CardLayout());

    // Add all pages to frame.
    Container c = getContentPane();
    for (int i = 0; i < pages.size(); i++) {
      Page p = pages.get(i);
      c.add(p.getPanel(), p.getInformalName());
    }

    fileExitSave.setEnabled(false);
    fileSaveState.setEnabled(false);
    fileSaveReplay.setEnabled(false);

    Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();

    pack();
    setSize((int) (0.55 * screenDim.getWidth()), (int) (0.8 * screenDim.getHeight()));
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Chap's Challenge");
    setFocusable(true);
    setVisible(true);

  }

  /**
   * A helper method for adding help text to the help menu.
   */
  private void setHelpText() {

    String rulesMsg = "There are two levels in this game. For each level, you must navigate a maze"
        + " to find the exit, on the way finding keys to open doors, going through"
        + " portals, and evading enemy characters.\n\n"
        + "You have the ability to move, save and load a"
        + " game or replay, as well as" + " pause/resume the game.";

    String controlsMsg = "Movement = arrow keys or WASD\n" + "Exit to menu = Ctrl + E\n"
        + "Load a previous game = Ctrl + R\n" + "Exit program without save = Ctrl + X\n"
        + "Save then exit program = Ctrl + S\n" 
        + "Save current game state = Ctrl + U\n" + "Load a replay = Ctrl + O\n"
        + "Save a replay = Ctrl + Y\n" + "Load level 1 = Ctrl + 1\n" + "Load level 2 = Ctrl + 2\n"
        + "Pause game = space\n" + "Resume game = esc\n";

    this.helpRules.addActionListener((ae) -> {
      control.displayHelpMessage(rulesMsg);
    });

    this.helpControls.addActionListener((ae) -> {
      control.displayHelpMessage(controlsMsg);
    });
  }
}
