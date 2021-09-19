package nz.ac.vuw.ecs.swen225.gp21.app;

import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

public class LoadLevel1Action implements Action {

	@Override
	public void execute(Controller control) {
		
		try {
			control.persister.loadLevel(1, control.world);
		} catch (PersistException e) {
			e.printStackTrace(); // Temporary.
		}
		
		control.gLoop.setIsPlaying(true);
	}

	@Override
	public String actionName() {
		return "LoadLevel1Action";
	}

}
