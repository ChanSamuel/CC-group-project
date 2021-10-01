package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The ReplayPanel holds all the components for interacting with the current
 * replay.

 * @author chansamu1 300545169
 *
 */
@SuppressWarnings("serial")
public class ReplayPanel extends JPanel {

  /**
   * Pause button for pausing the game.
   */
  JButton pausePlay = new JButton("Pause/Play");
  /**
   * Button which toggles the auto play feature for the replay.
   */
  JButton autoPlay = new JButton("Auto-play");
  /**
   * A panel which contains the speed label and text field for setting the speed.
   */
  JPanel speedPanel = new JPanel();
  /**
   * The label for setting the speed.
   */
  JLabel setSpeedLabel = new JLabel("Set speed");
  /**
   * The text field which sets the speed on enter press.
   */
  JTextField setSpeedField = new JTextField();
  /**
   * Panel which contains the buttons for stepping through the recording.
   */
  JPanel stepPanel = new JPanel();
  /**
   * Button which steps forward through the recording.
   */
  JButton stepForward = new JButton("+ Step");
  /**
   * Button which steps backwards through the recording.
   */
  JButton stepBackward = new JButton("- Step");

  /**
   * Construct the ReplayPanel. Adds the components and panels, and set the layout
   * and sizes.
   */
  public ReplayPanel() {
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    add(Box.createRigidArea(new Dimension(0, 10)));
    add(pausePlay);
    add(Box.createRigidArea(new Dimension(0, 10)));
    add(autoPlay);
    add(speedPanel);
    add(stepPanel);

    speedPanel.add(setSpeedLabel);
    speedPanel.add(setSpeedField);
    stepPanel.add(stepBackward);
    stepPanel.add(stepForward);

    setSpeedField.setPreferredSize(new Dimension(100, 30));

    pausePlay.setAlignmentX(Component.CENTER_ALIGNMENT);
    autoPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
    speedPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    stepPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

    pausePlay.setFont(pausePlay.getFont().deriveFont(12f));
    autoPlay.setFont(autoPlay.getFont().deriveFont(12f));
    setSpeedLabel.setFont(setSpeedLabel.getFont().deriveFont(12f));

  }

}
