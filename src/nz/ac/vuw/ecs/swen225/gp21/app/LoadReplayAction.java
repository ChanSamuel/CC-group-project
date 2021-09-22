package nz.ac.vuw.ecs.swen225.gp21.app;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

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
		
		try {
			SwingUtilities.invokeAndWait(() -> {
				control.showPage("Game page");
			});
		} catch (InvocationTargetException e) {
			control.warning("Showing page was interrupted");
		} catch (InterruptedException e) {
			control.warning("Showing page was interrupted");
		}
		
	}

	@Override
	public String actionName() {
		return "LoadReplayAction";
	}

}
