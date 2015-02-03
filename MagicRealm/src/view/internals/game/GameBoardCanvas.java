package view.internals.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JPanel;

public class GameBoardCanvas extends Canvas{

	private static final long serialVersionUID = -4828905301301544492L;
	
	
	Random r;
	
	public GameBoardCanvas(){
		super();
		r = new Random();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		
		Graphics2D g2D=(Graphics2D) g; // cast to 2D
	    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                         RenderingHints.VALUE_ANTIALIAS_ON);
	    
	    
	    g2D.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
		g2D.drawOval(500, 500, 700, 700);
	}
	

	

}
