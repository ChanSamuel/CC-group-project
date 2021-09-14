package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomePage extends JPanel implements Page {
	
	String[] levels = {"Level 1", "Level 2"};
	
	JButton loadGameButton = new JButton("Load game");
	JButton newGameButton = new JButton("New game");
	JComboBox levelChooser = new JComboBox(levels);
	JPanel buttonPanel = new JPanel();
	JLabel heroImageLabel = new JLabel();
	JLabel heroImageTextLabel = new JLabel();
	
	ImageIcon heroImg = new ImageIcon("Assets/images/treasure.png");
	
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
		
		
		heroImageLabel.setIcon(heroImg);
		heroImageTextLabel.setText("Chap's Challenge");
		heroImageTextLabel.setFont(heroImageTextLabel.getFont().deriveFont(50f));
		add(buttonPanel);
		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		loadGameButton.setPreferredSize(new Dimension((int)(0.2 * screenSize.getWidth()), (int)(0.08 * screenSize.getHeight())));
		newGameButton.setPreferredSize(new Dimension((int)(0.2 * screenSize.getWidth()), (int)(0.08 * screenSize.getHeight())));
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
