package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp21.app.Controller;

/**
 * GUI is an abstract class which exposes all components available in the GUI as a field.
 * @author Sam
 *
 */
public abstract class GUI extends Controller {
	GUIFrame frame;
	
	// Game page components.
	GamePage gamePage;
	
	
	// home page components.
	HomePage homePage;
	
	/**
	 * Construct a GUI.
	 * Nothing is initialised until run() is called.
	 */
	public GUI() {
		super();
	}
	
	@Override
	public void run() {
		try {
			SwingUtilities.invokeAndWait(() -> {
				gamePage = new GamePage(renderer);
				homePage = new HomePage();
				
				List<Page> pages = new ArrayList<Page>();
				pages.add(homePage);
				pages.add(gamePage);
				
				frame = new GUIFrame(this, pages);
				
			});
		} catch (InvocationTargetException e) {
			throw new Error("Frame Construction invocation error!", e);
		} catch (InterruptedException e) {
			throw new Error("Frame construction interrupted!", e);
		}
	}

}
