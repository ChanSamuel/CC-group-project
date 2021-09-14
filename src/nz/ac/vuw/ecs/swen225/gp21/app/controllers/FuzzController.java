package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import nz.ac.vuw.ecs.swen225.gp21.app.Controller;

public class FuzzController extends Controller {

	@Override
	public void run() {
		
	}

	@Override
	public void chipCollectedTrans() {
		System.out.println("Chip collected");
		
	}

	@Override
	public void enteredExitTrans() {
		System.out.println("Entered an exit tile");
		
	}

	@Override
	public void enteredInfoTrans() {
		System.out.println("Entered an info tile");
		
	}

	@Override
	public void leftInfoTrans() {
		System.out.println("Exited an info tile");
		
	}

	@Override
	public void playerLostTrans() {
		System.out.println("Player has lost!");
		
	}

	@Override
	public void playerGainedItemTrans() {
		System.out.println("Gained an item");
		
	}

	@Override
	public void playerConsumedItemTrans() {
		System.out.println("Consumed an item");
	}

	@Override
	public void playerOpenedADoorTrans() {
		System.out.println("Player opened a door");
		
	}


}
