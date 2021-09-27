package nz.ac.vuw.ecs.swen225.gp21.app;

import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp21.domain.state.Loading;

public class NextLevelAction implements Action {

	@Override
	public void execute(Controller control) {
		control.gLoop.setIsPlaying(false);
		
		// Depending on the level, issue the corresponding action to move to that level.
		if (control.levelNumber == 0) { // On test level
			control.report("You completed this level congratulations!\n"
					+ "Proceeding to level 1.");
			new LoadLevel1Action().execute(control);
		} else if (control.levelNumber == 1) {
			control.report("You completed this level congratulations!\n"
					+ "Proceeding to level 2.");
			new LoadLevel2Action().execute(control);
		} else if (control.levelNumber == 2) {
			control.report("You completed this level congratulations!");
		} else {
			throw new Error("There should only be 2 real levels and a test level!");
		}
	}

	@Override
	public String actionName() {
		return "NextLevelAction";
	}

}
