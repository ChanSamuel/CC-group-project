package nz.ac.vuw.ecs.swen225.gp21.app.controllers;


import java.util.Scanner;

import nz.ac.vuw.ecs.swen225.gp21.app.Action;
import nz.ac.vuw.ecs.swen225.gp21.app.Controller;

/**
 * A controller that is used as a tester for this module's functionality.
 * @author Sam
 *
 */
public class TextController extends Controller {
	
	/**
	 * Construct a Text Controller.
	 * On construction, will run the main game loop.
	 */
	public TextController() {
		super();		
	}
	
	/**
	 * Run the text controller loop.
	 */
	@Override
	public void run() {
		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.println("Enter either 1 or 2 for an action and 3 to exit");
			String s = scan.nextLine();
			if (s.equals("1")) {
				this.issue(new Action() {

					@Override
					public void execute(Controller control) {
						System.out.println("1 has been executed.");
					}

					@Override
					public String actionName() {
						return null;
					}
				});
			} else if (s.equals("2")) {
				this.issue(new Action() {

					@Override
					public void execute(Controller control) {
						System.out.println("2 has been executed.");
					}

					@Override
					public String actionName() {
						return null;
					}
				});
			} else if (s.equals("3")) {
				this.issue(new Action() {

					@Override
					public void execute(Controller control) {
						System.out.println("Exit has been ordered.");
						System.exit(0);
					}

					@Override
					public String actionName() {
						return null;
					}
				});
			} else {
				throw new RuntimeException("Something went wrong, input was '" + s + "'");
			}
		}
	}

	@Override
	public void chipCollectedTrans() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enteredExitTrans() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enteredInfoTrans() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leftInfoTrans() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerLostTrans() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerGainedItemTrans() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerConsumedItemTrans() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerOpenedADoorTrans() {
		// TODO Auto-generated method stub
		
	}
	
}
