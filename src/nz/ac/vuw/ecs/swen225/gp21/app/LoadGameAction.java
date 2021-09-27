package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.CardLayout;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp21.app.controllers.GUIController;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

public class LoadGameAction implements Action {

	
	File f;
	
	public LoadGameAction(File f) {
		this.f = f;
	}
	
	@Override
	public void execute(Controller control) {
		try {
			control.persister.loadGame(f);
		} catch (Exception e) {
			control.warning("Something went wrong when loading a previously saved game:\n"+ e.getMessage());
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
			control.warning("Renderer intialisation interrputed");
			return;
		} catch (InterruptedException e) {
			control.warning("Renderer intialisation interrputed");
			return;
		}
		
		control.gLoop.setIsPlaying(true);
		control.gLoop.setIsReplay(false);
		control.gLoop.setAutoPlay(false);
	}

	@Override
	public String actionName() {
		return "LoadGameAction";
	}
	
}
