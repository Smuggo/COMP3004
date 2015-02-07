package game.environment.hex;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameHex {
	
	private BufferedImage lImage;
	
	public GameHex(){
		try {
		    lImage = ImageIO.read(new File("media/awfulvalley-e1.gif"));
		} catch (IOException e) {
			lImage = null;
		}
		
	}
	
	public BufferedImage getTileImage(){
		return lImage;
	}

}
