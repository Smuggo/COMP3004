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
		
		//MOVE
		lCharge.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				enableOrDisableButtons(false);
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().setCombatStage(CombatStage.MOVE);
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().setMoveType(MoveType.CHARGE);
				lModel.requestHeroActionChits();
			}
		});
		lDodge.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				enableOrDisableButtons(false);
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().setCombatStage(CombatStage.MOVE);
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().setMoveType(MoveType.DODGE);
				lModel.requestHeroActionChits();
			}
		});
		lDuck.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				enableOrDisableButtons(false);
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().setCombatStage(CombatStage.MOVE);
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().setMoveType(MoveType.DUCK);
				lModel.requestHeroActionChits();
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
}
