package nz.ac.vuw.ecs.swen225.gp21.app.controllers;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.renderer.WorldJPanel;

public class GamePage extends JPanel implements Page {
	
	GamePageBackground background = new GamePageBackground();
	JPanel infoPanel = new JPanel();
	JPanel replayPanel = new JPanel();
	
	public GamePage(WorldJPanel renderer) {
		setLayout(new GridBagLayout());
        
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
		add(background, gbc);
		
		background.setLayout(new BorderLayout(50, 0));
		
		background.add(renderer, BorderLayout.LINE_START);
		background.add(infoPanel, BorderLayout.CENTER);
		background.add(replayPanel, BorderLayout.LINE_END);
		
		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height;
		int width = screenSize.width;
		
		renderer.setBackground(Color.orange);
		renderer.setPreferredSize(new Dimension((int) (0.4 * width), (int) (0.70 * height)));
		
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
