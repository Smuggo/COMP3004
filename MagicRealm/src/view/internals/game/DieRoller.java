package view.internals.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JInternalFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import config.Config.ActionType;
import config.Config.SearchType;
import model.ViewModel;

public class DieRoller extends JInternalFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5986269180485376784L;

	private ViewModel lModel;
	
	private JLabel lRoll;
	private ArrayList<JButton> lDice;
	private ActionType lActionType;
	private SearchType lSearchType;
	
	public DieRoller(ViewModel aModel, ActionType aActionType, SearchType aSearchType){
		super("Die Roller",false,false,false,true);
		
		lModel = aModel;
		lDice = new ArrayList<JButton>();
		lActionType = aActionType;
		lSearchType = aSearchType;
		
		setPreferredSize(new Dimension(400, 400));
		setSize(400, 400);
		
		setLocation(0, 0);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		lRoll = new JLabel("PICK DIE VALUE:");
		c.gridx = 0;
		c.gridy = 0;
		add(lRoll, c);
		
		for(int i = 0; i < 6; i++){
			lDice.add(new JButton(Integer.toString(i+1)));
			c.gridx = 0;
			c.gridy = i+1;
			add(lDice.get(i), c);
		}
		
		createButtonListeners();
		setVisible(true);
	}
	
	protected void createButtonListeners(){
		for(int i = 0; i < lDice.size(); i++){
			int lRoll = i+1;
			lDice.get(i).addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					lModel.enableOrDisablePlayer(true);
					if(lActionType.equals(ActionType.SEARCH))
						lModel.getActionManager().getActionList().addCheatSearchAction(lSearchType, lRoll);
					else if(lActionType.equals(ActionType.HIDE))
						lModel.getActionManager().getActionList().addCheatHideAction(lRoll);
					dispose();
				}
			});
		}
	}
}
