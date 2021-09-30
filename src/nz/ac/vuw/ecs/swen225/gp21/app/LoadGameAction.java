package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.CardLayout;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp21.app.controllers.GUIController;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Loading;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Running;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

public class LoadGameAction implements Action, StartAction {

	
	File f;
	
	public LoadGameAction(File f) {
		this.f = f;
	}
	
	@Override
	public void execute(Controller control) {
		
		if (control.world.getDomainState() instanceof Running) {
			control.world.setState(new Loading());
		}
		
		int[] levelAndTime = null;
		try {
			levelAndTime = control.persister.loadGame(f);
		} catch (Exception e) {
			control.warning("Something went wrong when loading a previously saved game:\n"+ e.getMessage());
			return;
		}
		
		// Reset the current recording.
		control.recorder.clear();
		
		control.levelNumber = levelAndTime[0];
		int timeLeft = levelAndTime[1];
		
		try {
			SwingUtilities.invokeAndWait(() -> {
				control.renderer.gameStopped();
				control.renderer.init(control.world, control.levelNumber);
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
		
		control.gLoop.setToInitialPlayState();
		control.gLoop.setLevelStartTime(timeLeft);
	}

	@Override
	public String actionName() {
		return "LoadGameAction";
	}
	
}
