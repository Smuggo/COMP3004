package view.internals.game;

import game.entity.Hero;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import config.Config.DelayPrompt;
import model.ViewModel;

//List of characters in the game, add characters to the game
public class ActionDisplay extends JInternalFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6931147843017384397L;
	

	private JButton lExecute;
	private JLabel lTurnLabel;
	private JLabel lActionPanel;
	private ViewModel lModel;
	private JButton lClose;
	
	public ActionDisplay(ViewModel aModel){
		super("Actions",true,false,false,true);
		
		lModel = aModel;

		int xSize = 300;
		int ySize = 200;
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		setLocation(50, 50);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		lTurnLabel = new JLabel("Action: ________");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=0;
		c.weighty=0;
		c.weightx=5;
		add(lTurnLabel, c);
		
		lExecute = new JButton("Execute Action");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=1;
		c.gridy=0;
		c.weighty=0;
		c.weightx=.5;
		add(lExecute, c);
		
		lActionPanel = new JLabel();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=1;
		c.weighty=1;
		c.weightx=1;
		c.gridwidth = 2;
		add(lActionPanel, c);
		
		lClose = new JButton("Close");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=2;
		c.weighty=0;
		c.weightx=1;
		add(lClose, c);
		lClose.setEnabled(false);
		
		
		createButtonListeners();
		setVisible(true);
	}
	protected void createButtonListeners(){
		lExecute.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(lModel.getGameState() != null && lModel.getGameState().getDelayPrompt()!= null){
					if(lModel.getGameState().getDelayPrompt().equals(DelayPrompt.HIDING)){
						lModel.hideConfirmed();
						lExecute.setEnabled(false);
					}
					if(lModel.getGameState().getDelayPrompt().equals(DelayPrompt.SEARCHING)){
						lModel.searchConfirmed();
						lExecute.setEnabled(false);
					}
					if(lModel.getGameState().getDelayPrompt().equals(DelayPrompt.MOVING)){
						lModel.moveConfirmed();
						lExecute.setEnabled(false);
					}
				}
				
			}
		});
		
		lClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				lModel.disposeActionDisplay();
			}
		});
		
	}
	
	
	
	public void newAction(){
		lClose.setEnabled(false);
		lExecute.setEnabled(true);
		lTurnLabel.setText("Action: "+lModel.getGameState().getDelayPrompt());
	}
	
	public void setPreviousText(String text){
		lActionPanel.setText(text);
	}
}
