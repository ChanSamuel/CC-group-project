package nz.ac.vuw.ecs.swen225.gp21.app;

import java.io.File;

import nz.ac.vuw.ecs.swen225.gp21.domain.state.Replaying;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

public class LoadReplayAction implements Action {

	
	File f; 
	/**
	 * Construct a LoadReplayAction which will load the given file.
	 * @param f
	 */
	public LoadReplayAction(File f) {
		this.f = f;
	}
	
	@Override
	public void execute(Controller control) {
		
		control.recorder.load(f);
		int levelNumber = control.recorder.getLevel();
		
		try {
			control.persister.loadLevel(levelNumber, control.world);
		} catch (PersistException e) {
			control.warning(e.getMessage());
			return;
		}
		
		control.gLoop.setIsReplay(true);
		control.gLoop.setIsPlaying(true);
		control.world.setState(new Replaying());
	}

	@Override
	public String actionName() {
		return "LoadReplayAction";
	}

}
