package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.CardLayout;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp21.app.controllers.GUIController;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Running;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

public class LoadLevel1Action implements Action {

	@Override
	public void execute(Controller control) {
		
		try {
			ConcretePersister.loadLevel(1, control.world);
		} catch (Exception e) {
			control.warning("Something went wrong when loading level 1:\n" + e.getMessage());
			return;
		}
		
		control.world.doneLoading();
		
		try {
			SwingUtilities.invokeAndWait(() -> {
				
				control.renderer.gameStopped();
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
		
		control.levelNumber = 1;
		control.gLoop.setIsPlaying(true);
		control.gLoop.setIsReplay(false);
		control.gLoop.setAutoPlay(false);
	}

	@Override
	public String actionName() {
		return "LoadLevel1Action";
	}

}
