package nz.ac.vuw.ecs.swen225.gp21.app;

import java.util.List;

import nz.ac.vuw.ecs.swen225.gp21.recorder.GameUpdate;
import nz.ac.vuw.ecs.swen225.gp21.recorder.RecorderException;

public class AutoTickAction implements Action {

	@Override
	public void execute(Controller control) {
		
		if (!control.gameLoop.getIsAutoPlay() || !control.gameLoop.getIsReplay() || 
				!control.gameLoop.getIsPlaying()) {
			return;
		}
		
		List<GameUpdate> gameUpdates = null;
		
		try {
			gameUpdates = control.recorder.next();
		} catch (RecorderException e) {
			control.warning("Stepping forward through replay failed because:\n" + e.getMessage());
			return;
		}
		
		long replayTime = -1;
		for (int i = 0; i < gameUpdates.size(); i++) {
			
			GameUpdateProxy gup = null;
			if (gameUpdates.get(i) instanceof GameUpdateProxy) {
				gup = (GameUpdateProxy) gameUpdates.get(i);
				replayTime = gup.getGameEvent().getTimeStamp();
			} else {
				throw new Error("Other GameUpdate instance type not supported!");
			}
			
			control.world.forwardTick(gup.getGameEvent());
		}
		
		if (gameUpdates.size() > 0) {
			control.gameLoop.setReplayTime(replayTime);
		}
		
	}
	
	/**
	 * Execute the action statically.
	 * This is for performance purposes.
	 * @param control
	 */
	public static void executeStatic(Controller control) {
		
		if (!control.gameLoop.getIsAutoPlay() || !control.gameLoop.getIsReplay() || 
				!control.gameLoop.getIsPlaying()) {
			return;
		}
		
		List<GameUpdate> gameUpdates = null;
		
		try {
			gameUpdates = control.recorder.next();
		} catch (RecorderException e) {
			control.warning("Stepping forward through replay failed because:\n" + e.getMessage());
			return;
		}
		
		long replayTime = -1;
		for (int i = 0; i < gameUpdates.size(); i++) {
			
			GameUpdateProxy gup = null;
			if (gameUpdates.get(i) instanceof GameUpdateProxy) {
				gup = (GameUpdateProxy) gameUpdates.get(i);
				replayTime = gup.getGameEvent().getTimeStamp();
			} else {
				throw new Error("Other GameUpdate instance type not supported!");
			}
			
			control.world.forwardTick(gup.getGameEvent());
		}
		
		if (gameUpdates.size() > 0) {
			control.gameLoop.setReplayTime(replayTime);
		}
	}

	@Override
	public String actionName() {
		return "AutoTickAction";
	}

}
