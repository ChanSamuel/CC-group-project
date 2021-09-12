package nz.ac.vuw.ecs.swen225.gp21.app.actions;

import java.util.Optional;

import nz.ac.vuw.ecs.swen225.gp21.app.controllers.Controller;

public interface Action {
	
	public Optional<Boolean> execute(Controller control);
}
