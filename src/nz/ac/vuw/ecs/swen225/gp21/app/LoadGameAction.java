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
		} catch (PersistException e) {
			control.warning(e.getMessage());
		}
		control.gLoop.setIsPlaying(true);
		control.gLoop.setIsReplay(false);
		control.gLoop.setAutoPlay(false);
	}

	@Override
	public String actionName() {
		return "LoadGameAction";
	}
	
}
