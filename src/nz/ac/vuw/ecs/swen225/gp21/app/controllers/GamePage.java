package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import nz.ac.vuw.ecs.swen225.gp21.renderer.WorldJPanel;
import nz.ac.vuw.ecs.swen225.gp21.renderer.WrapperJPanel;

/**
 * The GamePage is the panel displayed when the user is playing the game.

 * @author chansamu1 300545169
 *
 */
@SuppressWarnings("serial")
public class GamePage extends JPanel implements Page {
  /**
   * The background panel of the GamePage.
   */
  GamePageBackground background = new GamePageBackground();
  /**
   * The side control panel.
   */
  JPanel controlPanel = new JPanel();
  /**
   * The info panel located within the controlPanel.
   */
  JPanel infoPanel = new JPanel();
  /**
   * The timer panel located within the controlPanel.
   */
  JPanel timerPanel = new JPanel();
  /**
   * The replay panel located within the controlPanel.
   */
  ReplayPanel replayPanel = new ReplayPanel();
  /**
   * The time left label which displays the time left currently.
   */
  JLabel timeLeftLabel = new JLabel("60");
  /**
   * The text area used to display event messages.
   */
  JTextArea textArea = new JTextArea(15, 17);
  /**
   * The scroll pane which contains the text area.
   */
  JScrollPane scrollPane = new JScrollPane(textArea);

  /**
   * Construct the gamePage, adding in the renderer and setting the layout.

   * @param renderer : the renderer panel representing the maze.
   */
  public GamePage(WrapperJPanel renderer) {
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.ipadx = WorldJPanel.TILE_WIDTH * 9;
    gbc.ipady = WorldJPanel.TILE_HEIGHT * 9;
    gbc.insets = new Insets(0, 25, 0, 0);

    gbc.gridx = 0;
    add(renderer, gbc);

    gbc.gridx = 1;
    gbc.ipadx = 5;
    gbc.ipady = 0;
    gbc.anchor = GridBagConstraints.PAGE_START;
    add(controlPanel, gbc);

    controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
    controlPanel.add(replayPanel);
    controlPanel.add(timerPanel);
    controlPanel.add(infoPanel);

    replayPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    timerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

    timerPanel.add(timeLeftLabel);
    timeLeftLabel.setFont(timeLeftLabel.getFont().deriveFont(45f));

    infoPanel.add(scrollPane);
    textArea.setEditable(false);

    Border panelBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED);
    controlPanel.setBorder(panelBorder);

  }

  @Override
  public JPanel getPanel() {
    return this;
  }

  @Override
  public String getInformalName() {
    return "Game page";
  }
}
