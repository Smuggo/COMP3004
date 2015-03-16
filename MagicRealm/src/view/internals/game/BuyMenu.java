package view.internals.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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
	
	int columnSize = 50;
	
	public BuyMenu(ViewModel aModel){
		super("Buy Menu",true,false,false,true);
		lModel = aModel;
		
		int xSize = lModel.getScreenDimensions().width/2;
		//int ySize = lModel.getScreenDimensions().height-340;
		int ySize = columnNames.length * columnSize;
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		setLocation(lModel.getScreenDimensions().width/2 - xSize/2,
					lModel.getScreenDimensions().height/2 - ySize/2);
		
		
		Object[][] data = {
				{"Kathy", "Smith",
				"Snowboarding", new Integer(5), new Boolean(false)},
				{"John", "Doe",
				"Rowing", new Integer(3), new Boolean(true)},
				{"Sue", "Black",
				"Knitting", new Integer(2), new Boolean(false)},
				{"Jane", "White",
				"Speed reading", new Integer(20), new Boolean(true)},
				{"Joe", "Brown",
				"Pool", new Integer(10), new Boolean(false)}
				};
		
		JTable table = new JTable(data, columnNames);
		
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
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
		c.gridwidth = 5;
		c.gridheight = 5;
		add(scrollPane, c);
		 
		// Place Buttons
		c.gridx = 4;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		//add(buy, c);

		c.gridx = 5;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		//add(cancel, c);
				
				
	}
}
