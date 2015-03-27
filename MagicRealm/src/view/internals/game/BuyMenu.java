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
	
	Hero theHero;
	Native theNativeGroup;
	
	public BuyMenu(ViewModel aModel, Native nativeGroup){
		super("Buy Menu",true,false,false,true);
		lModel = aModel;
		// The hero
		theHero = lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero();
		theNativeGroup = nativeGroup;
		numRows = nativeGroup.getWeapons().size();
		
		int xSize = columnSize * numColumns + 50;
		//int ySize = lModel.getScreenDimensions().height-340;
		int ySize = rowSize * numRows + 200;
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		setLocation(lModel.getScreenDimensions().width/2 - xSize/2,
					lModel.getScreenDimensions().height/2 - ySize/2);
		
		
		
		Object[][] data = new Object[nativeGroup.getWeapons().size()][columnNames.length];
		
		for (int i = 0; i < nativeGroup.getWeapons().size(); i++) {
			data[i][0] = nativeGroup.getWeapons().get(i).getWeaponType();
			data[i][1] = nativeGroup.getWeapons().get(i).getPrice();
			data[i][2] = "";
			data[i][3] = "";
			data[i][4] = "";
		}
		
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
		add(buy, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.CENTER;
		add(cancel, c);
				
		createButtonListeners();	
	}
	
	protected void createButtonListeners(){
		buy.addActionListener(new ActionListener() {
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
