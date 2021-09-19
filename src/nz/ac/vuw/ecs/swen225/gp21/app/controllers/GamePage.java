package nz.ac.vuw.ecs.swen225.gp21.app.controllers;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import nz.ac.vuw.ecs.swen225.gp21.renderer.WorldJPanel;

public class GamePage extends JPanel implements Page {
	
	GamePageBackground background = new GamePageBackground();
	JPanel controlPanel = new JPanel();
	JPanel infoPanel = new JPanel();
	ReplayPanel replayPanel = new ReplayPanel();
	JPanel wrapperPanel = new JPanel();
	
	public GamePage(WorldJPanel renderer) {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipadx = WorldJPanel.TILE_WIDTH * 9;
		gbc.ipady = WorldJPanel.TILE_HEIGHT * 9;
		gbc.insets = new Insets(0, 25, 0, 0);
		
		gbc.gridx = 0;
		add(wrapperPanel, gbc);
		wrapperPanel.add(renderer);
		wrapperPanel.setLayout(null);
		
		
		gbc.gridx = 1;
		gbc.ipadx = 5;
		gbc.ipady = 0;
		gbc.anchor = GridBagConstraints.PAGE_START;
		add(controlPanel, gbc);
		
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
		controlPanel.add(replayPanel);
		controlPanel.add(infoPanel);
		
		replayPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Border panelBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED);
		
		wrapperPanel.setBorder(panelBorder);
		controlPanel.setBorder(panelBorder);
		infoPanel.setBackground(Color.blue);
		
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
