package view.internals.menu;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;

import model.ViewModel;

public class ClientLobbyView extends JInternalFrame{
	
	private static final long serialVersionUID = 3742758020392137899L;

	JButton lLeaveGameButton;
	
	ViewModel lModel;
	
	public ClientLobbyView(ViewModel aModel){
		super("Client Lobby",false,false,false,true);
		
		lModel = aModel;
		
		Dimension lScreenSize = lModel.getScreenDimensions();
		
		int xScreen = (int)lScreenSize.getWidth();
		int yScreen = (int)lScreenSize.getHeight();
		
		int xSize = 300;
		int ySize = 400;
		
		
		setPreferredSize(new Dimension(xSize,ySize));
		setSize(xSize,ySize);
		
		setLocation((xScreen/2)-(xSize/2), (yScreen/2)-(ySize/2));
		
		
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Content
		
		lLeaveGameButton = new JButton("Leave Game");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(0,30,0,30);
		add(lLeaveGameButton, c);
		

		
		createButtonListeners();
	}

	protected void createButtonListeners(){
		
		
		lLeaveGameButton.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
		    lModel.requestMainMenu();
		    dispose();
		  }
		});
		
		
	}
}
