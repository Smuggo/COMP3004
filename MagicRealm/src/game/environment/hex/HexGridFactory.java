package game.environment.hex;

import game.chit.ChitFactory;

import java.awt.Dimension;
import java.util.ArrayList;

public class HexGridFactory {
	
	public static HexGrid newHexGrid(ArrayList<Hextile> hextiles){
		
		Dimension lCanvasSize = new Dimension(3000,3000);

		// Create hexGrid and send hextile logic
		HexGrid lHexGrid = new HexGrid(3, lCanvasSize, hextiles);
		lHexGrid.setHexClearings();
		
		return lHexGrid;
	}
	
}
