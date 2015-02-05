package game.environment.hex;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import config.Config;

public class Hex{
	private boolean active;
	private int radius = 248;
	private int width = radius*2;
	private int innerWidth = (int) (width * 3/4);
	private int height = (int)(Math.sqrt(3)/2 * (radius*2)); //Also the vertical distance
	
	GameHex lGameHex;
	
	//For coordinates we'll be using an axial coordinate system.
	 
	private int x; //x position on the grid
	private int y; //y position on the grid
	
	private Point centre = new Point();

	public Hex(int aX, int aY){
		x = aX;
		y = aY;
		active = true;
		if((x < 0 && y < 0 && x + y < -3) || (x > 0 && y > 0 && x + y > 3)){
			active = false;
		}
		lGameHex = new GameHex();
	}
	
	//Returns the corner starting at the rightmost corner
	public Point getCorner(Point hexCentre, int size, int cornerNum){
		double angle = (2 * Math.PI) / (6 * cornerNum);
		
		return new Point((int) (hexCentre.x + size * Math.cos(angle)),
				 		 (int)(hexCentre.y + size * Math.sin(angle)));
	}
	
	public int getRaddius() { return radius; }
	public int getVert()    { return height; }
	public Point getCentre(){ return centre; }
	
	
	public void drawHex (int aX, int aY, Graphics g, Dimension aCanvasSize){
		if(active){
			int lCenterX = ((int) ((aCanvasSize.getWidth()/2)+(innerWidth*aX)));
			int lCenterY = ((int) (aCanvasSize.getHeight()/2)+((height*aY))+(height*aX/2));
	
			
			g.drawImage(lGameHex.getTileImage(), lCenterX-(width/2), lCenterY-(height/2)-1, null);
			
			int x[] = getHexXCoords(lCenterX);
			int y[] = getHexYCoords(lCenterY);
			g.drawPolygon(x,y,6);
			
			
			if(Config.drawingHexCoords){
				g.drawString(aX+","+aY,lCenterX, lCenterY);
			}
		}
	}
	
	private double getAngle(double a){
		double r = ((Math.PI) / 3 * a);
		return r;
	}
	
	private int[] getHexXCoords(int center){
		int[] r = new int[6];
		r[0] = (int)(center+(radius * Math.cos(getAngle(1))));
		r[1] = (int)(center+(radius * Math.cos(getAngle(2))));
		r[2] = (int)(center+(radius * Math.cos(getAngle(3))));
		r[3] = (int)(center+(radius * Math.cos(getAngle(4))));
		r[4] = (int)(center+(radius * Math.cos(getAngle(5))));
		r[5] = (int)(center+(radius * Math.cos(getAngle(6))));
		return r;
	}
	
	private int[] getHexYCoords(int center){
		int[] r = new int[6];
		r[0] = (int)(center+(radius * Math.sin(getAngle(1))));
		r[1] = (int)(center+(radius * Math.sin(getAngle(2))));
		r[2] = (int)(center+(radius * Math.sin(getAngle(3))));
		r[3] = (int)(center+(radius * Math.sin(getAngle(4))));
		r[4] = (int)(center+(radius * Math.sin(getAngle(5))));
		r[5] = (int)(center+(radius * Math.sin(getAngle(6))));
		return r;
	}
}
