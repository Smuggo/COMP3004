package view.internals.game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import model.ViewModel;

public class GameBoardView extends JInternalFrame{

	private static final long serialVersionUID = -4478656155793467601L;

	ViewModel lModel;
	
	ScrollPane lScrollPane;
	
	GameBoardCanvas lCanvas;
	
	JButton lTestButton;
	
	public GameBoardView(ViewModel aModel, Dimension aCanvasSize){
		super("Game Board",false,false,false,true);
		
		lModel = aModel;
		
		Dimension lScreenSize = lModel.getScreenDimensions();
		
		int xScreen = (int)lScreenSize.getWidth();
		int yScreen = (int)lScreenSize.getHeight();
		
		int xSize = (xScreen/2)-10;
		int ySize = yScreen-40;
		
		
		setPreferredSize(new Dimension(xSize,ySize));
		setSize(xSize,ySize);
		
		setLocation((xScreen/2), 0);
		
		
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		lScrollPane = new ScrollPane();
		lScrollPane.getVAdjustable().setUnitIncrement(16);
		
		fillScrollPane(aCanvasSize);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0,0,0,0);
		add(lScrollPane, c);
		
		lTestButton = new JButton("Test");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0,0,0,0);
		add(lTestButton, c);
		
		
		createButtonListeners();
	}
	
	
	
	protected void createButtonListeners(){
		
		
		lTestButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				lCanvas.repaint();
			}
		});
		
		
	}
	
	
	public void fillScrollPane(Dimension aCanvasSize){
		lCanvas = new GameBoardCanvas(lModel);
		lCanvas.setPreferredSize(aCanvasSize);
		lCanvas.setSize(aCanvasSize);
		lScrollPane.add(lCanvas);
	}
	
}
