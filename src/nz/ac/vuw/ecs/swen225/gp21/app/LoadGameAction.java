package nz.ac.vuw.ecs.swen225.gp21.app;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

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
		return "LoadGameAction";
	}
	
}
