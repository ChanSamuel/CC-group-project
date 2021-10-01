package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The HomePage adds the components and sets the layout for the main menu page.

 * @author chansamu1 300545169
 *
 */
@SuppressWarnings("serial")
public class HomePage extends JPanel implements Page {

  /**
   * The array of possible levels.
   */
  String[] levels = { "Level 1", "Level 2", "TEST LEVEL" };

  /**
   * A button which can load a saved game.
   */
  JButton loadGameButton = new JButton("Load game");
  /**
   * A button which will load a new level.
   */
  JButton newGameButton = new JButton("New game");
  /**
   * A selector for choosing the level to start playing.
   */
  JComboBox<String> levelChooser = new JComboBox<>(levels);
  /**
   * The panel for organising all the loading and new game buttons.
   */
  JPanel buttonPanel = new JPanel();
  /**
   * The title card label.
   */
  JLabel heroImageTextLabel = new JLabel();

  /**
   * Construct the HomePage. This will add the components using the right layout,
   * and configure the sizes.
   */
  public HomePage() {
    setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.anchor = GridBagConstraints.NORTH;
    gbc.insets = new Insets(0, 0, 200, 0);
    add(heroImageTextLabel, gbc);

    buttonPanel.setLayout(new BorderLayout(0, 15));
    buttonPanel.add(loadGameButton, BorderLayout.PAGE_START);
    buttonPanel.add(newGameButton, BorderLayout.CENTER);
    buttonPanel.add(levelChooser, BorderLayout.PAGE_END);

    heroImageTextLabel.setText("Chap's Challenge");
    heroImageTextLabel.setFont(heroImageTextLabel.getFont().deriveFont(50f));
    add(buttonPanel);

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    loadGameButton
        .setPreferredSize(new Dimension((int) (0.2 * screenSize.getWidth()),
            (int) (0.08 * screenSize.getHeight())));
    newGameButton
        .setPreferredSize(new Dimension((int) (0.2 * screenSize.getWidth()),
            (int) (0.08 * screenSize.getHeight())));
  }

  @Override
  public JPanel getPanel() {
    return this;
  }

  @Override
  public String getInformalName() {
    return "Home page";
  }

}
