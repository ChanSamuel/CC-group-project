package nz.ac.vuw.ecs.swen225.gp21.app;

import java.util.List;
import nz.ac.vuw.ecs.swen225.gp21.recorder.GameUpdate;
import nz.ac.vuw.ecs.swen225.gp21.recorder.RecorderException;

public class BackTickAction implements Action, AdvanceTickAction {

	
	@Override
	public void execute(Controller control) {
		
		if (!control.gLoop.getIsPlaying()) {
			control.warning("Cannot step through replay unless playing a game");
			return;
		}
		
		if (!control.gLoop.getIsReplay()) {
			control.warning("Cannot step through replay when not in replay.");
			return;
		}
		
		if (control.gLoop.getIsAutoPlay()) {
			control.warning("Can't manually do next tick during autoplay");
			return;
		}
		
		List<GameUpdate> gameUpdates = null;
		
		try {
			gameUpdates = control.recorder.prev();
		} catch (RecorderException e) {
			control.warning("Stepping backward through replay failed because:\n" + e.getMessage());
			return;
		}
		
		for (int i = 0; i < gameUpdates.size(); i++) {
			
			GameUpdateProxy gup = null;
			if (gameUpdates.get(i) instanceof GameUpdateProxy) {
				gup = (GameUpdateProxy) gameUpdates.get(i);
			} else {
				throw new Error("Other GameUpdate instance type not supported!");
			}
			
			control.world.backTick(gup.getGameEvent());
		}
		
	}

	@Override
	public String actionName() {
		return "BackTickAction";
	}

}
