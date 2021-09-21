package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import javax.swing.JFrame;

import nz.ac.vuw.ecs.swen225.gp21.app.Controller;

public class FuzzController extends Controller {

	@Override
	public void run() {
		
	}

	@Override
	protected void warning(String message) {
		System.out.println(message);
	}

	@Override
	protected void report(String message) {
		System.out.println(message);
	}

	@Override
	protected void inform(String message) {
		System.out.println(message);
	}

	@Override
	protected void requestFocus() {
		// Do nothing.
	}
	
	@Override
	public void haltGame() {
		// Do nothing.
	}

	@Override
	public JFrame getFrame() {
		// TODO Auto-generated method stub
		return null;
	}


}
