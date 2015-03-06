package view.internals.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JInternalFrame;

import model.ViewModel;

public class HeroActionChits extends JInternalFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -797938516902370365L;

	public HeroActionChits(ViewModel aModel){
		super("Combat View",true,false,false,true);
		
		lModel = aModel;

		int xSize = 400;
		int ySize = 400;
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		setLocation(0, 0);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
	}
}
