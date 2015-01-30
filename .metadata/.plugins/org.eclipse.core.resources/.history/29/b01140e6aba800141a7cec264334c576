package view.internals;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;

import model.ViewModel;

public class NewGameView extends JInternalFrame{
	
	private static final long serialVersionUID = -2996765842795567425L;

	JTextField lUsernameInput;
	
	JButton lStartGameButton;
	JButton lMainMenuButton;
	
	ViewModel lModel;
	
	
	public NewGameView(ViewModel aModel){
		super("New Game",true,false,false,true);
		
		lModel = aModel;
		
		setPreferredSize(new Dimension(800,600));
		setSize(800,600);
		
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Content
		
		lUsernameInput = new JTextField("Name: ");
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		add(lUsernameInput, c);
		
		lStartGameButton = new JButton("Start Game");
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 1;
		add(lStartGameButton, c);
		
		lMainMenuButton = new JButton("Main Menu");
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 2;
		add(lMainMenuButton, c);
		
		
		createButtonListeners();
	}
	
	
	protected void createButtonListeners(){
		
		lMainMenuButton.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
		    lModel.requestMainMenu();
		    dispose();
		  }
		});
		
		
	}

}
