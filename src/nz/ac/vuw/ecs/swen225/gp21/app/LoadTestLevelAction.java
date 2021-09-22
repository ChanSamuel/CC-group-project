package nz.ac.vuw.ecs.swen225.gp21.app;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Loading;

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
		
		control.world.setState(new Loading());
		control.world.loadLevelData(testLevel);
		control.world.doneLoading();
		
		try {
			SwingUtilities.invokeAndWait(() -> {
				control.renderer.init(control.world, 1);
			});
		} catch (InvocationTargetException e) {
			control.warning("Renderer intialisation interrputed");;
		} catch (InterruptedException e) {
			control.warning("Renderer intialisation interrputed");
		}
		
		control.gLoop.setIsPlaying(true);
		control.gLoop.setIsReplay(false);
		control.gLoop.setAutoPlay(false);
		
	}

	@Override
	public String actionName() {
		return "LoadTestLevelAction";
	}

}
