package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Block;
/**
 * This suite of tests makes use of the new level loading functionality
 * @author Benjamin
 *
 */
class LevelLoadingTests {
	/**
	 * Tests will use this testLevel
	 */
	Level testLevel;
	{
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
	}
	
	/**
	 * Try to load a level
	 */
	@Test
	void testLoad() {
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
	
	/**
	 * Test that one way tiles work
	 */
	@Test
	void testLoadTwo() {
		World w = new World(testLevel);
		w.doneLoading();
		w.moveChipDown();
		w.moveChipDown();
		w.moveChipDown();
		w.update(200);
		w.update(200);
		w.update(200);
		w.moveChipUp();
		w.update(200);
		String expected = "Is game over? -> false\n"
				+ "PlayerQueue: \n"
				+ "EMPTY\n"
				+ "All entities: \n"
				+ "GameObject: Chip facing->NORTH at->Row: 3 Columns: 0 Chip Chip's Invetory: []\n"
				+ "GameObject: Block facing->NONE at->Row: 2 Columns: 2 Block\n"
				+ "\n"
				+ "Board: \n"
				+ "0|_|_|_|_|_|_|_|_|_|_|\n"
				+ "1|_|_|_|_|_|_|_|_|_|_|\n"
				+ "2|v|#|=|_|_|_|_|_|_|_|\n"
				+ "3|C|#|_|_|_|_|_|_|_|_|\n"
				+ "4|O|#|_|_|_|_|_|_|_|_|\n"
				+ "5|#|#|#|#|#|#|#|#|#|#|\n"
				+ "6|O|#|_|_|_|_|_|_|_|_|\n"
				+ "7|_|#|_|_|_|_|_|_|#|X|\n"
				+ "8|v|#|_|_|_|_|_|_|#|_|\n"
				+ "9|_|_|_|_|_|c|c|c|#|e|\n";
		
		assertEquals(expected, w.toString());
	}
	
	/**
	 * Test that one way tiles work
	 */
	@Test
	void testLoadThree() {
		World w = new World(testLevel);
		w.addGameObject(new Block(w), new Coord(1,1));
		w.doneLoading();
		w.moveChipRight(); w.moveChipRight();
		w.moveChipDown();
		w.moveChipLeft();
		w.moveChipUp();
		w.moveChipLeft();
		w.moveChipDown(); w.moveChipDown(); w.moveChipDown(); w.moveChipDown(); w.moveChipDown();
		for(int runs = 0; runs < 11; runs++) { w.update(200); }
		
		String expected = "Is game over? -> false\n"
				+ "PlayerQueue: \n"
				+ "EMPTY\n"
				+ "All entities: \n"
				+ "GameObject: Chip facing->SOUTH at->Row: 8 Columns: 0 Chip Chip's Invetory: []\n"
				+ "GameObject: Block facing->NONE at->Row: 2 Columns: 2 Block\n"
				+ "GameObject: Block facing->NONE at->Row: 9 Columns: 0 Block\n"
				+ "\n"
				+ "Board: \n"
				+ "0|_|_|_|_|_|_|_|_|_|_|\n"
				+ "1|_|_|_|_|_|_|_|_|_|_|\n"
				+ "2|v|#|=|_|_|_|_|_|_|_|\n"
				+ "3|_|#|_|_|_|_|_|_|_|_|\n"
				+ "4|O|#|_|_|_|_|_|_|_|_|\n"
				+ "5|#|#|#|#|#|#|#|#|#|#|\n"
				+ "6|O|#|_|_|_|_|_|_|_|_|\n"
				+ "7|_|#|_|_|_|_|_|_|#|X|\n"
				+ "8|C|#|_|_|_|_|_|_|#|_|\n"
				+ "9|=|_|_|_|_|c|c|c|#|e|\n";
		
		assertEquals(expected, w.toString());
		assertEquals(11, w.updates);
	}
	

}
