package nz.ac.vuw.ecs.swen225.gp21.app;

import java.io.File;

import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

public class ExitSaveAction implements Action{

	private File saveFile;
	
	public ExitSaveAction(File saveFile) {
		this.saveFile = saveFile;
	}
	
	@Override
	public void execute(Controller control) {
		try {
			control.report("Attempting to save " + saveFile.getPath() + "\nPress 'OK' and wait.");
			control.persister.saveCurrentGame(saveFile, control.world);
		} catch (PersistException e) {
			control.warning(e.getMessage());
			return;
		}
		
		// Close all threads and exit.
		System.exit(0);
	}

	@Override
	public String actionName() {
		return "ExitSaveAction";
	}

}
