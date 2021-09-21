package nz.ac.vuw.ecs.swen225.gp21.app;

import java.io.File;

import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

public class SaveStateAction implements Action {

	private File saveFile;
	
	public SaveStateAction(File saveFile) {
		this.saveFile = saveFile;
	}
	
	
	@Override
	public void execute(Controller control) {
		
		if (!control.gLoop.getIsPlaying()) {
			control.warning("Cannot save unless a game is being played.");
			return;
		}
		
		try {
			control.report("Attempting to save " + saveFile.getPath() + "\nPress 'OK' and wait.");
			control.persister.saveCurrentGame(saveFile, control.world);
		} catch (PersistException e) {
			control.warning(e.getMessage());
		}
	}

	@Override
	public String actionName() {
		return "SaveAction";
	}

}
