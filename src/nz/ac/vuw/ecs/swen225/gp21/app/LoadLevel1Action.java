package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.CardLayout;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp21.app.controllers.GUIController;
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
		
		try {
			SwingUtilities.invokeAndWait(() -> {
				control.renderer.init(control.world, 1);
				if (control instanceof GUIController) {
					JFrame frame = ((GUIController) control).getFrame();
					CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
					cl.show(frame.getContentPane(), "Game page");
				}
			});
		} catch (InvocationTargetException e) {
			control.warning("Renderer intialisation interrputed");;
		} catch (InterruptedException e) {
			control.warning("Renderer intialisation interrputed");
		}
		
		control.gLoop.setIsPlaying(true);
		control.gLoop.setIsReplay(false);
		control.gLoop.setAutoPlay(false);
	}

	@Override
	public String actionName() {
		return "LoadLevel1Action";
	}

}
