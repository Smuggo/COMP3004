package view.internals.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
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
            public void mouseClicked(MouseEvent e){}

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
		g.setColor(Color.white);
	    g.fillRect(0, 0, getWidth(), getHeight());
	    g.setColor(Color.black);
		
	    lModel.requestGrid().drawGrid(g, this.getSize(), lMouse);
	    

	    g.drawString(lMouse.x+","+lMouse.y, 200, 200);		
		

	    

	}
	

	

}
