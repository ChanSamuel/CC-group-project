package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePageBackground extends JPanel {
	
	public GamePageBackground() {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height;
		int width = screenSize.width;
		
		Dimension size = new Dimension((int) (0.85*width), (int) (0.75*height));
		setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	}
}
