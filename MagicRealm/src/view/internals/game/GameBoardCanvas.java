package view.internals.game;

import game.GameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JPanel;

import model.ViewModel;

public class GameBoardCanvas extends JPanel{

	private static final long serialVersionUID = -4828905301301544492L;
	
	ViewModel lModel;
	Random r;
	
	Point lMouse;
	
	
	public GameBoardCanvas(ViewModel aModel){
		super();
		lModel = aModel;
		
		lMouse = new Point();
		setDoubleBuffered(true);

		r = new Random();
		
		addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e){
            	lModel.updatePlayerClicked(e.getPoint());
            }

            public void mouseEntered(MouseEvent arg0) {}
            public void mouseExited(MouseEvent arg0) {}
            public void mousePressed(MouseEvent arg0) {}
            public void mouseReleased(MouseEvent arg0) {}
        });
		
		this.addMouseMotionListener(new MouseMotionListener(){

			public void mouseDragged(MouseEvent arg0) {
				lMouse.setLocation(arg0.getPoint());
				repaint();
			}

			public void mouseMoved(MouseEvent arg0) {
				lMouse.setLocation(arg0.getPoint());
				repaint();
			}
			
		});
	}
	
	public void paint(Graphics g){
		
		GameState lGameState = lModel.getGameState();
		
		g.setColor(Color.white);
	    g.fillRect(0, 0, getWidth(), getHeight());
	    g.setColor(Color.black);
		
	    if(lModel.requestGrid() != null){
	    	lModel.requestGrid().drawGrid(g, this.getSize(), lMouse, lModel.getImageMap());
	    }
	    
	    for(int i = 1; i < lGameState.getPlayers().size()+1; i++){
	    	if(lGameState.getPlayer(i)!= null && lGameState.getPlayer(i).getChosenHero() != null){
	    		lGameState.getPlayer(i).getChosenHero().draw(g);
	    	}
	    }

	    g.drawString(lMouse.x+","+lMouse.y, 200, 200);		
		
	    for(int i = 0; i < lGameState.getPlayers().size(); i++){
	    	g.drawString("Player "+(i+1), lGameState.getPlayers().get(i).lastClick.x, lGameState.getPlayers().get(i).lastClick.y);
	    }
	    

	    

	}
	

	

}
