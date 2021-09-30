package nz.ac.vuw.ecs.swen225.gp21.renderer;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
/**
 * This class is a modified LineListener, is used for close the clip when stopped.
 * @author limeng7 300525081
 *
 */
public class MyLineListener implements LineListener{
	private Clip clip;
	private Boolean pause = false;
	/**
	 * Constructor
	 * @param clip
	 */
	public MyLineListener(Clip clip) {
		this.clip = clip;
	}
	/**
	 * Set pause state
	 * @param pause
	 */
	public void setPause(Boolean pause) {
		this.pause=pause;
	}
	@Override
	public void update(LineEvent event) {
		if (event.getType() == LineEvent.Type.STOP&&!pause)
	        clip.close();
	}

}
