package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * Contains a method to add key listeners to a page. Idea here is that multiple
 * pages will have the same key bindings action mappings, but only the currently
 * focused page will be listening.

 * @author chansamu1 300545169
 *
 */
public class KeyboardConfigurer {

  /**
   * Add a listener to the current page.

   * @param page    : the page to add to.
   * @param control : the GUIController object
   */
  @SuppressWarnings("serial")
  public static void addListener(JPanel page, GuiController control) {
    ActionMap actionMap = page.getActionMap();

    actionMap.put("MOVE LEFT", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        control.moveLeft();
      }
    });

    actionMap.put("MOVE RIGHT", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        control.moveRight();
      }
    });

    actionMap.put("MOVE UP", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        control.moveUp();
      }
    });

    actionMap.put("MOVE DOWN", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        control.moveDown();
      }
    });

    actionMap.put("PAUSE GAME", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        control.pauseGame();
      }
    });

    actionMap.put("RESUME GAME", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        control.resumeGame();
      }
    });

    actionMap.put("NEW LEVEL 1", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        // Re-enable save buttons for game page.
        control.frame.fileExitSave.setEnabled(true);
        control.frame.fileSaveState.setEnabled(true);
        control.frame.fileSaveReplay.setEnabled(true);

        control.loadLevel1();
      }
    });

    actionMap.put("NEW LEVEL 2", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        // Re-enable save buttons for game page.
        control.frame.fileExitSave.setEnabled(true);
        control.frame.fileSaveState.setEnabled(true);
        control.frame.fileSaveReplay.setEnabled(true);

        control.loadLevel2();
      }
    });

    InputMap inputMap = page.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

    // Key bindings for movement.
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "MOVE LEFT");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "MOVE LEFT");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "MOVE RIGHT");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "MOVE RIGHT");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "MOVE UP");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "MOVE UP");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "MOVE DOWN");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "MOVE DOWN");

    // Key bindings for pausing and resuming the game.
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "PAUSE GAME");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "RESUME GAME");

    // Key bindings for starting a new game.
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_1, KeyEvent.CTRL_DOWN_MASK), "NEW LEVEL 1");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_2, KeyEvent.CTRL_DOWN_MASK), "NEW LEVEL 2");

  }

}
