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
		
		setPreferredSize(new Dimension(500, 500));
		setSize(500, 500);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		for(DwellingType dType: cStartingLocations){
			startingLocations.add(new JButton(new ImageIcon(lModel.getGameManager().getDwellingImage(dType))));
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
	}
}