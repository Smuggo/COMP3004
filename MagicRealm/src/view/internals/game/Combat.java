package view.internals.game;

import game.entity.Hero;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import config.Config.CombatStage;
import config.Config.FightType;
import model.ViewModel;

public class Combat extends JInternalFrame{
	private ViewModel lModel;
	
	private JLabel lCombatCommand;
	
	private JButton lThrust;
	private JButton lSwing;
	private JButton lSmash;
	private JButton lCharge;
	private JButton lDodge;
	private JButton lDuck;
	
	public Combat(ViewModel aModel){
		super("Combat View",true,false,false,true);
		
		lModel = aModel;

		int xSize = 400;
		int ySize = 400;
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		setLocation(0, 0);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		lCombatCommand = new JLabel("Select an Attack:");
		c.gridx = 0;
		c.gridy = 1;
		add(lCombatCommand, c);
		
		lThrust = new JButton("Thrust");
		c.gridx = 0;
		c.gridy = 2;
		add(lThrust, c);
		
		lSwing = new JButton("Swing");
		c.gridx = 0;
		c.gridy = 3;
		add(lSwing, c);
		
		lSmash = new JButton("Smash");
		c.gridx = 0;
		c.gridy = 4;
		add(lSmash, c);
		
		createButtonListeners();
		setVisible(true);
	}
	protected void createButtonListeners(){
		lThrust.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				enableOrDisableButtons(false);
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().setCombatStage(CombatStage.FIGHT);
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().setFightType(FightType.THRUST);
				lModel.requestHeroActionChits();
			}
		});
		lSwing.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				enableOrDisableButtons(false);
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().setCombatStage(CombatStage.FIGHT);
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().setFightType(FightType.SWING);
				lModel.requestHeroActionChits();
			}
		});
		lSmash.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				enableOrDisableButtons(false);
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().setCombatStage(CombatStage.FIGHT);
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().setFightType(FightType.SMASH);
				lModel.requestHeroActionChits();
			}
		});
	}
	
	public void enableOrDisableButtons(boolean aState){
		lThrust.setEnabled(aState);
		lSwing.setEnabled(aState);
		lSmash.setEnabled(aState);
	}
}
