package nz.ac.vuw.ecs.swen225.gp21.app;

import java.io.File;

import nz.ac.vuw.ecs.swen225.gp21.recorder.RecorderException;

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
		
		try {
			control.recorder.save(saveFile);
		} catch (RecorderException e) {
			control.warning("Something went wrong with saving the replay:\n" + e.getMessage());
			return;
		}
		
		control.report("Save successful");
	}

	@Override
	public String actionName() {
		return "SaveReplayAction";
	}

}
