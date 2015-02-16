package game.environment.hex;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import config.Config;

public class Clearing {
	ArrayList<Roadway> roadways;
	String identifier; //Abbreviation of Hex Tile Name + Number
	int number;
	Config.ClearingType clearingType;
	int clearingDiameter;
	Point position;
	
	public Clearing() {
		roadways = new ArrayList<Roadway>();
		clearingDiameter = 80;
		position = new Point(0,0);
	}
	
	public Clearing(String str, Config.ClearingType cT) {
		identifier = str;
		clearingType = cT;
		position = new Point(0,0);
	}
	
	public void setPosition(Point aPoint){
		position = aPoint;
	}
	
	public Point getPosition(){
		return position;
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

	public void initialize(String abbreviation, int n, Config.ClearingType cT) {
		number = n;
		identifier = abbreviation + n;
		clearingType = cT;
	}
	
	public void addRoadway(Roadway roadway) {
		roadways.add(roadway);
	}
	
	public boolean neighbourTo(Clearing possibleNeighbour) {
		return true;
	}
	
	public void draw(Graphics g, int centerX, int centerY, int degrees){
		double angle = Math.toRadians(degrees);
		Point p = new Point((int)(centerX+(position.getX())), (int)(centerY+(position.getY())));
		
		double x = Math.cos(angle) * (p.x-centerX) - Math.sin(angle) * (p.y-centerY) + centerX;
		double y = Math.sin(angle) * (p.x-centerX) + Math.cos(angle) * (p.y-centerY) + centerY;
		
		Point newP = new Point((int)x-(clearingDiameter/2), (int)y-(clearingDiameter/2));
		
		
		g.drawOval(newP.x,newP.y, clearingDiameter, clearingDiameter);


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
