package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.CardLayout;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp21.app.controllers.GUIController;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Loading;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Running;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

public class LoadLevel1Action implements Action, StartAction {

	@Override
	public void execute(Controller control) {
		
		if (control.world.getDomainState() instanceof Running) {
			control.world.setState(new Loading());
		}
		
		try {
			Persister.loadLevel(1, control.world);
		} catch (Exception e) {
			control.warning("Something went wrong when loading level 1:\n" + e.getMessage());
			return;
		}
		
		if (control.world.getDomainState() instanceof Loading) {
			control.world.doneLoading();
		}
		
		try {
			SwingUtilities.invokeAndWait(() -> {
				
				control.renderer.gameStopped();
				control.renderer.init(control.world, 1);
				if (control instanceof GUIController) {
					
					GUIController gui = (GUIController) control;
					gui.clearTextPanel();
					
					JFrame frame = gui.getFrame();
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
		control.gLoop.setLevelStartTime(60);
		control.gLoop.setToInitialPlayState();
	}

	@Override
	public String actionName() {
		return "LoadLevel1Action";
	}

}
