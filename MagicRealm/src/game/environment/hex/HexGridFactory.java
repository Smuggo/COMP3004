package game.environment.hex;

import java.awt.Dimension;
import java.util.ArrayList;

public class HexGridFactory {
	
	public static HexGrid newHexGrid(){
		
		Dimension lCanvasSize = new Dimension(3000,3000);
		
		// Create hextiles logic
		ArrayList<Hextile> hextiles = XMLParser.newGameHexs("HexTiles.xml");
		
		// Create hexGrid and send hextile logic
		HexGrid lHexGrid = new HexGrid(3, lCanvasSize, hextiles);
		lHexGrid.setHexClearings();
		
		return lHexGrid;
	}
	
}
