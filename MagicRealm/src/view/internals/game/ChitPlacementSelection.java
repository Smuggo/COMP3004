package view.internals.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import model.ViewModel;

public class ChitPlacementSelection extends JInternalFrame{
	
	// Change serial Version
	private static final long serialVersionUID = 3742758020392137899L;
	
	private ViewModel lModel;
	
	private JLabel cheatModeDescription;
	
	private JButton manuallyPlaceChits;
	private JButton randomlyPlaceChits;
	
	public ChitPlacementSelection(ViewModel aModel){
		super("Cheat Mode",false,false,false,true);
		
		lModel = aModel;
			
		// Set the size of the Chit List Window
		int xSize = 400;
		int ySize = 200;
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		// Set the location of the window to appear in the bottom left of the main application
		Dimension lScreenSize = lModel.getScreenDimensions();
		int xScreen = (int)lScreenSize.getWidth();
		int yScreen = (int)lScreenSize.getHeight();
		setLocation((xScreen/8)-(xSize/8), (3 * yScreen/4)-(3 * ySize/4)); 
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		cheatModeDescription = new JLabel("How would you like to place the chits?");
		c.ipadx = 50;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		add(cheatModeDescription, c);
		
		manuallyPlaceChits = new JButton("Manually");
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.fill = GridBagConstraints.NONE;
		c.ipadx = 50;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		add(manuallyPlaceChits, c);
		
		randomlyPlaceChits = new JButton("Randomly");
		c.anchor = GridBagConstraints.SOUTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		add(randomlyPlaceChits, c);
		
		createButtonListeners();
	}
	protected void createButtonListeners(){
		manuallyPlaceChits.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  lModel.getGameManager().getEnvironmentManager().getChitFactory().addChitsManually();
			  lModel.requestChitList(lModel);
			  dispose();
		  }
		});
		
		randomlyPlaceChits.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  lModel.getGameManager().getEnvironmentManager().getChitFactory().addChitsRandomly();
			  dispose();
		  }
		});
	}
}