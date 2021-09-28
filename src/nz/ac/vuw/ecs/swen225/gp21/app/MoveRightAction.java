package nz.ac.vuw.ecs.swen225.gp21.app;

public class MoveRightAction implements Action, MovementAction {

	@Override
	public void execute(Controller control) {
		control.world.moveChipRight();
		//System.out.println("chap's location is: " + control.world.getPlayerLocation());
		return;
	}

	@Override
	public String actionName() {
		return "MoveRightAction";
	}

}
