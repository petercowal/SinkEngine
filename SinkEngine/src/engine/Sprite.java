package engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
	BufferedImage[] frames;
	public Sprite(String... fileNames) throws IOException{
		frames = new BufferedImage[fileNames.length];
		for(int i = 0; i < fileNames.length; i++){
			frames[i] = ImageIO.read(new File(fileNames[i]));
		}
		
	}
	public Sprite(String fileName, int numFrames) throws IOException{
		BufferedImage source = ImageIO.read(new File(fileName));
		frames = new BufferedImage[numFrames];
		for(int i = 0; i < frames.length; i++){
			frames[i]=new BufferedImage(source.getWidth()/numFrames, source.getHeight(),
						BufferedImage.TYPE_INT_ARGB);
			frames[i].createGraphics().drawImage(source, -frames[i].getWidth()*i, 0, null);
		}
	}
	public BufferedImage getFrame(int index){
		return frames[index % frames.length];
	}
}
