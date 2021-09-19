package nz.ac.vuw.ecs.swen225.gp21.app;

public class NewGameAction implements Action {

	private static final String[] levels = {"Level 1", "Level 2", "TEST LEVEL"};
	String levelChoice;
	
	public NewGameAction(String levelChoice) {
		this.levelChoice = levelChoice;
	}
	
	@Override
	public void execute(Controller control) {
		
		if (levelChoice.equals(levels[0])) {
			control.loadLevel1();
		} else if (levelChoice.equals(levels[1])) {
			control.loadLevel2();
		} else if (levelChoice.equals(levels[2])) {
			control.loadTestLevel();
		} else {
			throw new Error("Level not recognised!");
		}
		
	}

	@Override
	public String actionName() {
		return "NewGameAction";
	}

}
