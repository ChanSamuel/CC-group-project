package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import nz.ac.vuw.ecs.swen225.gp21.app.Controller;

public class Keyboard implements KeyListener{
	
	Controller control;
	
	public Keyboard(Controller control) {
		this.control = control;
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		System.out.println("dddddddd");
		int code = ke.getKeyCode();
		switch (code) {
			case KeyEvent.VK_UP:
				control.moveUp();
				break;
			case KeyEvent.VK_DOWN:
				control.moveDown();
				break;
			case KeyEvent.VK_LEFT:
				control.moveLeft();
				break;
			case KeyEvent.VK_RIGHT:
				control.moveRight();
				break;
			case KeyEvent.VK_W:
				control.moveUp();
				break;
			case KeyEvent.VK_S:
				control.moveDown();
				break;
			case KeyEvent.VK_A:
				control.moveLeft();
				break;
			case KeyEvent.VK_D:
				control.moveRight();
				break;
			default:
				break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// pass
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// pass
		
	}

}
