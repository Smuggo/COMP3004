package game.environment.hex;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import config.Config;
 
public class XMLParser {
	
	public static ArrayList<Hextile> newGameHexs(String fileName){
		
    	ArrayList<Hextile> hextiles = new ArrayList<Hextile>();
    	
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File(fileName);
            
            if (file.exists()) {
                Document doc = db.parse(file);
                Element docEle = doc.getDocumentElement();
 
                NodeList hextileList = docEle.getElementsByTagName("hextile");
 
                // Hextiles
                if (hextileList != null && hextileList.getLength() > 0) {
                    for (int i = 0; i < hextileList.getLength(); i++) {
                    	
                    	// Create Hextile Node and Hextile Object
                        Node hextileNode = hextileList.item(i);
                    	Hextile hextile = new Hextile();
                        
                        if (hextileNode.getNodeType() == Node.ELEMENT_NODE) {
 
                            Element h = (Element) hextileNode;
                            
                            // Hextile Name
                            NodeList nodeList = h.getElementsByTagName("name");
                            String name = nodeList.item(0).getChildNodes().item(0).getNodeValue();

                            // Hextile Abbreviation
                            nodeList = h.getElementsByTagName("abbreviation");
                            String abbreviation = nodeList.item(0).getChildNodes().item(0).getNodeValue();
                            	
                            // Hextile Image File
                            nodeList = h.getElementsByTagName("image");
                            BufferedImage imageFile = null;
                            if (isFile("media/images/tiles/" + nodeList.item(0).getChildNodes().item(0).getNodeValue() + ".gif")) {
                            	imageFile = ImageIO.read(new File("media/images/tiles/" + nodeList.item(0).getChildNodes().item(0).getNodeValue() + ".gif"));
                    		}
                            
                            // Hextile Type
                            nodeList = h.getElementsByTagName("hextiletype");
                            Config.HextileType hextileType = Config.HextileType.valueOf(nodeList.item(0).getChildNodes().item(0).getNodeValue());
                           
                            
                            // Hextile xLocation
                            nodeList = h.getElementsByTagName("xlocation");
                            int xLocation = Integer.parseInt(nodeList.item(0).getChildNodes().item(0).getNodeValue());

                            // Hextile yLocation
                            nodeList = h.getElementsByTagName("ylocation");
                            int yLocation = Integer.parseInt(nodeList.item(0).getChildNodes().item(0).getNodeValue());

                            // Hextile rotation
                            nodeList = h.getElementsByTagName("rotation");
                            int rotation = Integer.parseInt(nodeList.item(0).getChildNodes().item(0).getNodeValue());

                            // Hextile Enchanted State
                            nodeList = h.getElementsByTagName("enchanted");
                            boolean enchanted = Boolean.parseBoolean(nodeList.item(0).getChildNodes().item(0).getNodeValue());
                            
                            // Fill Hextile Object
                            hextile.initialize(name, abbreviation, imageFile, hextileType, xLocation, yLocation, rotation, enchanted);
                            
                            // Create NodeLists for Hextile Components
                            NodeList clearingList = h.getElementsByTagName("clearing");
                            NodeList roadwayList = h.getElementsByTagName("roadway");
                            
                            // Clearings
                            if (clearingList != null && clearingList.getLength() > 0) {
                                for (int j = 0; j < clearingList.getLength(); j++) {
                                	
                                	// Create Clearing Node and Clearing Object
                                    Node clearingNode = clearingList.item(j);
                                    Clearing clearing = new Clearing();
             
                                    if (clearingNode.getNodeType() == Node.ELEMENT_NODE) {
             
                                        Element c = (Element) clearingNode;
                                        
                                        // Clearing Number
                                        nodeList = c.getElementsByTagName("number");
                                        int number = Integer.parseInt(nodeList.item(0).getChildNodes().item(0).getNodeValue());
                                        
                                        // Clearing Type
                                        nodeList = c.getElementsByTagName("clearingtype");
                                        Config.ClearingType clearingType = Config.ClearingType.valueOf(nodeList.item(0).getChildNodes().item(0).getNodeValue());
                                        
                                        nodeList = c.getElementsByTagName("positionx");
                                        int posx = Integer.parseInt(nodeList.item(0).getChildNodes().item(0).getNodeValue());
                                        
                                        nodeList = c.getElementsByTagName("positiony");
                                        int posy = Integer.parseInt(nodeList.item(0).getChildNodes().item(0).getNodeValue());
                                        
                                        nodeList = c.getElementsByTagName("dwellingType");
                                        Config.DwellingType dwellingType = null;
                                        if (!nodeList.item(0).getChildNodes().item(0).getNodeValue().equals("null"))
                                        	dwellingType = Config.DwellingType.valueOf(nodeList.item(0).getChildNodes().item(0).getNodeValue());
                                        
                                        nodeList = c.getElementsByTagName("dwellingImage");
                                        BufferedImage dwellingImage = null;
                                        if (dwellingType != null)
                                        	dwellingImage = ImageIO.read(new File("media/images/dwellings/" + nodeList.item(0).getChildNodes().item(0).getNodeValue() + ".gif"));
                                        
                                        clearing.setPosition(new Point(posx,posy));
                                        
                                        // Initialize Clearing Object
                                        clearing.initialize(abbreviation, number, clearingType, dwellingType, dwellingImage);
                                        
                                        // Add Clearing Object to Hextile
                                        hextile.addClearing(clearing);
                                    }
                                    
                                }
                            } else {
                            	System.out.println("No Clearings Found");
                                System.exit(1);
                            }
                            
                            // Roadways
                            if (roadwayList != null && roadwayList.getLength() > 0) {
                                for (int j = 0; j < roadwayList.getLength(); j++) {
                                	
                                	// Create Roadway Node and Roadway Object
                                    Node RoadwayNode = roadwayList.item(j);
                                    Roadway roadway = new Roadway();
             
                                    if (RoadwayNode.getNodeType() == Node.ELEMENT_NODE) {
             
                                        Element r = (Element) RoadwayNode;
                                        
                                        // Roadway Head Clearing
                                        nodeList = r.getElementsByTagName("headclearing");
                                        Clearing headClearing;
                                        if (isInteger(nodeList.item(0).getChildNodes().item(0).getNodeValue())) {
                                        	headClearing = hextile.getClearing(Integer.parseInt(nodeList.item(0).getChildNodes().item(0).getNodeValue()));
                                        } else {
                                        	headClearing = null;
                                        }
                                        
                                        // Roadway Tail Clearing (not required)
                                        Clearing tailClearing = null;
                                        try {
                                        	nodeList = r.getElementsByTagName("tailclearing");
                                        	tailClearing = hextile.getClearing(Integer.parseInt(nodeList.item(0).getChildNodes().item(0).getNodeValue()));
                                    	} catch(NullPointerException e) {
                                    	
                                    	}
                                        
                                        // Roadway Type
                                        nodeList = r.getElementsByTagName("roadwaytype");
                                        Config.RoadwayType roadwayType = Config.RoadwayType.valueOf(nodeList.item(0).getChildNodes().item(0).getNodeValue());
                                        
                                       
                                        // Incomplete Roadway Direction (not required)
                                        Config.IncompleteRoadwayDirection incompleteRoadwayDirection = null;
                                        try {
	                                        nodeList = r.getElementsByTagName("incompleteroadwaydirection");
	                                        incompleteRoadwayDirection = Config.IncompleteRoadwayDirection.valueOf(nodeList.item(0).getChildNodes().item(0).getNodeValue());
                                        } catch(NullPointerException e) {
                                        	
                                        }
                                        
                                        // Initialize Roadway Object
                                        roadway.initialize(headClearing, tailClearing, roadwayType, incompleteRoadwayDirection);
                                        
                                        // Add Roadway Object to Hextile & Clearing(s)
                                        hextile.addRoadway(roadway);
                                        
                                        // Must have a headClearing, tailClearing optional
                                        headClearing.addRoadway(roadway);
                                        
                                        if (tailClearing != null) {
                                        	tailClearing.addRoadway(roadway);
                                        }
                                    }
                                }
                            } else {
                            	System.out.println("No Pathways Found");
                                System.exit(1);
                            }
                      
                            // Add Hextile Object to Hextile ArrayList
                            hextiles.add(hextile);
                        }
                       
                    }
                } else {
                	System.out.println("No Hextiles Found");
                    System.exit(1);
                }
            }
            else {
            	System.out.println("File does not exist");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return hextiles;
    }
    
    private static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        }
        // only got here if we didn't return false
        return true;
    }
    
    private static boolean isFile(String s) {
    	try {
        	ImageIO.read(new File(s));
		} catch (IOException e) {
			System.out.print(e);
			return false;
		}
        // We good
        return true;
    }
}