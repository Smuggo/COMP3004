package view.internals.game;

import game.chit.Chit;
import game.chit.ChitFactory;
import game.environment.hex.Hextile;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import config.Config;
import model.ViewModel;

public class ChitList extends JInternalFrame{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5919121963644991941L;
	private ViewModel lModel;
	private ArrayList<JButton> chitButtons = new ArrayList<JButton>();
	
	private JLabel chitSelectionDescription = new JLabel();
	
	private ChitFactory chitFactory;
	private ArrayList<Chit> chits = new ArrayList<Chit>();
	private ArrayList<Hextile> hextiles;
	private Hextile hextile = null;
	boolean lostCity = false;
	boolean lostCastle = false;
	boolean warningChit = false;
	boolean otherChit = false;
	
	public ChitList(ViewModel aModel){
		super("Chit List",false,false,false,true);
		
		lModel = aModel;
		chitFactory = lModel.getGameManager().getEnvironmentManager().getChitFactory();
		hextiles = lModel.getGameManager().getEnvironmentManager().getHextiles();
		
		determineChitPlacement();

		// Set the size of the Chit List Window
		int xSize = 300;
		int ySize = 600;
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		// Set the location of the window to appear in the center of the main application
		Dimension lScreenSize = lModel.getScreenDimensions();
		int xScreen = (int)lScreenSize.getWidth();
		int yScreen = (int)lScreenSize.getHeight();
		setLocation((xScreen/2)-(xSize/2), (yScreen/2)-(ySize/2)); 
		
		// Set Buttons
		for(int i = 0; i < chits.size(); i++){
			chitButtons.add(new JButton(chits.get(i).getName()));
		}
		
		// Create Layout
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// Place Title
		c.gridx = 0;
		c.gridy = 0;
		add(chitSelectionDescription, c);
		
		// Layout Content
		for(int i = 0; i < chitButtons.size(); i++){
			c.gridx = 0;
			c.gridy = i+1;
			add(chitButtons.get(i), c);
		}
		// Attach Button Listener
		createButtonListeners();
	}
	
	protected void createButtonListeners(){
		for(int i = 0; i < chitButtons.size(); i++){
			Chit correspondingChit = chits.get(i);
			chitButtons.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					// We are adding the last chit
					if (chitFactory.getSiteSoundLostCastleChits().size() == 1) {
						chitFactory.addChitManually(correspondingChit, hextile, lostCity, lostCastle, warningChit);
						dispose();
					}
					else {
						chitFactory.addChitManually(correspondingChit, hextile, lostCity, lostCastle, warningChit);
						// Call another instince
						lModel.requestChitList(lModel);
						dispose();
					}
				}
			});
		}
	}
	
	// This can be broken into three functions and added to the chitFactory
	private void determineChitPlacement() {
		
		// We are adding to lost City
		if (chitFactory.getLostCityChits().size() < 5) {
			chits.addAll(chitFactory.getSiteSoundChits());
			lostCity = true;
			chitSelectionDescription.setText("Select 5 chits to be added to the Lost City");
			return;
		}
		// Add to Lost Castle
		else if (chitFactory.getLostCastleChits().size() < 5) {
			chits.addAll(chitFactory.getSiteSoundChits());
			lostCastle = true;
			chitSelectionDescription.setText("Select 5 chits to be added to the Lost Castle");
			return;
		}
		// May add to a hextile
		// Place 1 Cave Warning Chit and 1 siteSoundLostCityChits on each of the 5 Cave Hextiles
		// Place 1 Mountain Warning Chit and 1 siteSoundLostCastleChits on each of the 5 Mountain Hextiles
		// Place 1 Valley Warning Chit on each of the 5 Valley Hextiles
		// Place 1 WOODS Warning Chit on each of the 5 WOODS Tiles
		else {
			for (int i = 0; i < hextiles.size(); i++) {
				if (hextiles.get(i).getWarningChit() == null) {
					hextile = hextiles.get(i);
					warningChit = true;
					chitSelectionDescription.setText("Select a warning chit to be placed in " + hextiles.get(i).getName());
					
					if (hextiles.get(i).getHextileType() == Config.HextileType.CAVE) {
						chits.addAll(chitFactory.getCaveChits());
						return;
					}
					else if (hextiles.get(i).getHextileType() == Config.HextileType.MOUNTAIN) {
						chits.addAll(chitFactory.getMountainChits());
						return;
					}
					else if (hextiles.get(i).getHextileType() == Config.HextileType.VALLEY) {
						chits.addAll(chitFactory.getValleyChits());
						return;
					}
					else if (hextiles.get(i).getHextileType() == Config.HextileType.WOODS) {
						chits.addAll(chitFactory.getWoodsChits());
						return;
					}
				}
			}
			
			for (int i = 0; i < hextiles.size(); i++) {
				if (hextiles.get(i).getOtherChit() == null) {
					if (hextiles.get(i).getHextileType() == Config.HextileType.CAVE) {
						chitSelectionDescription.setText("Select a site/sound chit or the Lost City Chit to be placed in " + hextiles.get(i).getName());
						hextile = hextiles.get(i);
						otherChit = true;
						chits.addAll(chitFactory.getSiteSoundLostCityChits());
						return;
					}
					else if (hextiles.get(i).getHextileType() == Config.HextileType.MOUNTAIN) {
						chitSelectionDescription.setText("Select a site/sound chit or the Lost Castle Chit to be placed in " + hextiles.get(i).getName());
						hextile = hextiles.get(i);
						otherChit = true;
						chits.addAll(chitFactory.getSiteSoundLostCastleChits());
						return;
					}
				}
			}
		}
	}
}