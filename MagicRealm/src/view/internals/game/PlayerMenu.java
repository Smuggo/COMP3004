package view.internals.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;

import model.ViewModel;

public class PlayerMenu extends JInternalFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2059466724284016945L;
	
	private ViewModel lModel;
	
	private JButton lMove;
	private JButton lHide;
	private JButton lRest;
	private JButton lSearch;
	
	public PlayerMenu(ViewModel aModel){
		lModel = aModel;
		
		int xSize = lModel.getScreenDimensions().width/2;
		int ySize = lModel.getScreenDimensions().height/2;
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		setLocation(0, ySize);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		lMove = new JButton("Move");
		c.gridx = 0;
		c.gridy = 0;
		add(lMove, c);
		
		lHide = new JButton("Hide");
		c.gridx = 1;
		c.gridy = 0;
		add(lHide, c);
		
		lRest = new JButton("Rest");
		c.gridx = 2;
		c.gridy = 0;
		add(lRest, c);
		
		lSearch = new JButton("Search");
		c.gridx = 3;
		c.gridy = 0;
		add(lSearch, c);
		
		createButtonListeners();
		setVisible(true);
	}
	
	protected void createButtonListeners(){
		
	}
}
