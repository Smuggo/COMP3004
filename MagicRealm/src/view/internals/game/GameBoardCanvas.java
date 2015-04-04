package view.internals.game;

import game.GameState;
import game.environment.hex.Clearing;
import game.environment.hex.HexGrid;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JPanel;

import config.Config.ActionState;
import action.ActionManager;
import model.ViewModel;

public class GameBoardCanvas extends Canvas{

	private static final long serialVersionUID = -4828905301301544492L;
	
	ViewModel lModel;
	Random r;
	BufferStrategy bs;
	GameBoardView lParentView;
	boolean ready = false;
	
	
	Point lMouse;
	
	
	public GameBoardCanvas(ViewModel aModel, GameBoardView aParentView){
		super();
		lModel = aModel;
		lParentView = aParentView;
		
		lMouse = new Point();
		

		r = new Random();
		
		addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e){
            	lModel.updatePlayerClicked(e.getPoint());
            }

            public void mouseEntered(MouseEvent arg0) {}
            public void mouseExited(MouseEvent arg0) {}
            public void mousePressed(MouseEvent arg0) {}
            public void mouseReleased(MouseEvent arg0) {
            	releaseMouse(arg0);
            }
        });
		
		this.addMouseMotionListener(new MouseMotionListener(){

			public void mouseDragged(MouseEvent arg0) {
				moveMouse(arg0);
			}

			public void mouseMoved(MouseEvent arg0) {
				moveMouse(arg0);
			}
			
		});
	}
	
	public void init(){

	}
	
	public void moveMouse(MouseEvent arg0){
		lMouse.setLocation(arg0.getPoint());
		repaint();
	}
	
	public void releaseMouse(MouseEvent arg0){
		ActionManager lActionManager = lModel.getActionManager();
		Clearing lClearing;
		if(lActionManager.getState().equals(ActionState.MOVING)){
			lClearing = lModel.getGameState().getHexGrid().getAdjacentClearingByMouse(lActionManager.getActionList().getCurrentClearing(), 
																								lMouse);
			if(lClearing != null){
				lModel.addToActionTable(lClearing.getIdentifier());
				lActionManager.getActionList().addMoveAction(lClearing);		
			}
		} else {
			lClearing = lModel.getGameState().getHexGrid().getClearingByMouse(lMouse);
			if(lClearing != null){
				lModel.addClearingChits(lClearing);
			}
		}
	}
	
	public void paint(Graphics g2){
		if(!ready){
			createBufferStrategy(2);
			bs = getBufferStrategy();
			ready = true;
		}
		if(ready){
			
			Graphics2D g = (Graphics2D) bs.getDrawGraphics();
			bs.show();
			GameState lGameState = lModel.getGameState();
			HexGrid lHexGrid = lModel.getGameState().getHexGrid();
			ActionManager lActionManager = lModel.getActionManager();
		    
		    Point lWindowPoint1 = lParentView.getScrollPane().getScrollPosition();
		    Dimension lWindowSize = lParentView.getSize();
		    
		    g.setColor(Color.black);
			
		    if(lModel.requestGrid() != null){
		    	lModel.requestGrid().drawGrid(g, this.getSize(), lMouse, lModel.getImageMap(), lWindowPoint1, lWindowSize);
		    }
		    
		    if(lActionManager != null){
		    	lActionManager.getActionList().drawMovements(g);
		    }
		    
		    if(lActionManager.getState().equals(ActionState.MOVING)){
		    	lGameState.getHexGrid().drawAdjacentClearings(g, lActionManager.getActionList().getCurrentClearing(), lMouse);
		    }
		
		    /*
		    for(int i = 0; i < lGameState.getPlayers().size(); i++){
		    	g.drawString("Player "+(i+1), lGameState.getPlayers().get(i).lastClick.x, lGameState.getPlayers().get(i).lastClick.y);
		    }*/
		    
		   for(int x = -4; x < lHexGrid.getGridRadius() + 1; x++){
		    	for(int y = -4; y < lHexGrid.getGridRadius() + 1; y++){
		    		if(lHexGrid.getHex(x, y) != null){
		    			if(lHexGrid.getHex(x, y).isActive()){
		    				if(lHexGrid.getHex(x, y).getHextile().getWarningChit() != null){
		    					lHexGrid.getHex(x, y).getHextile().getWarningChit().draw(lModel.getGameState(), g, x, y, lWindowPoint1, lWindowSize);
		    				}
		    				if(lHexGrid.getHex(x, y).getHextile().getOtherChit() != null){
		    					lHexGrid.getHex(x, y).getHextile().getOtherChit().draw(lModel.getGameState(), g, x, y, lWindowPoint1, lWindowSize);
		    				}
		    				if(lHexGrid.getHex(x, y).getHextile().getChitsInLostCityCastle() != null){
		    					for (int i = 0; i < lHexGrid.getHex(x, y).getHextile().getChitsInLostCityCastle().size(); i++) {
		    						lHexGrid.getHex(x, y).getHextile().getChitsInLostCityCastle().get(i).draw(lModel.getGameState(), g, x, y, lWindowPoint1, lWindowSize);
		    					}
		    				}
		    			}
		    		}
		    	}
		    }
		   
		    for(int i = 1; i < lGameState.getPlayers().size()+1; i++){
		    	if(lGameState.getPlayer(i)!= null && lGameState.getPlayer(i).getChosenHero() != null){
		    		lGameState.getPlayer(i).getChosenHero().draw(lModel.getGameManager(), g, lGameState.getPlayer(i));
		    	}
		    }
		    
			//bs.show();
			//g.dispose();
		}
	}
}
