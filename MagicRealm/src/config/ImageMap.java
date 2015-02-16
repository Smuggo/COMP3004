package config;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import config.Config.ImageType;

public class ImageMap {

	private Map<Config.ImageType, BufferedImage> imageMap = new HashMap<Config.ImageType, BufferedImage>();

	public ImageMap(){
		try {
			imageMap.put(ImageType.captainPage, ImageIO.read(new File ("media/images/characterdetail/captain.jpg")));
			imageMap.put(ImageType.captainChit, ImageIO.read(new File ("media/images/characters/captain.png")));
			imageMap.put(ImageType.swordsmanPage, ImageIO.read(new File("media/images/characterdetail/swordsman.jpg")));
			imageMap.put(ImageType.swordsmanChit, ImageIO.read(new File("media/images/characters/swordsman.png")));
			imageMap.put(ImageType.amazonPage, ImageIO.read(new File ("media/images/characterdetail/amazon.jpg")));
			imageMap.put(ImageType.amazonChit, ImageIO.read(new File ("media/images/characters/amazon.png")));
			imageMap.put(ImageType.dwarfPage, ImageIO.read(new File ("media/images/characterdetail/dwarf.jpg")));
			imageMap.put(ImageType.dwarfChit, ImageIO.read(new File ("media/images/characters/dwarf.png")));
			imageMap.put(ImageType.elfPage, ImageIO.read(new File ("media/images/characterdetail/elf.jpg")));
			imageMap.put(ImageType.elfChit, ImageIO.read(new File ("media/images/characters/elf.png")));
			imageMap.put(ImageType.bKnightPage, ImageIO.read(new File ("media/images/characterdetail/black_knight.jpg")));
			imageMap.put(ImageType.bKnightChit, ImageIO.read(new File ("media/images/characters/black_knight.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImage(ImageType aImageType){
		return imageMap.get(aImageType);
	}
}
