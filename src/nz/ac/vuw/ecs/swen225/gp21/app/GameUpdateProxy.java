package nz.ac.vuw.ecs.swen225.gp21.app;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameEvent;
import nz.ac.vuw.ecs.swen225.gp21.recorder.GameUpdate;

public class GameUpdateProxy implements GameUpdate {
	
	private GameEvent ge;
	
	
	public GameUpdateProxy() {
		
	}
	
	public GameEvent getGe() {
		return ge;
	}

	public GameUpdateProxy(GameEvent ge) {
		this.ge = ge;
	}
	
	@Override
	public long getUpdateIndex() {
		return ge.getUpdateIndex();
	}
	
	public GameEvent getGameEvent() {
		return this.ge;
	}

}
