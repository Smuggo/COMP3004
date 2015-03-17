package view.internals.game;

import game.chit.ActionChit;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import config.Config.CombatStage;
import model.ViewModel;

public class HeroActionChits extends JInternalFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -797938516902370365L;

	private JLabel lFightType;
	private JLabel lMoveType;
	
	private ArrayList<ActionChit> lHeroFight;
	private ArrayList<ActionChit> lHeroMove;
	
	private ArrayList<JButton> lHeroFightChits;
	private ArrayList<JButton> lHeroMoveChits;
	
	private ViewModel lModel;
	
	public HeroActionChits(ViewModel aModel){
		super("Hero Chits",true,false,false,true);

		int xSize = 400;
		int ySize = 500;
		lHeroFight = aModel.getGameState().getPlayer(aModel.getLocalPlayerNum()).getChosenHero().getFightChits();
		lHeroMove = aModel.getGameState().getPlayer(aModel.getLocalPlayerNum()).getChosenHero().getMoveChits();
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		setLocation(0, 0);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		lHeroFightChits = new ArrayList<JButton>();
		lHeroMoveChits = new ArrayList<JButton>();
		lModel = aModel;
		 
		for(ActionChit lActionChit: lHeroFight){
			lHeroFightChits.add(new JButton(lActionChit.toString()));
		}
		
		lFightType = new JLabel("Fight Chits");
		c.gridx = 0;
		c.gridy = 0;
		add(lFightType, c);
		
		for(int i = 0; i < lHeroFightChits.size(); i++){
			c.gridx = 0;
			c.gridy = i + 1;
			add(lHeroFightChits.get(i), c);
			if(!aModel.getGameState().getPlayer(aModel.getLocalPlayerNum()).getChosenHero().getCombatState().equals(CombatStage.FIGHT))
				lHeroFightChits.get(i).setEnabled(false);
		}
		
		for(ActionChit lActionChit: lHeroMove){
			lHeroMoveChits.add(new JButton(lActionChit.toString()));
		}
		
		lMoveType = new JLabel("Move Chits");
		c.gridx = 1;
		c.gridy = 0;
		add(lMoveType, c);
		
		for(int i = 0; i < lHeroMoveChits.size(); i++){
			c.gridx = 1;
			c.gridy = i + 1;
			add(lHeroMoveChits.get(i), c);
			if(!aModel.getGameState().getPlayer(aModel.getLocalPlayerNum()).getChosenHero().getCombatState().equals(CombatStage.MOVE))
				lHeroMoveChits.get(i).setEnabled(false);
		}
		
		createButtonListeners();
		setVisible(true);
	}
	protected void createButtonListeners(){
		for(int i = 0; i < lHeroFightChits.size(); i++){
			ActionChit lTempChit = lHeroFight.get(i);
			
			lHeroFightChits.get(i).addActionListener(new ActionListener()
			{
			 public void actionPerformed(ActionEvent e)
			 {
				 lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().setFightChoice(lTempChit);
				 lModel.selectCombatMovement();
				 dispose();
			 }
			});
		}
		for(int i = 0; i < lHeroMoveChits.size(); i++){
			ActionChit lTempChit = lHeroFight.get(i);
			
			lHeroMoveChits.get(i).addActionListener(new ActionListener()
			{
			 public void actionPerformed(ActionEvent e)
			 {
				 
				 lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().setMoveChoice(lTempChit);
				 lModel.selectBlockDirection();
				 dispose();
			 }
			});
		}
	}
}
