package nz.ac.vuw.ecs.swen225.gp21.app;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

public class ExitToMenuAction implements Action {

	@Override
	public void execute(Controller control) {
		control.gLoop.setIsPlaying(false);
		
		try {
			SwingUtilities.invokeAndWait(() -> {
				control.showPage("Home page");
			});
		} catch (InvocationTargetException e) {
			control.warning("Showing page was interrupted");
		} catch (InterruptedException e) {
			control.warning("Showing page was interrupted");
		}
	}

	@Override
	public String actionName() {
		return "ExitToMenuAction";
	}

}
