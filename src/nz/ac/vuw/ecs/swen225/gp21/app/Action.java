package nz.ac.vuw.ecs.swen225.gp21.app;

import java.util.Optional;

public interface Action {
	
	public Optional<Boolean> execute(Controller control);
}
