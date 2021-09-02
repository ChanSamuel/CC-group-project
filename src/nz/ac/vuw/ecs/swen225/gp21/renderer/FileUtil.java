package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

import javax.imageio.ImageIO;

/**
 * This is a utility class used for load files, such as images and musics
 * @author mengli
 *
 */
public class FileUtil {
	public static InputStream getInputStream(String type,String path) {
		//NOTE ref: https://stackoverflow.com/questions/25635636/eclipse-exported-runnable-jar-not-showing-images
		//URLClassLoader.getSystemClassLoader() similar to getClass().getClassLoader()
		//go to the root folder of current class, and get the url of given type and path, 
		//doesn't matter using absolute url or relative url here because this is the root folder
		//here use the relative url.
		String filepath = "/"+type+"/"+path;
		//NOTE couldn't get systemClassLoader working in this case
//		return URLClassLoader.getSystemClassLoader().getResourceAsStream(s);
		return FileUtil.class.getResourceAsStream(filepath);
	}
	public static BufferedImage getBufferedImage(String path) throws IOException {
		//get the inputStream of this image
		InputStream inputStream = getInputStream("images",path);
		//if inputStream is null, throw exception.
		if(inputStream==null) throw new RuntimeException("couldn't find image inputSteam");
		//otherwise use Toolkit return the image.
		return ImageIO.read(inputStream);
	}
}
