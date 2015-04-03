package view.internals.game;

import game.entity.Hero;
import game.entity.Native;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;

import model.ViewModel;

public class TradeMenu extends JInternalFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8935077420291925770L;
	
	private ViewModel lModel;
	
	private JButton lBuy;
	private JButton lSell;
	private JButton lCancel;
	
	Hero lHero;
	Native lNativeGroup;
	
	
	public TradeMenu(ViewModel aModel, Hero aHero, Native aNativeGroup){
		super("Trade Menu",true,false,false,true);

		lModel = aModel;
		lHero = aHero;
		lNativeGroup = aNativeGroup;
		
		//int xSize = lModel.getScreenDimensions().width/2;
		//int ySize = lModel.getScreenDimensions().height-340;
		
		int xSize = 300;
		int ySize = 300;
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		setLocation(0, 0);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		lBuy = new JButton("Buy");
		c.gridx = 0;
		c.gridy = 0;
		add(lBuy, c);
		
		lSell = new JButton("Sell");
		c.gridx = 0;
		c.gridy = 1;
		add(lSell, c);
		
		lCancel = new JButton("Cancel");
		c.gridx = 0;
		c.gridy = 2;
		add(lCancel, c);
		
		createButtonListeners();
	}
	
	protected void createButtonListeners(){
		lBuy.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				lModel.requestBuyMenu(lHero, lNativeGroup);
			}	
		});
		lSell.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				lModel.requestSellMenu(lHero, lNativeGroup);
			}	
		});
		lCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}	
		});
	}
}
