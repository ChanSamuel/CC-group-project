package nz.ac.vuw.ecs.swen225.gp21.app;

import nz.ac.vuw.ecs.swen225.gp21.app.controllers.Controller;
import nz.ac.vuw.ecs.swen225.gp21.app.controllers.TextController;

public class Main {
	
	public static void main(String[] args) {
		Controller c = new TextController();
		c.start();
	}

}
