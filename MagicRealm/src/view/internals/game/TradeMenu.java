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
	
	//Hero lHero;
	//Native lNativeGroup;
	
	
	public TradeMenu(ViewModel aModel, Hero aHero, Native aNativeGroup){
		super("Trade Menu",true,false,false,true);

		lModel = aModel;
		//lHero = aHero;
		//lNativeGroup = aNativeGroup;
		
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
		
		if(aHero.getWeapon() == null && aHero.getHelmet() == null && aHero.getBreastplate() == null && aHero.getSuit() == null && aHero.getShield() == null) {
			lSell.setEnabled(false);
		}
		if(aNativeGroup.getWeapons().size() == 0 && aNativeGroup.getArmours().size() == 0) {
			lBuy.setEnabled(false);
		}
		
		createButtonListeners(aHero, aNativeGroup);
	}
	
	protected void createButtonListeners(Hero aHero, Native aNativeGroup){
		lBuy.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				lModel.requestBuyMenu(aHero, aNativeGroup);
			}	
		});
		lSell.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				lModel.requestSellMenu(aHero, aNativeGroup);
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
