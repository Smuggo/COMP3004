package view.internals.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JInternalFrame;

import model.ViewModel;

public class ClearingView extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1011994368090366319L;
	
	ViewModel lModel;

	public ClearingView(ViewModel aModel){
		super("Clearing View");
		
		lModel = aModel;

		int xSize = aModel.getScreenDimensions().width/2;
		int ySize = 400;
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		setLocation(0, ySize/2);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		setVisible(true);
	}
}
