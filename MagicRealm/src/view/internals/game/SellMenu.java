package view.internals.game;

import game.chit.Chit;
import game.entity.Hero;
import game.entity.Native;

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
	
	int rowNumber = 0;
	
	Hero theHero;
	Native theNativeGroup;
	
	
	public SellMenu(ViewModel aModel, Hero aHero, Native aNativeGroup){
		super("Sell Menu",true,false,false,true);
		lModel = aModel;
		// The hero
		theHero = lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero();
		theNativeGroup = aNativeGroup;
		numRows = aNativeGroup.getWeapons().size();
		
		int xSize = columnSize * numColumns + 50;
		//int ySize = lModel.getScreenDimensions().height-340;
		int ySize = rowSize * numRows + 200;
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		setLocation(lModel.getScreenDimensions().width/2 - xSize/2,
					lModel.getScreenDimensions().height/2 - ySize/2);
		
		
		// Init Array Size
		if (aHero.getWeapon() != null)
			rowNumber++;
		if (aHero.getHelmet() != null)
			rowNumber++;
		if (aHero.getSuit() != null)
			rowNumber++;
		if (aHero.getBreastplate() != null)
			rowNumber++;
		if (aHero.getShield() != null)
			rowNumber++;
		
		rowNumber += aHero.getOwnedTreasures().size();
		
		// Create Array
		
		Object[][] data = new Object[rowNumber][columnNames.length];
		
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
		JTable table = new JTable(data, columnNames);
		
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
				
		createButtonListeners();	
	}
	
	private void addToData(Object[][] data, int i, Object name, int price, String unknow, String unknown, String other) {

		data[i][0] = name;
		data[i][1] = price;
		data[i][2] = unknow;
		data[i][3] = unknown;
		data[i][4] = other;
	}
	
	protected void createButtonListeners(){
		sell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
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
}
