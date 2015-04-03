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

import config.Config;
import config.Config.ArmourType;
import model.ViewModel;

public class SellMenu extends JInternalFrame{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8240423330619669512L;

	private ViewModel lModel;

	String[] columnNames = {"Name",
            "Price",
            "Side 1",
            "Side 2",
            "Other"};
	
	JButton sell = new JButton("Sell Now");
	JButton cancel = new JButton("Cancel");
	
	int columnSize = 125;
	int rowSize = 16;
	int numColumns = 5;
	int numRows = 0;
	
	//int rowNumber = 0;
	
	//Hero theHero;
	//Native theNativeGroup;
	
	JTable table;
	
	
	public SellMenu(ViewModel aModel, Hero aHero, Native aNativeGroup){
		super("Sell Menu",true,false,false,true);
		
		// Set variables
		
		lModel = aModel;
		//theHero = aHero; //lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero();
		//theNativeGroup = aNativeGroup;

		// Init Array Size
		if (aHero.getWeapon() != null)
			numRows++;
		if (aHero.getHelmet() != null)
			numRows++;
		if (aHero.getSuit() != null)
			numRows++;
		if (aHero.getBreastplate() != null)
			numRows++;
		if (aHero.getShield() != null)
			numRows++;
		
		numRows += aHero.getOwnedTreasures().size();
		
		// Create Array
		
		Object[][] data = new Object[numRows][columnNames.length];
		
		// Init Window 
		int xSize = columnSize * numColumns + 50;
		int ySize = rowSize * numRows + 200;
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		setLocation(lModel.getScreenDimensions().width/2 - xSize/2,
					lModel.getScreenDimensions().height/2 - ySize/2);
		
		// Fill Array	
		int index = 0;
		
		if (aHero.getWeapon() != null)
			addToData(data, index++, aHero.getWeapon().getWeaponType(),aHero.getWeapon().getPrice(), "", "", "");
		if (aHero.getHelmet() != null)
			addToData(data, index++, aHero.getHelmet().getArmourType(),aHero.getHelmet().getPriceIntact(), "", "", "");
		if (aHero.getSuit() != null)
			addToData(data, index++, aHero.getSuit().getArmourType(),aHero.getSuit().getPriceIntact(), "", "", "");
		if (aHero.getBreastplate() != null)
			addToData(data, index++, aHero.getBreastplate().getArmourType(),aHero.getBreastplate().getPriceIntact(), "", "", "");
		if (aHero.getShield() != null)
			addToData(data, index++, aHero.getShield().getArmourType(),aHero.getShield().getPriceIntact(), "", "", "");
		
		for (int i = 0; i < aHero.getOwnedTreasures().size(); i++) {
			addToData(data, index++, aHero.getOwnedTreasures().get(i).getName(),aHero.getOwnedTreasures().get(i).getGoldValue(), "", "", "");
		}
		
		
		// Add Array to Table
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
		add(sell, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.CENTER;
		add(cancel, c);
				
		createButtonListeners(aHero, aNativeGroup);	
	}
	
	private void addToData(Object[][] data, int i, Object name, int price, String unknow, String unknown, String other) {

		data[i][0] = name;
		data[i][1] = price;
		data[i][2] = unknow;
		data[i][3] = unknown;
		data[i][4] = other;
	}
	
	protected void createButtonListeners(Hero aHero, Native aNativeGroup){
		sell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				
				Object obj = findMatchingObject(table.getValueAt(table.getSelectedRow(), 0).toString(), aHero);
				if (obj != null && obj.getClass() == Weapon.class) {
					Weapon theWeaponBeingSold = (Weapon) obj;
					aHero.setWeapon(null);
					aNativeGroup.addWeapon(theWeaponBeingSold);
					aHero.setGold(aHero.getGold() + theWeaponBeingSold.getPrice());
					//System.out.println("So you wanna be a pokemon master?");
				}
				else if (obj != null && obj.getClass() == Armour.class) {
					Armour theArmourBeingSold = (Armour) obj;
					
					if (theArmourBeingSold.getArmourType() == ArmourType.HELMET) {
						aHero.setHelmet(null);
					}
					else if (theArmourBeingSold.getArmourType() == ArmourType.SUIT_OF_ARMOUR) {
						aHero.setSuit(null);
					}
					else if (theArmourBeingSold.getArmourType() == ArmourType.BREASTPLATE) {
						aHero.setBreastplate(null);
					}
					else if (theArmourBeingSold.getArmourType() == ArmourType.SHIELD) {
						aHero.setShield(null);
					}	
					
					aNativeGroup.addArmor(theArmourBeingSold);
					aHero.setGold(aHero.getGold() + theArmourBeingSold.getPriceIntact());
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
	
	private Object findMatchingObject(String name, Hero aHero) {
		
		// Look through weapons
		if (aHero.getWeapon() != null && name == aHero.getWeapon().getWeaponType().toString()) {
			return aHero.getWeapon();
		}
		
		// Look though armours
		else if (aHero.getHelmet() != null && name == aHero.getHelmet().getArmourType().toString()) {
			return aHero.getHelmet();
		}
		else if (aHero.getSuit() != null && name == aHero.getSuit().getArmourType().toString()) {
			return aHero.getSuit();
		}
		else if (aHero.getBreastplate() != null && name == aHero.getBreastplate().getArmourType().toString()) {
			return aHero.getBreastplate();
		}
		else if (aHero.getShield() != null && name == aHero.getShield().getArmourType().toString()) {
			return aHero.getShield();
		}
		
		return null; // This should not happen
	}
}
