package nz.ac.vuw.ecs.swen225.gp21.app;

import java.io.File;

public class SaveReplayAction implements Action {

	private File saveFile;
	
	public SaveReplayAction(File saveFile) {
		this.saveFile = saveFile;
	}
	
	@Override
	public void execute(Controller control) {
		
		if (!control.gLoop.getIsPlaying()) {
			control.warning("Cannot save unless a game is being played.");
			return;
		}
		
		if (control.gLoop.getIsReplay()) {
			control.warning("Can't save a replay whilst in a replay.");
			return;
		}
		
		control.report("Attempting to save " + saveFile.getPath() + "\nPress 'OK' and wait.");
		if (control.recorder.save(saveFile)) {
			control.report("Save successful");
		} else {
			control.warning("Save unsuccessful");
		}
	}

	@Override
	public String actionName() {
		return "SaveReplayAction";
	}

}
