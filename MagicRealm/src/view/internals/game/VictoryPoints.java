package view.internals.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.ViewModel;
import game.entity.Hero;

public class VictoryPoints extends JInternalFrame{
	/*After selecting a character, this frame will pop up to get the victory
	  information for that character.*/
	/**
	 * 
	 */
	private static final long serialVersionUID = 319803940359220114L;
	
	private JLabel pointsNeeded;
	private JLabel pointsAssigned;
	private JLabel gTreasures;
	private JLabel uSpells;
	private JLabel fame;
	private JLabel notoriety;
	private JLabel gold;
	
	JSpinner greatTreasurePoints;
	JSpinner uSpellPoints;
	JSpinner famePoints;
	JSpinner notorietyPoints;
	JSpinner goldPoints;
	
	JButton confirm;
	
	Hero lCharacter;
	ViewModel lModel;
	
	int pAssigned;
	
	public VictoryPoints(Hero aCharacter, ViewModel aModel){
		super("Victory Conditions for " + aCharacter.getName());
		lCharacter = aCharacter;
		
		int xSize = 300;
		int ySize = 400;
		
		lModel = aModel;
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(5, 5, 5 ,5);
		
		pointsNeeded = new JLabel("Required Points: 5");
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 0;
		add(pointsNeeded, c);
		
		pointsAssigned = new JLabel("Points Assigned: 0");
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 1;
		add(pointsAssigned, c);
		
		gTreasures = new JLabel("Great Treasures:");
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 2;
		add(gTreasures, c);
		
		uSpells = new JLabel("Usable Spells:");
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 3;
		add(uSpells, c);
		
		fame = new JLabel("Fame:");
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 4;
		add(fame, c);
		
		notoriety = new JLabel("Notoriety:");
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 5;
		add(notoriety, c);
		
		gold = new JLabel("Gold:");
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 6;
		add(gold, c);
		
		greatTreasurePoints = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 20;
		c.gridx = 1;
		c.gridy = 2;
		add(greatTreasurePoints, c);
		
		uSpellPoints = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 20;
		c.gridx = 1;
		c.gridy = 3;
		add(uSpellPoints, c);
		
		famePoints = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 20;
		c.gridx = 1;
		c.gridy = 4;
		add(famePoints, c);
		
		notorietyPoints = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 20;
		c.gridx = 1;
		c.gridy = 5;
		add(notorietyPoints, c);
		
		goldPoints = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 20;
		c.gridx = 1;
		c.gridy = 6;
		add(goldPoints, c);
		
		confirm = new JButton("Confirm");
		confirm.setEnabled(false);
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 7;
		add(confirm, c);
		
		createListeners();
	}
	
	protected void createListeners(){
		greatTreasurePoints.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				pAssigned = (int)greatTreasurePoints.getValue() + 
						    (int)uSpellPoints.getValue() +
						    (int)famePoints.getValue() +
						    (int)notorietyPoints.getValue() +
						    (int)goldPoints.getValue();
				
				pointsAssigned.setText("Points Assigned: " + pAssigned);
				
				if(pAssigned == 5)
					confirm.setEnabled(true);
				else
					confirm.setEnabled(false);
			}
		});
		
		uSpellPoints.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				pAssigned = (int)greatTreasurePoints.getValue() + 
						    (int)uSpellPoints.getValue() +
						    (int)famePoints.getValue() +
						    (int)notorietyPoints.getValue() +
						    (int)goldPoints.getValue();
				
				pointsAssigned.setText("Points Assigned: " + pAssigned);
				
				if(pAssigned == 5)
					confirm.setEnabled(true);
				else
					confirm.setEnabled(false);
			}
		});
		
		famePoints.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				pAssigned = (int)greatTreasurePoints.getValue() + 
						    (int)uSpellPoints.getValue() +
						    (int)famePoints.getValue() +
						    (int)notorietyPoints.getValue() +
						    (int)goldPoints.getValue();
				
				pointsAssigned.setText("Points Assigned: " + pAssigned);
				
				if(pAssigned == 5)
					confirm.setEnabled(true);
				else
					confirm.setEnabled(false);
			}
		});
		
		notorietyPoints.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				pAssigned = (int)greatTreasurePoints.getValue() + 
						    (int)uSpellPoints.getValue() +
						    (int)famePoints.getValue() +
						    (int)notorietyPoints.getValue() +
						    (int)goldPoints.getValue();
				
				pointsAssigned.setText("Points Assigned: " + pAssigned);
				
				if(pAssigned == 5)
					confirm.setEnabled(true);
				else
					confirm.setEnabled(false);
			}
		});
		
		goldPoints.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				pAssigned = (int)greatTreasurePoints.getValue() + 
						    (int)uSpellPoints.getValue() +
						    (int)famePoints.getValue() +
						    (int)notorietyPoints.getValue() +
						    (int)goldPoints.getValue();
				
				pointsAssigned.setText("Points Assigned: " + pAssigned);
				
				if(pAssigned == 5)
					confirm.setEnabled(true);
				else
					confirm.setEnabled(false);
			}
		});
		
		confirm.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  int[] victoryConditions = new int[]{(int)greatTreasurePoints.getValue(),
					    						  (int)uSpellPoints.getValue(),
					    						  (int)famePoints.getValue(), 
					    						  (int)notorietyPoints.getValue(), 
					    						  (int)goldPoints.getValue()};
			  
			  lCharacter.setVictoryConditions(victoryConditions);
			  lModel.updateGameState();
			  dispose();
		  }
		});
	}
}
