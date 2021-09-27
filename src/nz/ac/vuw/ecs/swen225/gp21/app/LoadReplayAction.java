package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.CardLayout;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp21.app.controllers.GUIController;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Loading;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Replaying;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Running;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;
import nz.ac.vuw.ecs.swen225.gp21.recorder.RecorderException;

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
		
		if (control.world.getDomainState() instanceof Running) {
			control.world.setState(new Loading());
		}
		
		try {
			control.recorder.load(f);
		} catch (RecorderException e1) {
			control.warning("Something went wrong when loading the replay:\n" + e1.getMessage());
			return;
		}
		
		int levelNumber = control.recorder.getLevel();
		
		try {
			ConcretePersister.loadLevel(levelNumber, control.world);
		} catch (PersistException e) {
			control.warning("Something went wrong when persisting the level:\n" + e.getMessage());
			return;
		}
		
		if (control.world.getDomainState() instanceof Loading) {
			control.world.doneLoading();
		}
		
		try {
			SwingUtilities.invokeAndWait(() -> {
				control.renderer.init(control.world, levelNumber);
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
		
		control.gLoop.setIsReplay(true);
		control.gLoop.setIsPlaying(true);
		control.world.setState(new Replaying());
	}

	@Override
	public String actionName() {
		return "LoadReplayAction";
	}

}
