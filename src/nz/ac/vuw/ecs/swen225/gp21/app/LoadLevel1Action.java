package nz.ac.vuw.ecs.swen225.gp21.app;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

public class LoadLevel1Action implements Action {

	@Override
	public void execute(Controller control) {
		
		try {
			control.persister.loadLevel(1, control.world);
		} catch (PersistException e) {
			control.warning(e.getMessage());
			return;
		}
		
		control.gLoop.setIsPlaying(true);
		control.gLoop.setIsReplay(false);
		control.gLoop.setAutoPlay(false);
		
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
		return "LoadLevel1Action";
	}

}
