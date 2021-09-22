package nz.ac.vuw.ecs.swen225.gp21.app;

import java.awt.CardLayout;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp21.app.controllers.GUIController;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Loading;

public class LoadTestLevelAction implements Action {

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
				if (control instanceof GUIController) {
					JFrame frame = ((GUIController) control).getFrame();
					CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
					cl.show(frame.getContentPane(), "Game page");
				}
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
