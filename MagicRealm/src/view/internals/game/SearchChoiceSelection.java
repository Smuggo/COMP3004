package view.internals.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JInternalFrame;

import config.Config.SearchType;
import model.ViewModel;

public class SearchChoiceSelection extends JInternalFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4870288106399935410L;
	
	private ViewModel lModel;
	
	private JButton lSearch1;
	private JButton lSearch2;
	private JButton lSearch3;
	private JButton lSearch4;
	
	public SearchChoiceSelection(ViewModel aModel, SearchType aSearchType){
		super("Search Choice",true,false,false,true);
		
		lModel = aModel;
		
		setPreferredSize(new Dimension(200, 200));
		setSize(200, 200);
		
		setLocation(0, 0);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		setVisible(true);
	}
}
