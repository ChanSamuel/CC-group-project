package nz.ac.vuw.ecs.swen225.gp21.app;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp21.domain.Item;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Loading;
import nz.ac.vuw.ecs.swen225.gp21.renderer.SoundType;
import nz.ac.vuw.ecs.swen225.gp21.renderer.WrapperJPanel;

public class LoadTestLevelAction implements Action {

	public static int times = 0;
	
	@Override
	public void execute(Controller control) {
		Level testLevel;
        int rows = 12;
        int columns = 22;
        String tiles = "";
        tiles += "######################";
        tiles += "#......#.c.#.c..#....#";
        tiles += "#......#..g#.u..G.>..#";
        tiles += "#v#....#S########.^<.#";
        tiles += "#.#...s....i....###^v#";
        tiles += "#1#..................#";
        tiles += "######################";
        tiles += "#1#.a.#.........A....#";
        tiles += "#.#...#.........####X#";
        tiles += "#v##U##............#.#";
        tiles += "#...............ccc#E#";
        tiles += "######################";
        String entities = "";
        entities += "......................";
        entities += ".C....................";
        entities += "......................";
        entities += "...B..................";
        entities += "......................";
        entities += "......................";
        entities += "......................";
        entities += "......................";
        entities += "......................";
        entities += "......................";
        entities += "......................";
        entities += "......................";
        testLevel = new Level(rows, columns, tiles, entities, "No Info");
		
		control.world.setState(new Loading());
		control.world.loadLevelData(testLevel);
		control.world.doneLoading();
		
		
		try {
			SwingUtilities.invokeAndWait(() -> {
				control.renderer.init(control.world, 1);
				control.showPage("Game page");
			});
		} catch (InvocationTargetException e) {
			control.warning("Showing page was interrupted");
		} catch (InterruptedException e) {
			control.warning("Showing page was interrupted");
		}
		control.gLoop.setIsPlaying(true);
		control.gLoop.setIsReplay(false);
		control.gLoop.setAutoPlay(false);
		
		if (this.times == 1) {
			control.renderer.makeSpecial();
		}
		
		this.times++;
		
	}

	@Override
	public String actionName() {
		return "LoadTestLevelAction";
	}

}
