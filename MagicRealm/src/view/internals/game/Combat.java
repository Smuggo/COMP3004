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

import model.ViewModel;

public class Combat extends JInternalFrame{
	private ViewModel lModel;
	
	private JLabel lCombatCommand;
	
	private JButton lChargeAndThrust;
	private JButton lDodgeAndSwing;
	private JButton lDuckAndSmash;
	
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
		
		lChargeAndThrust = new JButton("Charge and Thrust");
		c.gridx = 0;
		c.gridy = 2;
		add(lChargeAndThrust, c);
		
		lDodgeAndSwing = new JButton("Dodge and Swing");
		c.gridx = 0;
		c.gridy = 3;
		add(lDodgeAndSwing, c);
		
		lDuckAndSmash = new JButton("Duck and Smash");
		c.gridx = 0;
		c.gridy = 4;
		add(lDuckAndSmash, c);
		
		createButtonListeners();
		setVisible(true);
	}
	protected void createButtonListeners(){
		lChargeAndThrust.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
	}
}
