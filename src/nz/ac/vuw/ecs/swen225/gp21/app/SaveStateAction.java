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
		
		if (!control.gameLoop.getIsPlaying()) {
			control.warning("Cannot save unless a game is being played.");
			return;
		}
		
		try {
			control.persister.saveCurrentGame(saveFile, control.levelNumber, control.gameLoop.getTimeLeft());
		} catch (PersistException e) {
			control.warning("Something went wrong when persisting the state:\n" + e.getMessage());
			return;
		}
		control.report("Save successful");
	}

	@Override
	public String actionName() {
		return "SaveAction";
	}

}
