package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
/**
 * This suite of tests makes use of the new level loading functionality
 * @author Benjamin
 *
 */
class LevelLoadingTests {
	/**
	 * Try to load a level
	 */
	@Test
	void testLoad() {
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
		Level testLevel = new Level(rows, columns, tiles, entities, "No Info");
		World w = new World(testLevel);
		w.doneLoading();
		w.moveChipDown();
		w.moveChipDown();
		w.moveChipDown();
		w.moveChipDown();
		w.update(200);
		w.update(200);
		w.update(200);
		w.update(200);
		w.update(200);
		w.update(200);
	}

}
