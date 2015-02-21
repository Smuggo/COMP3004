package game.environment.hex;
import java.awt.Graphics;
import java.io.Serializable;

import config.Config.RoadwayType;
import config.Config.IncompleteRoadwayDirection;

public class Roadway implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3934951441765390826L;
	private String lName;
	private Clearing headClearing;
	private Clearing tailClearing;
	private RoadwayType roadwayType;
	private IncompleteRoadwayDirection incompleteRoadwayDirection;
	private boolean interconnected;
	private boolean lDiscovered;
	
	public Roadway() {

	}
	
	
	public void draw(Graphics g){
		if(headClearing != null && tailClearing != null){
			int x1 = (int)(headClearing.getRotPosition().getX());
			int y1 = (int)(headClearing.getRotPosition().getY());
			int x2 = (int)(tailClearing.getRotPosition().getX());
			int y2 = (int)(tailClearing.getRotPosition().getY());
			g.drawLine(x1,y1,x2,y2);
		}
	}
	
	public boolean has(Clearing aClearing){
		if(headClearing.equals(aClearing) || tailClearing.equals(aClearing)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public Clearing getOtherClearing(Clearing aClearing){
		if(headClearing.equals(aClearing)){
			return tailClearing;
		}
		if(tailClearing.equals(aClearing)){
			return headClearing;
		}
		return null;
	}
	
	
	// Has head and tail therefore no roadway Exit
	public void initialize(Clearing head, Clearing tail, RoadwayType rT, IncompleteRoadwayDirection iRD) {
		headClearing = head;
		tailClearing = tail;
		roadwayType = rT;
		incompleteRoadwayDirection = iRD;
		lName = "";
		if(tailClearing != null && headClearing != null){
			lName = headClearing.getOwnedHextile().getName() + " " +  
					Integer.toString(headClearing.getNumber()) + "-" +
					Integer.toString(getOtherClearing(headClearing).getNumber());
		}
		
		if(roadwayType.equals(RoadwayType.HIDDEN_PATH) || roadwayType.equals(RoadwayType.SECRET_PASSAGE))
			lDiscovered = false;
		else
			lDiscovered = true;
		
		// If the roadway is complete, it must be connected within the hextile, at initalization.
		if (incompleteRoadwayDirection == null) {
			interconnected = true;
		}
		else {
			interconnected = false;
		}
	}
	
	public void print() {
		/*if (headClearing != null) {
			System.out.println("My headClearing is: " + headClearing.getIdentifier());
		} else {
			System.out.println("My headClearing is null");
		}
		if (tailClearing != null) {
		System.out.println("My tailClearing is: " + tailClearing.getIdentifier());
		} else {
			System.out.println("My tailClearing is null");
		}*/
	}
	
	public void setTailClearing(Clearing clearing) {
		tailClearing = clearing;
	}

	public Clearing getHeadClearing() {
		return headClearing;
	}
	
	public Clearing getTailClearing() {
		return tailClearing;
	}
	
	public IncompleteRoadwayDirection getIncompleteRoadwayDirection() {
		return incompleteRoadwayDirection;
	}
	
	public void setIncompleteRoadwayDirection(IncompleteRoadwayDirection i) {
		incompleteRoadwayDirection = i;
	}
	
	public void printNonInterconnected() {
		if (!interconnected) {
			print();
		}
	}
	
	public boolean getInterconnected() {
		return interconnected;
	}
	
	public RoadwayType getRoadwayType(){
		return roadwayType;
	}
	
	public boolean getDiscovered(){
		return lDiscovered;
	}
	
	public String getName(){
		return lName;
	}
}