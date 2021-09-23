package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This is a utility class used for load files, such as images and musics
 * @author mengli 300525081
 *
 */
public class FileUtil {
	/**
	 * A method for returning an InputStream from a filepath
	 * @param type which type of file, image or sound
	 * @param path the filepath of this file
	 * @return the inputstream
	 */
	static InputStream getInputStream(String type,String path) {
		//NOTE ref: https://stackoverflow.com/questions/25635636/eclipse-exported-runnable-jar-not-showing-images
		//URLClassLoader.getSystemClassLoader() similar to getClass().getClassLoader()
		//go to the root folder of current class, and get the url of given type and path, 
		//doesn't matter using absolute url or relative url here because this is the root folder
		//here use the relative url.
		String filepath = "/"+type+"/"+path;
		System.out.println(filepath);
		//NOTE couldn't get systemClassLoader working in this case
//		return URLClassLoader.getSystemClassLoader().getResourceAsStream(s);
		return FileUtil.class.getResourceAsStream(filepath);
	}
	/**
	 * A method for returning an url from a filepath
	 * @param type is it an image or music
	 * @param path the filepath
	 * @return an URL
	 * 
	 */
	static URL getURL(String type,String path) {
		String filepath = "/"+type+"/"+path;
		return FileUtil.class.getResource(filepath);
	}
	
	/**
	 * A method for returning a buffered image.
	 * @param path the image file path
	 * @return the buffered image
	 * @throws IOException if couldn't get input stream
	 */
	static BufferedImage getBufferedImage(String path) throws IOException {
		//get the inputStream of this image
		InputStream inputStream = getInputStream("images",path);
		//if inputStream is null, throw exception.
		if(inputStream==null) throw new RuntimeException("couldn't find image inputSteam");
		//otherwise use Toolkit return the image.
		return ImageIO.read(inputStream);
	}
	/**
	 * A method for returning a gif.
	 */
	static Image getGIF(String path) {
		URL url = getURL("images",path);
		Image img = Toolkit.getDefaultToolkit().createImage(url);
		return img;
	}
	/**
	 * A method for returning an AudioStream from given path
	 * @param path the audio file path
	 * @return the audioInputStream
	 * @throws IOException if couldn't get input stream or audio file not support
	 */
	static AudioInputStream getAudioStream(String path) throws IOException {
		//get the inputStream of this image
		InputStream inputStream = getInputStream("musics",path);
		//if inputStream is null, throw exception.
		if(inputStream==null) throw new RuntimeException("couldn't find sounds inputSteam");
		//otherwise use Toolkit return the image.
		try {
			return AudioSystem.getAudioInputStream(inputStream);
		} catch (UnsupportedAudioFileException | IOException e) {
			System.out.println("Unsupport audio file");
			e.printStackTrace();
		}
		return null;
	}
}
