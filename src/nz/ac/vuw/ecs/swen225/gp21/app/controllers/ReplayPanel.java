package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ReplayPanel extends JPanel {
	
	JButton pausePlay = new JButton("Pause/Play");
	JButton autoPlay = new JButton("Auto-play");
	JPanel speedPanel = new JPanel();
	JLabel setSpeedLabel = new JLabel("Set speed");
	JTextField setSpeedField = new JTextField();
	JPanel stepPanel = new JPanel();
	JButton stepForward = new JButton("+ Step");
	JButton stepBackward = new JButton("- Step");
	
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
