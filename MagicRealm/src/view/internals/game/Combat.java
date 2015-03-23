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
import config.Config.MoveType;
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
	private JButton lShieldThrust;
	private JButton lShieldSwing;
	private JButton lShieldSmash;
	
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
		c.gridy = 0;
		add(lCombatCommand, c);
		
		lThrust = new JButton("Thrust");
		c.gridx = 0;
		c.gridy = 1;
		add(lThrust, c);
		
		lSwing = new JButton("Swing");
		c.gridx = 0;
		c.gridy = 2;
		add(lSwing, c);
		
		lSmash = new JButton("Smash");
		c.gridx = 0;
		c.gridy = 3;
		add(lSmash, c);
		
		lCharge = new JButton("Charge");
		c.gridx = 0;
		c.gridy = 1;
		lCharge.setVisible(false);
		add(lCharge, c);
		
		lDodge = new JButton("Dodge");
		c.gridx = 0;
		c.gridy = 2;
		lDodge.setVisible(false);
		add(lDodge, c);
		
		lDuck = new JButton("Duck");
		c.gridx = 0;
		c.gridy = 3;
		lDuck.setVisible(false);
		add(lDuck, c);
		
		lShieldThrust = new JButton("Thrust");
		c.gridx = 0;
		c.gridy = 1;
		lShieldThrust.setVisible(false);
		add(lShieldThrust, c);
		
		lShieldSwing = new JButton("Swing");
		c.gridx = 0;
		c.gridy = 2;
		lShieldSwing.setVisible(false);
		add(lShieldSwing, c);
		
		lShieldSmash = new JButton("Smash");
		c.gridx = 0;
		c.gridy = 3;
		lShieldSmash.setVisible(false);
		add(lShieldSmash, c);
		
		createButtonListeners();
		setVisible(true);
	}
	protected void createButtonListeners(){
		//FIGHT
		lThrust.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				enableOrDisableButtons(false);
				if(lModel.assignFightChoice(FightType.THRUST)){
					lModel.refreshGameState();
					lModel.requestHeroActionChits();
				}
			}
		});
		lSwing.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				enableOrDisableButtons(false);
				if(lModel.assignFightChoice(FightType.SWING)){
					lModel.refreshGameState();
					lModel.requestHeroActionChits();
				}
			}
		});
		lSmash.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				enableOrDisableButtons(false);
				if(lModel.assignFightChoice(FightType.SMASH)){
					lModel.refreshGameState();
					lModel.requestHeroActionChits();
				}
			}
		});
		
		//MOVE
		lCharge.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				enableOrDisableButtons(false);
				if(lModel.assignMoveChoice(MoveType.CHARGE)){
					lModel.refreshGameState();
					lModel.requestHeroActionChits();
				}
			}
		});
		lDodge.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				enableOrDisableButtons(false);
				if(lModel.assignMoveChoice(MoveType.DODGE)){
					lModel.refreshGameState();
					lModel.requestHeroActionChits();
				}
			}
		});
		lDuck.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				enableOrDisableButtons(false);
				if(lModel.assignMoveChoice(MoveType.DUCK)){
					lModel.refreshGameState();
					lModel.requestHeroActionChits();
				}
			}
		});
		
		//BLOCKING
		lShieldThrust.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().getShield().setProtectsFrom(FightType.THRUST);
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().setCombatStage(CombatStage.WAITING);
				enableOrDisableButtons(false);
			}
		});
		
		lShieldSwing.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().getShield().setProtectsFrom(FightType.SWING);
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().setCombatStage(CombatStage.WAITING);
				enableOrDisableButtons(false);
			}
		});
		
		lShieldSmash.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().getShield().setProtectsFrom(FightType.SMASH);
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().setCombatStage(CombatStage.WAITING);
				enableOrDisableButtons(false);
			}
		});
	}
	
	public void enableOrDisableButtons(boolean aState){
		lThrust.setEnabled(aState);
		lSwing.setEnabled(aState);
		lSmash.setEnabled(aState);
		lCharge.setEnabled(aState);
		lDodge.setEnabled(aState);
		lDuck.setEnabled(aState);
		lShieldThrust.setEnabled(aState);
		lShieldSwing.setEnabled(aState);
		lShieldSmash.setEnabled(aState);
	}
	
	public void selectMovement(){
		enableOrDisableButtons(true);
		
		lCombatCommand.setText("Select Movement:");
		lThrust.setVisible(false);
		lSwing.setVisible(false);
		lSmash.setVisible(false);
		lCharge.setVisible(true);
		lDodge.setVisible(true);
		lDuck.setVisible(true);
	}
	
	public void selectShieldDirection(){
		enableOrDisableButtons(true);
		
		if(lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().getShield() != null){
			lCombatCommand.setText("Select Shield Direction:");
			lCharge.setVisible(false);
			lDodge.setVisible(false);
			lDuck.setVisible(false);
			lShieldThrust.setVisible(true);
			lShieldSwing.setVisible(true);
			lShieldSmash.setVisible(true);
		} else
			enableOrDisableButtons(false);
			lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().setCombatStage(CombatStage.WAITING);
	}
}
