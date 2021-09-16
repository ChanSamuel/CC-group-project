package nz.ac.vuw.ecs.swen225.gp21.app.controllers;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import nz.ac.vuw.ecs.swen225.gp21.renderer.WorldJPanel;

public class GamePage extends JPanel implements Page {
	
	GamePageBackground background = new GamePageBackground();
	JPanel infoPanel = new JPanel();
	JPanel replayPanel = new JPanel();
	
	public GamePage(WorldJPanel renderer) {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height;
		int width = screenSize.width;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipadx = 400;
		gbc.ipady = 400;
		gbc.insets = new Insets(0, 50, 0, 0);
		
		gbc.gridx = 0;
		add(infoPanel, gbc);
		
		gbc.gridx = 1;
		add(renderer, gbc);
		
		gbc.gridx = 2;
		add(replayPanel, gbc);
		
		
		Border panelBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED);
		
		renderer.setBackground(Color.orange);
		renderer.setBorder(panelBorder);
		infoPanel.setBackground(Color.blue);
		infoPanel.setBorder(panelBorder);
		replayPanel.setBackground(Color.yellow);
		replayPanel.setBorder(panelBorder);
		
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
