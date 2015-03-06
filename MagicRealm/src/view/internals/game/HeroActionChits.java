package view.internals.game;

import game.chit.ActionChit;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import model.ViewModel;

public class HeroActionChits extends JInternalFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -797938516902370365L;

	private JLabel lChitType;
	private ArrayList<JButton> lHeroFightChits;
	
	public HeroActionChits(ViewModel aModel){
		super("Hero Chits",true,false,false,true);

		int xSize = 400;
		int ySize = 500;
		ArrayList<ActionChit> lHeroChits = aModel.getGameState().getPlayer(aModel.getLocalPlayerNum()).getChosenHero().getActionChits();
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		setLocation(0, 0);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		lHeroFightChits = new ArrayList<JButton>();
		 
		for(ActionChit lActionChit: lHeroChits){
			if(lActionChit.getType().equals("FIGHT"))
				lHeroFightChits.add(new JButton(lActionChit.toString()));
		}
		
		lChitType = new JLabel("Fight Chits");
		c.gridx = 0;
		c.gridy = 0;
		add(lChitType, c);
		
		for(int i = 0; i < lHeroFightChits.size(); i++){
			c.gridx = 0;
			c.gridy = i + 1;
			add(lHeroFightChits.get(i), c);
		}
		
		setVisible(true);
	}
}
