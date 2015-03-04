package view.internals.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JInternalFrame;

import model.ViewModel;

public class Combat extends JInternalFrame{
	private ViewModel lModel;
	
	public Combat(ViewModel aModel){
		super("Character List",true,false,false,true);
		
		lModel = aModel;

		int xSize = 595;
		int ySize = 748;
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		setLocation(0, 0);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
	}
}
