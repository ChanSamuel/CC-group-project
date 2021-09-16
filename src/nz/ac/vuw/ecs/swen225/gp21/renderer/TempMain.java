package nz.ac.vuw.ecs.swen225.gp21.renderer;

import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.Item;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.TestWorld;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;

/**
 * TODO This is a temp main method created for testing GUI.
 * 
 * @author mengli
 *
 */
public class TempMain {

	public static void main(String arg[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Level testLevel;
				int rows = 10;
				int columns = 20;
				String tiles = "";
				tiles += "......#.c.#.c..#....";
				tiles += "......#..g#.u..G....";
				tiles += "v#....#S########.^<<";
				tiles += ".#...s....i....####^";
				tiles += "1#.............>....";
				tiles += "####################";
				tiles += "1#.a.#.........A....";
				tiles += ".#...#.........####X";
				tiles += "v##U##............#.";
				tiles += "...............ccc#E";
				String entities = "";
				entities += "C...................";
				entities += "....................";
				entities += "..B.................";
				entities += "....................";
				entities += "....................";
				entities += "....................";
				entities += "....................";
				entities += "....................";
				entities += "....................";
				entities += "....................";
				testLevel = new Level(rows, columns, tiles, entities, "No Info");
				
				Domain domain = new TestWorld();
				domain.loadLevelData(testLevel);
				domain.doneLoading();
//				domain = new World() {
//
//					@Override
//					public void collectedAChip() {
//						// TODO Auto-generated method stub
//						
//					}
//
//					@Override
//					public void enteredExit() {
//						// TODO Auto-generated method stub
//						
//					}
//
//					@Override
//					public void enteredInfo(String msg) {
//						// TODO Auto-generated method stub
//						
//					}
//
//					@Override
//					public void leftInfo() {
//						// TODO Auto-generated method stub
//						
//					}
//
//					@Override
//					public void playerLost() {
//						// TODO Auto-generated method stub
//						
//					}
//
//					@Override
//					public void playerGainedItem(Item item) {
//						// TODO Auto-generated method stub
//						
//					}
//
//					@Override
//					public void playerConsumedItem(Item item) {
//						// TODO Auto-generated method stub
//						
//					}};
				WorldJFrame worldJFrame = new WorldJFrame(domain);
			}
		});
	}
}
