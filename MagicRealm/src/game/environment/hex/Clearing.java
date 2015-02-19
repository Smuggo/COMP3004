package game.environment.hex;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

import config.Config;
import config.Config.DwellingType;
import config.ImageMap;

public class Clearing implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -403029986388081133L;
	ArrayList<Roadway> roadways;
	String identifier; //Abbreviation of Hex Tile Name + Number
	int number;
	Config.ClearingType clearingType;
	int clearingDiameter;
	Point position; //Actual position from center
	Hex ownedHex;
	DwellingType lDwellingType;

	
	public Clearing() {
		roadways = new ArrayList<Roadway>();
		clearingDiameter = 80;
		position = new Point(0,0);
	}

	public Clearing(String str, Config.ClearingType cT, Config.DwellingType dT) {
		identifier = str;
		clearingType = cT;
		position = new Point(0,0);
		lDwellingType = dT;
	}

	public Hex getOwnedHex(){
		return ownedHex;
	}

	public void setOwnedHex(Hex aHex){
		ownedHex = aHex;
	}

	public void setPosition(Point aPoint){
		position = aPoint;
	}

	public Point getPosition(){
		return position;
	}

	public Point getRotPosition(){
		int degrees = ownedHex.getHextile().getAngle();
		double angle = Math.toRadians(degrees);
		int centerX = (int)(ownedHex.getCenter().getX());
		int centerY = (int)(ownedHex.getCenter().getY());
		Point p = new Point((int)(centerX+(position.getX())), (int)(centerY+(position.getY())));

		double x = Math.cos(angle) * (p.x-centerX) - Math.sin(angle) * (p.y-centerY) + centerX;
		double y = Math.sin(angle) * (p.x-centerX) + Math.cos(angle) * (p.y-centerY) + centerY;

		Point newP = new Point((int)x, (int)y);
		return newP;
	}

	public int getClearingDiameter(){
		return clearingDiameter;
	}

	public int getNumber() {
		return number;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void initialize(String abbreviation, int n, Config.ClearingType cT, DwellingType dwellingType) {
		number = n;
		identifier = abbreviation + n;
		clearingType = cT;
		lDwellingType = dwellingType;
	}

	public void addRoadway(Roadway roadway) {
		roadways.add(roadway);
	}

	public boolean neighbourTo(Clearing possibleNeighbour) {
		return true;
	}

	public void draw(Graphics g, int centerX, int centerY, int degrees, ImageMap aImageMap){
		double angle = Math.toRadians(degrees);
		Point p = new Point((int)(centerX+(position.getX())), (int)(centerY+(position.getY())));

		double x = Math.cos(angle) * (p.x-centerX) - Math.sin(angle) * (p.y-centerY) + centerX;
		double y = Math.sin(angle) * (p.x-centerX) + Math.cos(angle) * (p.y-centerY) + centerY;

		Point newP = new Point((int)x-(clearingDiameter/2), (int)y-(clearingDiameter/2));


		g.drawOval(newP.x,newP.y, clearingDiameter, clearingDiameter);

		if(lDwellingType != null){
			g.drawImage(aImageMap.getDwellingImage(lDwellingType), newP.x, newP.y, Color.ORANGE, null);
		}
	}

	public void drawSelected(Graphics g, int centerX, int centerY, int degrees, Point aMouse){
		double angle = Math.toRadians(degrees);
		Point p = new Point((int)(centerX+(position.getX())), (int)(centerY+(position.getY())));

		double x = Math.cos(angle) * (p.x-centerX) - Math.sin(angle) * (p.y-centerY) + centerX;
		double y = Math.sin(angle) * (p.x-centerX) + Math.cos(angle) * (p.y-centerY) + centerY;

		if(aMouse.distance(x, y) <= clearingDiameter/2){

			Point newP = new Point((int)x-(clearingDiameter/2), (int)y-(clearingDiameter/2));

			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.RED);
			g2.drawOval(newP.x,newP.y, clearingDiameter, clearingDiameter);
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(1));
		}
	}




	public void print() {
		System.out.println("My identifier is: " + identifier);
		System.out.println("My clearing type is: " + clearingType);
	}
}
