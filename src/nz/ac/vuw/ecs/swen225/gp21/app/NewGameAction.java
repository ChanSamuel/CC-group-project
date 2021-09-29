package nz.ac.vuw.ecs.swen225.gp21.app;

public class NewGameAction implements Action, StartAction {

	private static final String[] levels = {"Level 1", "Level 2", "TEST LEVEL"};
	String levelChoice;
	
	public NewGameAction(String levelChoice) {
		this.levelChoice = levelChoice;
	}
	
	@Override
	public void execute(Controller control) {
		
		if (levelChoice.equals(levels[0])) {
			new LoadLevel1Action().execute(control);
		} else if (levelChoice.equals(levels[1])) {
			new LoadLevel2Action().execute(control);
		} else if (levelChoice.equals(levels[2])) {
			new LoadTestLevelAction().execute(control);
		} else {
			control.warning("Level not recognised!");
		}
		
	}

	@Override
	public String actionName() {
		return "NewGameAction";
	}

}
