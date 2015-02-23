package view.internals.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import model.ViewModel;
import config.Config.DwellingType;

public class StartingLocation extends JInternalFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7557769900635702240L;
	
	private ViewModel lModel;
	private JLabel lStart = new JLabel("Select a starting dwelling:");
	
	private ArrayList<JButton> startingLocations = new ArrayList<JButton>();
	private DwellingType[] cStartingLocations;
	
	public StartingLocation(ViewModel aModel){
		super("Starting Location");
		lModel = aModel;
		cStartingLocations = lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().getStartingLocations();
		
		int xSize = 300;
		int ySize = 400;
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		Dimension lScreenSize = lModel.getScreenDimensions();
		
		int xScreen = (int)lScreenSize.getWidth();
		int yScreen = (int)lScreenSize.getHeight();
		
		setLocation((xScreen/4)-(xSize/2), (yScreen/2)-(ySize/2));
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		for(DwellingType dType: cStartingLocations){
			startingLocations.add(new JButton(dType.name(), new ImageIcon(lModel.getGameManager().getDwellingImage(dType))));
		}
		
		c.gridx = 0;
		c.gridy = 0;
		add(lStart, c);
		
		for(int i = 0; i < startingLocations.size(); i++){
			c.gridx = 0;
			c.gridy = i+1;
			add(startingLocations.get(i), c);
		}
		
		createButtonListeners();
		setVisible(true);
	}
	
	protected void createButtonListeners(){
		for(int i = 0; i < startingLocations.size(); i++){
			String text = startingLocations.get(i).getText();
			startingLocations.get(i).addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					lModel.setPlayerStartingLocation(text);
					lModel.requestPlayerMenu();
					dispose();
				}
			});
		}
	}
}