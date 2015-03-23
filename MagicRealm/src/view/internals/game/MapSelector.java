package view.internals.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;

import model.ViewModel;

public class MapSelector extends JInternalFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8599167194948547205L;
	
	private ViewModel lModel;
	
	private JButton defaultMapButton = new JButton("Default Map");
	private JButton randomlyGeneratedMapButton = new JButton("Randomly Generated Map");
	
	public MapSelector(ViewModel aModel){
		super("Map Selection",false,false,false,true);
		
		lModel = aModel;
		
		// Set the size of the Chit List Window
		int xSize = 300;
		int ySize = 200;
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		// Set the location of the window to appear in the middle of the main application
		Dimension lScreenSize = lModel.getScreenDimensions();
		int xScreen = (int)lScreenSize.getWidth();
		int yScreen = (int)lScreenSize.getHeight();
		setLocation((xScreen/2)-(xSize/2), (yScreen/2)-(ySize/2)); 
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		add(defaultMapButton, c);
		
		c.gridx = 0;
		c.gridy = 1;
		add(randomlyGeneratedMapButton, c);
	
		createButtonListeners();
	}
	protected void createButtonListeners(){
		defaultMapButton.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  lModel.getViewManager().showChitPlacementSelection();
			  dispose();
		  }
		});
		
		randomlyGeneratedMapButton.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  lModel.getGameManager().getEnvironmentManager().randomlyGenerateMap();
			  lModel.getViewManager().showChitPlacementSelection();
			  dispose();
		  }
		});
	}
	
	

}
