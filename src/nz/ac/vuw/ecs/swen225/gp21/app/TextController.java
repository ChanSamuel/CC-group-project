package nz.ac.vuw.ecs.swen225.gp21.app;

import java.util.Optional;
import java.util.Scanner;

/**
 * A controller that is used as a tester for this module's functionality.
 * @author Sam
 *
 */
public class TextController extends Controller {
	
	/**
	 * Construct a Text Controller.
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
				this.issue((Controller c) -> {
					System.out.println("Action 1 has been executed.");
					return Optional.of(true);
				});
			} else if (s.equals("2")) {
				this.issue((Controller c) -> {
					System.out.println("A different action has been executed.");
					return Optional.of(true);
				});
			} else if (s.equals("3")) {
				this.issue((Controller c) -> {
					System.out.println("Exit has been ordered.");
					System.exit(0);
					return Optional.of(true);
				});
			} else {
				throw new RuntimeException("Something went wrong, input was '" + s + "'");
			}
		}
	}
	
}
