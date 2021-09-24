package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;

/**
 * This is the music class responsible for the music play and pause in game.
 * can load wav format music, mp3 not supported.
 * @author mengli 300525081
 *
 */
class Music {
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
	Music(AudioInputStream ais) {
		this.ais = ais;
		init();
	}
	/**
	 * initialize the music
	 */
	private void init() {
		try {
			clip = AudioSystem.getClip();
			clip.addLineListener(new LineListener() {
				@Override
			    public void update(LineEvent myLineEvent) {
			      if (myLineEvent.getType() == LineEvent.Type.STOP)
			        clip.close();
			    }
			  });
		} catch (LineUnavailableException e) {
			System.out.println("failed to get clip");
			e.printStackTrace();
		}
		try {
			if(ais!=null)
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
		if(clip!=null)
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	/**
	 * Start the music
	 */
	void start() {
		if(clip!=null)
        clip.start();
	}

	/**
	 * Stop the music
	 */
	void stop() {
		if(clip!=null)
        clip.stop();
	}
	/**
	 * Modify the volumn
	 */
	void modifyVolumn(int value) {
		//make sure value within range
		if(value>6) value = 6;
		FloatControl gainControl = 
			    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(value); 
	}
	
}

