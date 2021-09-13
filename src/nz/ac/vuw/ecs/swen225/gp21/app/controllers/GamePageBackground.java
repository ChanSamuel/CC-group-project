package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePageBackground extends JPanel {
	private Image img;
	
	public GamePageBackground() {
		try {
			img = ImageIO.read(new File(System.getProperty("user.dir") + "\\Assets\\images\\treasure.png"));
		} catch (IOException e) {
			throw new Error("Could not read game page background image file!", e);
		}
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
	}
}
