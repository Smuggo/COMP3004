package game.environment.hex;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import config.Config.ActionState;
import config.ImageMap;

public class HexGrid implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -848201332949488463L;
	private Hex[][] lGrid; //DO NOT ACCESS GRID VIA ARRAY, USE GET METHOD
	private Hex lCenterHex;
	private int lRadius;
	private Dimension lCanvasSize;
	
	public HexGrid(int aRadius, Dimension aCanvasSize, ArrayList<Hextile> hextiles){
		lRadius = aRadius;
		
		int lGridSize = (2*aRadius)+1;
		lGrid = new Hex[lGridSize][lGridSize];
		
		for(int y = 0; y < lGridSize; y++){
			for(int x = 0; x < lGridSize; x++){
				lGrid[x][y] = null;
				for(int i = 0; i < hextiles.size(); i++){
					// Find the hextile logic and create a hex
					if (hextiles.get(i).getXLocation() == x-lRadius && hextiles.get(i).getYLocation() == y-lRadius) {
						lGrid[x][y] = new Hex(x-lRadius,y-lRadius, aCanvasSize, hextiles.get(i));
						hextiles.get(i).checkForAdjacentHextiles(x-lRadius, y-lRadius, aRadius, this);
					}
					
					// Set Center Hex
					if(x == (aRadius) && y == (aRadius)){
						lCenterHex = lGrid[x][y];
					}
				}
			}
		}
		lCanvasSize = aCanvasSize;
	}
	
	public void setHexClearings(){
		int lGridSize = (2*lRadius)+1;
		for(int y = 0; y < lGridSize; y++){
			for(int x = 0; x < lGridSize; x++){
				if(lGrid[x][y] != null && lGrid[x][y].isActive()){
					lGrid[x][y].setHexClearings();
				}
			}
		}
	}
	
	//NOTE: Converts x-y array to a fictional grid with negative x and y coords
	public Hex getHex(int aX, int aY){
		return lGrid[aX+(lRadius)][aY+(lRadius)];
	}
	
	
	public void drawGrid(Graphics g, Dimension aCanvasSize, Point aMouse, ImageMap aImageMap){
		for(int y = -lRadius; y <= lRadius; y++ ){
			for(int x = -lRadius; x <= lRadius; x++){
				if (getHex(x,y) != null) {
					getHex(x,y).drawHex(x,y,g, aCanvasSize, aMouse, aImageMap);
				}
			}
		}
		
		
		//DrawSelected
		for(int y = -lRadius; y <= lRadius; y++ ){
			for(int x = -lRadius; x <= lRadius; x++){
				if (getHex(x,y) != null) {
					getHex(x,y).drawSelectedHex(x,y,g, aCanvasSize, aMouse);
				}
			}
		}
		
		
	}
	
	public void drawAdjacentClearings(Graphics g, Clearing aClearing, Point aMouse){
		if(aClearing != null){
			for(int i = 0; i < aClearing.getRoadways().size(); i++){
				Roadway lRoadway = aClearing.getRoadways().get(i);
				Clearing otherClearing = lRoadway.getOtherClearing(aClearing);
				if(otherClearing != null){
					otherClearing.drawAdjacent(g, aMouse);
				}
			}
		}
	}
	
	public Clearing getAdjacentClearingByMouse(Clearing aClearing, Point aMouse){
		Clearing lClearing = null;
		for(int y = -lRadius; y <= lRadius; y++ ){
			for(int x = -lRadius; x <= lRadius; x++){
				if (getHex(x,y) != null) {
					lClearing = getHex(x,y).getHextile().getClearingByMouse(aMouse);
					if(lClearing != null && lClearing.neighbourTo(aClearing)){
						return lClearing;
					}
				}
			}
		}
		return lClearing;
	}

	public Dimension getCanvasSize() {
		return lCanvasSize;
	}
	
	public Clearing getClearingByDwelling(String aDwelling){
		Clearing lClearing = null;
		for(int y = -lRadius; y <= lRadius; y++ ){
			for(int x = -lRadius; x <= lRadius; x++){
				if (getHex(x,y) != null && getHex(x,y).getHextile() != null) {
					lClearing = getHex(x,y).getHextile().getClearingByDwelling(aDwelling);
					if(lClearing != null){
						return lClearing;
					}
				}
			}
		}
		return null;
	}
}
