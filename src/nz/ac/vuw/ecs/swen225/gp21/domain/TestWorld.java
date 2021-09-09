package nz.ac.vuw.ecs.swen225.gp21.domain;

import nz.ac.vuw.ecs.swen225.gp21.domain.state.GameOver;

/**
 * Create a test world for unit testing.
 * Prints out simple messages when events happen.
 * @author Benjamin
 *
 */
public final class TestWorld extends World {
	/**
	 * Create a new test world.
	 * World is uninitialized
	 * Expect test world to be loaded properly.
	 */
	public TestWorld() { super(); }
	
	//I am expecting the App to provide its own definition of these methods.
	//There, it can do useful stuff like:
	//	update the view after an event.
	//	Tell the recorder that the game ended.
	//	and more.
	@Override
	public void collectedAChip() {
		System.out.println("Player collected a chip!");
		System.out.println("Remaining Chips: "+(this.totalTreasure - playerEntity.treasureCollected));
	}

	@Override
	public void enteredExit() {
		System.out.println("Player Won!");
		setState(new GameOver());
	}

	@Override
	public void enteredInfo(String msg) {
		System.out.println("View -> stared displaying information: "+msg);
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
		System.out.println("Player gained item: "+item);
	}

	@Override
	public void playerConsumedItem(Item item) {
		System.out.println("Player used item: "+item);
	}
}
