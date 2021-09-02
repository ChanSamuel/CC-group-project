package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

/**
 * This is the music class responsible for the music play and pause in game.
 * can load wav format music, mp3 not supported.
 * @author mengli
 *
 */
public class Music {
	/**
	 * The audio input stream
	 */
	private AudioInputStream ais;
	/**
	 * The audio clip
	 */
	private Clip clip;
	/**
	 * Constructor
	 * @param ais the audio input stream.
	 */
	public Music(AudioInputStream ais) {
		this.ais = ais;
		init();
	}
	/**
	 * initialize the music
	 */
	private void init() {
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			System.out.println("failed to get clip");
			e.printStackTrace();
		}
		try {
			clip.open(ais);
		} catch (LineUnavailableException | IOException e) {
			System.out.println("open audio file stream failed");
			e.printStackTrace();
		}
        clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	/**
	 * Start the music
	 */
	public void start() {
        clip.start();
	}
	
}

