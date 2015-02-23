package view.internals.game;

import game.entity.Monster;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import model.ViewModel;

public class ChitPlacementSelection extends JInternalFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1998851506025417299L;
	
	private ViewModel lModel;
	
	private ArrayList<JLabel> cheatModeDescription  = new ArrayList<>();
	
	
	private JButton manuallyPlaceChits;
	private JButton randomlyPlaceChits;
	
	public ChitPlacementSelection(ViewModel aModel){
		super("Cheat Mode",false,false,false,true);
		
		lModel = aModel;
		
		// Set JLabels and JButtons
		cheatModeDescription.add(new JLabel("Would you like to turn on cheat mode?"));
		//cheatModeDescription.add(new JLabel("In cheat mode the all chits all placed manually."));
		//cheatModeDescription.add(new JLabel("In the future cheat mode will allow the user to control dice rolls."));
		manuallyPlaceChits = new JButton("Yes");
		randomlyPlaceChits = new JButton("No");
		
		// Set the size of the Chit List Window
		int xSize = 0;
		int ySize = 200;
		
		for (int i = 0; i < cheatModeDescription.size(); i++) {
			if (xSize < (int)cheatModeDescription.get(i).getPreferredSize().getWidth() + 100) {
				xSize = (int)cheatModeDescription.get(i).getPreferredSize().getWidth() + 100;
			}
		}
		
		//ySize += (int)cheatModeDescription.get(0).getPreferredSize().getHeight() * cheatModeDescription.size();
		//ySize += manuallyPlaceChits.getPreferredSize().getHeight();

		System.out.println(cheatModeDescription.get(0).getPreferredSize().getWidth());
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		// Set the location of the window to appear in the bottom left of the main application
		Dimension lScreenSize = lModel.getScreenDimensions();
		int xScreen = (int)lScreenSize.getWidth();
		int yScreen = (int)lScreenSize.getHeight();
		setLocation((xScreen/2)-(xSize/2), (yScreen/2)-(ySize/2)); 
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
				 
		for (int i = 0; i < cheatModeDescription.size(); i++) {
			c.anchor = GridBagConstraints.NORTH;
			//c.ipadx = 50;
			c.ipady = 0;
			c.gridx = 0;
			c.gridy = i;
			c.gridwidth = 2;
			add(cheatModeDescription.get(i), c);
		}
		
		c.anchor = GridBagConstraints.CENTER;
		//c.fill = GridBagConstraints.NONE;
		//c.ipadx = 50;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = cheatModeDescription.size();
		c.gridwidth = 1;
		add(manuallyPlaceChits, c);
		
		c.anchor = GridBagConstraints.CENTER;
		//c.fill = GridBagConstraints.NONE;
		//c.ipadx = 0;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = 1;
		c.gridy = cheatModeDescription.size();
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
			  lModel.requestChitList();
			  lModel.enableCheat();
			  dispose();
		  }
		});
		
		randomlyPlaceChits.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  lModel.getGameManager().getEnvironmentManager().getChitFactory().addChitsRandomly();
			  lModel.getGameManager().getEnvironmentManager().setDwellingTypesAndGhosts();
			  lModel.startGame();
			  dispose();
		  }
		});
	}
}