package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.CardLayout;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp21.app.controllers.GUIController;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Loading;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Running;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

public class LoadLevel2Action implements Action {

	@Override
	public void execute(Controller control) {
		
		try {
			ConcretePersister.loadLevel(2, control.world);
		} catch (Exception e) {
			control.warning("Something went wrong when loading level 2:\n" + e.getMessage());
			return;
		}
		
		if (control.world.getDomainState() instanceof Loading) {
			control.world.doneLoading();
		}
		
		try {
			SwingUtilities.invokeAndWait(() -> {
				
				control.renderer.gameStopped();
				
				control.renderer.init(control.world, 2);
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
		
		control.levelNumber = 2;
		control.gLoop.setIsPlaying(true);
		control.gLoop.setIsReplay(false);
		control.gLoop.setAutoPlay(false);
	}

	@Override
	public String actionName() {
		return "LoadLevel2Action";
	}

}
