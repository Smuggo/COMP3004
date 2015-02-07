package game.environment.hex;

import java.awt.Dimension;

public class HexGridFactory {
	
	public static HexGrid newHexGrid(){
		
		Dimension lCanvasSize = new Dimension(3000,3000);
		
		HexGrid lHexGrid = new HexGrid(3, lCanvasSize);
		
		return lHexGrid;
	}

}
