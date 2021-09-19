package nz.ac.vuw.ecs.swen225.gp21.app;

import java.io.File;

import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

public class LoadGameAction implements Action {

	
	File f;
	
	public LoadGameAction(File f) {
		this.f = f;
	}
	
	@Override
	public void execute(Controller control) {
		try {
			control.persister.loadGame(f, control.world);
			control.gLoop.setIsPlaying(true);
		} catch (PersistException e) {
			e.printStackTrace(); // temporary.
		}
	}

	@Override
	public String actionName() {
		return "LoadGameAction";
	}
	
}
