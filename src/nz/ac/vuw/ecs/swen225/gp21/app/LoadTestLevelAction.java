package nz.ac.vuw.ecs.swen225.gp21.app;

import nz.ac.vuw.ecs.swen225.gp21.domain.Level;

public class LoadTestLevelAction implements Action {

	@Override
	public void execute(Controller control) {
		Level testLevel;
		int rows = 10;
		int columns = 10;
		String tiles = "";
		tiles += "..........";
		tiles += "..........";
		tiles += "v#........";
		tiles += ".#........";
		tiles += "1#........";
		tiles += "##########";
		tiles += "1#........";
		tiles += ".#......#X";
		tiles += "v#......#.";
		tiles += ".....ccc#E";
		String entities = "";
		entities += "C.........";
		entities += "..........";
		entities += "..B.......";
		entities += "..........";
		entities += "..........";
		entities += "..........";
		entities += "..........";
		entities += "..........";
		entities += "..........";
		entities += "..........";
		testLevel = new Level(rows, columns, tiles, entities, "No Info");
		
		control.world.loadLevelData(testLevel);
		control.world.doneLoading();
		control.renderer.setDomain(control.world);
		control.gLoop.setIsPlaying(true);
		
	}

	@Override
	public String actionName() {
		return "LoadTestLevelAction";
	}

}
