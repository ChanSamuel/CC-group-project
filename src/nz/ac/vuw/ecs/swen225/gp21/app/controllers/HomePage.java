package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomePage extends JPanel implements Page {
	
	JButton loadGameButton = new JButton("Load game");
	JButton newGameButton = new JButton("New game");
	JPanel buttonPanel = new JPanel();
	JLabel heroImageLabel = new JLabel();
	JLabel heroImageTextLabel = new JLabel();
	
	ImageIcon heroImg = new ImageIcon("Assets/images/treasure.png");
	
	public HomePage() {
		setLayout(new GridBagLayout());
        
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
		add(heroImageTextLabel, gbc);
		
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		buttonPanel.add(loadGameButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
		buttonPanel.add(newGameButton);
		
		heroImageLabel.setIcon(heroImg);
		heroImageTextLabel.setText("<html><h1>Chap's Challenge</h1></html>");
		add(buttonPanel);
		
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
