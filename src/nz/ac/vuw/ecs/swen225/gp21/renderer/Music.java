package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

/**
 * This is the music class responsible for the music play and pause in game. can
 * load wav format music, mp3 not supported.
 * it offers loop, start, stop(close the clip), pause(just pause, won't close the clip), 
 * resume and volumn changing functions.
 * @author limeng7 300525081
 *
 */
class Music {
	private AudioInputStream ais;
	private Clip clip;
	private long clipTime;
	private MyLineListener myLineListener;

	/**
	 * Constructor
	 * 
	 * @param ais the audio input stream.
	 */
	Music(AudioInputStream ais) {
		this.ais = ais;
		init();
	}

	/**
	 * initialize the music, add linelistener to the clip, 
	 * when stop occurs, it will check if it's pause or not,
	 * if it's not pause(means it's stop), then it will close the clip , otherwise do nothing.
	 */
	private void init() {
		try {
			clip = AudioSystem.getClip();
			myLineListener = new MyLineListener(clip);
			clip.addLineListener(myLineListener);
		} catch (LineUnavailableException e) {
			System.out.println("failed to get clip");
			e.printStackTrace();
		}
		try {
			if (ais != null)
				clip.open(ais);
		} catch (LineUnavailableException | IOException e) {
			System.out.println("open audio file stream failed");
			e.printStackTrace();
		}
	}

	/**
	 * Loop the music
	 */
	void loop() {
		if (clip != null)
			clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	/**
	 * Start the music
	 */
	void start() {
		if (clip != null)
			clip.start();
	}

	/**
	 * Stop the music
	 */
	void stop() {
		if (clip != null)
			clip.stop();
	}

	/**
	 * Pause the music
	 */
	void pause() {
		if (clip != null) {
			clipTime = clip.getMicrosecondPosition();
			myLineListener.setPause(true);
			clip.stop();
		}
	}

	/**
	 * Resume the music
	 */
	void resume() {
		if (clip != null) {
			myLineListener.setPause(false);
			clip.setMicrosecondPosition(clipTime);
			clip.start();
		}
	}


	/**
	 * Modify the volumn
	 * 
	 * @param value positive number means up the volumn, negative means down the
	 *              volumn
	 */
	void modifyVolumn(int value) {
		// make sure value within range
		if (value > 6)
			value = 6;
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(value);
	}

}
