package nz.ac.vuw.ecs.swen225.gp21.renderer;

import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.Item;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.TestWorld;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.domain.items.KeyItem;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.GameOver;

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

//				Domain domain = new TestWorld();

				Domain domain = new World() {
					
					@Override
					public void collectedChip() {
						WrapperJPanel.playSound(SoundType.PICK_UP_A_CHIP);
						System.out.println("Player collected a chip!");
						System.out.println("Remaining Chips: " + (this.totalTreasure - playerEntity.treasureCollected));
					}

					@Override
					public void openedDoor() {
						WrapperJPanel.playSound(SoundType.DOOR_OPEN);
						System.out.println("Chip opened a door!");
					}

					@Override
					public void enteredExit() {
						WrapperJPanel.playSound(SoundType.ENTER_EXIT);
						System.out.println("Player Won!");
						setState(new GameOver());
					}

					@Override
					public void enteredInfo(String msg) {
						WrapperJPanel.playSound(SoundType.SHOW_INFO);
						System.out.println("View -> stared displaying information: " + msg);
					}

					@Override
					public void leftInfo() {
						System.out.println("View -> stopped displaying information");
					}

					@Override
					public void playerLost() {
						System.out.println("Player lost!");
						setState(new GameOver());
					}

					@Override
					public void playerGainedItem(Item item) {
						if (item instanceof KeyItem) {
							WrapperJPanel.playSound(SoundType.PICK_UP_A_KEY);
						}
						System.out.println("Player gained item: " + item);
					}

					@Override
					public void playerConsumedItem(Item item) {
						if (item instanceof KeyItem) {
							WrapperJPanel.playSound(SoundType.DOOR_OPEN);
						}
						System.out.println("Player used item: " + item);
					}

					@Override
					public String toString() {
						return super.toString();
					}

					@Override
					public void objectTeleported() {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void objectPushed() {
						// TODO Auto-generated method stub
						
					}
				};
				domain.loadLevelData(testLevel);
				domain.doneLoading();
				WorldJFrame worldJFrame = new WorldJFrame(domain);
			}
		});
	}
}
