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

//Choices for if the player rolls a 1 on PEER or LOCATE
public class SearchChoiceSelection extends JInternalFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4870288106399935410L;
	
	private ViewModel lModel;
	
	private JLabel lChoose;
	private JButton lSearch1;
	private JButton lSearch2;
	private JButton lSearch3;
	private JButton lSearch4;
	
	private SearchType lSearchType;
	
	public SearchChoiceSelection(ViewModel aModel, SearchType aSearchType){
		super("Search Choice",true,false,false,true);
		
		lSearchType = aSearchType;
		lModel = aModel;
		
		setPreferredSize(new Dimension(200, 200));
		setSize(200, 200);
		
		setLocation(0, 0);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		lChoose = new JLabel("Search Findings:");
		c.gridx = 0;
		c.gridy = 0;
		add(lChoose, c);
		
		//If the search type was a PEER, give options for all PEER table results
		if(aSearchType.equals(SearchType.PEER)){
			lSearch1 = new JButton("Clues and Paths");
			c.gridx = 0;
			c.gridy = 1;
			add(lSearch1, c);
			
			lSearch2 = new JButton("Hidden Enemies and Paths");
			c.gridx = 0;
			c.gridy = 2;
			add(lSearch2, c);
			
			lSearch3 = new JButton("Hidden Enemies");
			c.gridx = 0;
			c.gridy = 3;
			add(lSearch3, c);
			
			lSearch4 = new JButton("Clues");
			c.gridx = 0;
			c.gridy = 4;
			add(lSearch4, c);
			
		}
		//If the search type was a LOCATE, give options for all LOCATE table results
		else {
			lSearch1 = new JButton("Passages and Clues");
			c.gridx = 0;
			c.gridy = 1;
			add(lSearch1, c);
			
			lSearch2 = new JButton("Passages");
			c.gridx = 0;
			c.gridy = 2;
			add(lSearch2, c);
			
			lSearch3 = new JButton("Discover Sites");
			c.gridx = 0;
			c.gridy = 3;
			add(lSearch3, c);
		}
		
		setVisible(true);
	}
	protected void createButtonListeners(){
		if(lSearchType.equals(SearchType.PEER)){
			lSearch1.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					dispose();
				}
			});
			lSearch2.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					dispose();
				}
			});
			lSearch3.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					dispose();
				}
			});
			lSearch4.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					dispose();
				}
			});
		}
		else{
			lSearch1.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					dispose();
				}
			});
			lSearch2.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					dispose();
				}
			});
			lSearch3.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					dispose();
				}
			});
		}
	}
}
