package view.internals.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import model.ViewModel;

public class ClearingView extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1011994368090366319L;
	
	ViewModel lModel;
	
	JLabel lInClearing;

	public ClearingView(ViewModel aModel){
		super("Clearing View",false,false,false,true);
		
		lModel = aModel;

		int xSize = aModel.getScreenDimensions().width/2;
		int ySize = 400;
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		setLocation(0, ySize/2);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		lInClearing = new JLabel("Entities In Clearing:");
		c.gridx = 0;
		c.gridy = 0;
		add(lInClearing, c);
		
		setVisible(true);
	}
}
