package nz.ac.vuw.ecs.swen225.gp21.app;

public class MoveUpAction implements Action {

	@Override
	public void execute(Controller control) {
		control.world.moveChipUp();
		System.out.println("chap's location is: " + control.world.getPlayerLocation());
	}

	@Override
	public String actionName() {
		return "MoveUpAction";
	}

}
