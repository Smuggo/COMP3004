package view.internals.game;

import game.chit.Chit;
import game.entity.Hero;
import game.entity.Native;
import game.item.Armour;
import game.item.Weapon;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import config.Config.ArmourType;
import config.Config.WeaponType;
import model.ViewModel;

public class BuyMenu extends JInternalFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -337052967793472264L;
	
	private ViewModel lModel;

	String[] columnNames = {"Name",
            "Price",
            "Side 1",
            "Side 2",
            "Other"};
	
	JButton buy = new JButton("Buy Now");
	JButton cancel = new JButton("Cancel");
	
	int columnSize = 125;
	int rowSize = 16;
	int numColumns = 5;
	int numRows = 0;
	
	//int rowNumber = 0;
	
	//Hero theHero;
	//Native theNativeGroup;
	
	JTable table;
	
	public BuyMenu(ViewModel aModel, Hero aHero, Native aNativeGroup){
		super("Buy Menu",true,false,false,true);
		lModel = aModel;
		// The hero
		//theHero = lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero();
		//theNativeGroup = aNativeGroup;
		numRows = aNativeGroup.getWeapons().size() + aNativeGroup.getArmours().size();
		
		int xSize = columnSize * numColumns + 50;
		//int ySize = lModel.getScreenDimensions().height-340;
		int ySize = rowSize * numRows + 200;
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		setLocation(lModel.getScreenDimensions().width/2 - xSize/2,
					lModel.getScreenDimensions().height/2 - ySize/2);
		
		
		// Calc number of rows
		//rowNumber += aNativeGroup.getWeapons().size();
		//rowNumber += aNativeGroup.getArmours().size();
		
		// Create data object
		Object[][] data = new Object[numRows][columnNames.length];
		
		
		// FIll data object
		int index = 0;
		
		for (int i = 0; i < aNativeGroup.getWeapons().size(); i++) {
			data[index][0] = aNativeGroup.getWeapons().get(i).getWeaponType();
			data[index][1] = aNativeGroup.getWeapons().get(i).getPrice();
			data[index][2] = "";
			data[index][3] = "";
			data[index][4] = "";
			index++;
		}
		
		for (int i = 0; i < aNativeGroup.getArmours().size(); i++) {
			data[index][0] = aNativeGroup.getArmours().get(i).getArmourType();
			data[index][1] = aNativeGroup.getArmours().get(i).getPriceIntact();
			data[index][2] = "";
			data[index][3] = "";
			data[index][4] = "";
			index++;
		}
		
		table = new JTable(data, columnNames);
		
		table.setPreferredScrollableViewportSize(new Dimension(600, rowSize * numRows));
        table.setFillsViewportHeight(true);
        
        // Force Users to Select only one row
        table.setSelectionModel(new ForcedListSelectionModel());
        
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
		
		
       // table.setCellSelectionAllowed(false);
        
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
		
        // Create Layout
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Add the scroll pane to this panel.
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		add(scrollPane, c);
	
		// Place Buttons
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.CENTER;
		add(buy, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.CENTER;
		add(cancel, c);
				
		createButtonListeners(aHero, aNativeGroup);	
	}
	
	protected void createButtonListeners(Hero aHero, Native aNativeGroup){
		buy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				
				Object obj = findMatchingObject(table.getValueAt(table.getSelectedRow(), 0).toString(), aNativeGroup);
				
				if (obj != null && obj.getClass() == Weapon.class) {
					Weapon theWeaponBeingBought = (Weapon) obj;
					aHero.setWeapon(theWeaponBeingBought);
					aNativeGroup.removeWeapon(theWeaponBeingBought);
					aHero.setGold(aHero.getGold() - theWeaponBeingBought.getPrice());
					//System.out.println("So you wanna be a pokemon master?");
				}
				else if (obj != null && obj.getClass() == Armour.class) {
					Armour theArmourBeingBought = (Armour) obj;
					
					if (theArmourBeingBought.getArmourType() == ArmourType.HELMET) {
						aHero.setHelmet(theArmourBeingBought);
					}
					else if (theArmourBeingBought.getArmourType() == ArmourType.SUIT_OF_ARMOUR) {
						aHero.setSuit(theArmourBeingBought);
					}
					else if (theArmourBeingBought.getArmourType() == ArmourType.BREASTPLATE) {
						aHero.setBreastplate(theArmourBeingBought);
					}
					else if (theArmourBeingBought.getArmourType() == ArmourType.SHIELD) {
						aHero.setShield(theArmourBeingBought);
					}	
					
					aNativeGroup.removeArmor(theArmourBeingBought);
					aHero.setGold(aHero.getGold() - theArmourBeingBought.getPriceIntact());
				}
				
				dispose();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
	}
	
	private Object findMatchingObject(String name, Native aNativeGroup) {
		
		// Look through weapons
		for (int i = 0; i < aNativeGroup.getWeapons().size(); i++) {
			if (name == aNativeGroup.getWeapons().get(i).getWeaponType().toString()) {
				return aNativeGroup.getWeapons().get(i);
			}
		}
		
		// Look through armor
		for (int i = 0; i < aNativeGroup.getArmours().size(); i++) {
			if (name == aNativeGroup.getArmours().get(i).getArmourType().toString()) {
				return aNativeGroup.getArmours().get(i);
			}
		}
		return null; // This should not happen
	}
}
