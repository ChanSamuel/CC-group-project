package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import javax.swing.JFrame;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.app.Controller;

/**
 * GUI is an abstract class which exposes all components available in the GUI as a field.
 * It's fields are accessed by Transition to transform the GUI with.
 * @author Sam
 *
 */
public abstract class GUI extends Controller {
	JFrame frame;
	JPanel infoPanel;
	
	/**
	 * Construct a GUI.
	 * Nothing is initialised until run() is called.
	 */
	public GUI() {
		super();
	}
	
	@Override
	public void run() {
		// Do some intialisation and add components to the right places.
		
	}

}
