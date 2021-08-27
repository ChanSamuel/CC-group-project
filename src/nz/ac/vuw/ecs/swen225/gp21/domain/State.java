package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * The Game world can be in one of several states, this interface defines the operations a state 
 * most perform.
 * @author Benjamin
 *
 */
public interface State {
	public void update(World w);
	
}
