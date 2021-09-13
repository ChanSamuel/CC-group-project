package nz.ac.vuw.ecs.swen225.gp21.app.controllers;


import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.renderer.WorldJPanel;

public class GamePage extends JPanel implements Page{
	
	GamePageBackground background = new GamePageBackground();
	JPanel infoPanel = new JPanel();
	JPanel replayPanel = new JPanel();
	
	public GamePage(WorldJPanel renderer) {
		add(background);
		
		background.setLayout(new BoxLayout(background, BoxLayout.LINE_AXIS));
		
		background.add(renderer);
		background.add(infoPanel);
		background.add(replayPanel);
		
		infoPanel.setBackground(Color.blue);
		replayPanel.setBackground(Color.yellow);
		
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
