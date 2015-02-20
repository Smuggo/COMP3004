package view.internals.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import config.Config.SearchType;
import model.ViewModel;

public class Searching extends JInternalFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6527053954009154481L;

	private ViewModel lModel;
	private JLabel lSelectTable;
	private JButton lPeerTable;
	private JButton lLocateTable;
	
	public Searching(ViewModel aModel){
		lModel = aModel;
		
		setPreferredSize(new Dimension(300, 300));
		setSize(300, 300);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		lSelectTable = new JLabel("Select a table:");
		c.gridx = 0;
		c.gridy = 0;
		add(lSelectTable, c);
		
		lPeerTable = new JButton("Peer: Paths and Hidden Enemies");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		add(lPeerTable, c);
		
		lLocateTable = new JButton("Locate: Passages and Treasure Locations");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		add(lLocateTable, c);
		
		createButtonListeners();
		setVisible(true);
	}
	
	protected void createButtonListeners(){
		
		lPeerTable.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().setSearchType(SearchType.PEER);
				dispose();
			}
		});
		lLocateTable.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().setSearchType(SearchType.LOCATE);
				dispose();
			}
		});
	}
}
