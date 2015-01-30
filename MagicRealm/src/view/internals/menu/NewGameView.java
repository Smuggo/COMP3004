package view.internals.menu;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import config.Config;
import model.ViewModel;

public class NewGameView extends JInternalFrame{
	
	private static final long serialVersionUID = -2996765842795567425L;

	JLabel lUsernameLabel;
	JTextField lUsernameField;
	
	JButton lStartGameButton;
	JButton lMainMenuButton;
	
	JButton lOpenServerButton;
	JLabel lPortNumberLabel;
	JTextField lPortNumberField;
	JLabel lHostIpAddress;
	JLabel lHostIpAddressLabel;
	
	JPanel lNetworkPanel;
	
	ViewModel lModel;
	
	
	public NewGameView(ViewModel aModel){
		super("New Game",true,false,false,true);
		
		lModel = aModel;
		
		Dimension lScreenSize = lModel.getScreenDimensions();
		
		int xScreen = (int)lScreenSize.getWidth();
		int yScreen = (int)lScreenSize.getHeight();
		
		int xSize = 800;
		int ySize = 600;
		
		
		setPreferredSize(new Dimension(xSize,ySize));
		setSize(xSize,ySize);
		
		setLocation((xScreen/2)-(xSize/2), (yScreen/2)-(ySize/2));
		
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		Border lowerdetached = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		//Content
		
		lNetworkPanel = new JPanel();
		setupNetworkPanel();
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		TitledBorder title = BorderFactory.createTitledBorder(lowerdetached, "Network");
		title.setTitleJustification(TitledBorder.CENTER);
		lNetworkPanel.setBorder(title);
		add(lNetworkPanel,c);
		
		lUsernameField = new JTextField("Name: ");
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 1;
		add(lUsernameField, c);
		
		lStartGameButton = new JButton("Start Game");
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 2;
		add(lStartGameButton, c);
		
		lMainMenuButton = new JButton("Main Menu");
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 3;
		add(lMainMenuButton, c);
		
		
		createButtonListeners();
	}
	
	
	protected void createButtonListeners(){
		
		lMainMenuButton.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
		    lModel.requestMainMenu();
		    lModel.requestCloseServer();
		    dispose();
		  }
		});
		
		lOpenServerButton.addActionListener(new ActionListener()
		{
			  public void actionPerformed(ActionEvent e)
			  {
			    lModel.requestServerMenu(Integer.parseInt(lPortNumberField.getText()));
			    lOpenServerButton.setEnabled(false);
			    lPortNumberField.setEnabled(false);
			    lOpenServerButton.setText("Server Open");
			  }
			});
		
		
	}
	
	
	protected void setupNetworkPanel(){
		lNetworkPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		lPortNumberLabel = new JLabel("Port Number:");
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		lNetworkPanel.add(lPortNumberLabel,c);
		
		lPortNumberField = new JTextField(Integer.toString(Config.lPort));
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 1;
		c.gridy = 0;
		lNetworkPanel.add(lPortNumberField,c);
		
		lHostIpAddressLabel = new JLabel("Local Ip Address:");
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 1;
		lNetworkPanel.add(lHostIpAddressLabel,c);
		
		
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 1;
		c.gridy = 1;
		try {
			lHostIpAddress = new JLabel(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			lHostIpAddress = new JLabel("Error Retrieving Address");
		}
		lNetworkPanel.add(lHostIpAddress,c);
		
		lOpenServerButton = new JButton("Open Server Connection");
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		lNetworkPanel.add(lOpenServerButton,c);
		
	}

}
