package view.internals.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import config.Config.ActionState;
import model.ViewModel;

public class PlayerMenu extends JInternalFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2059466724284016945L;
	
	private ViewModel lModel;
	
	private JButton lMove;
	private JButton lHide;
	private JButton lRest;
	private JButton lSearch;
	private JButton lSendActionsOrCancel;

	
	private JScrollPane lActionPane;
	
	private JTable lActionTable;
	
	public PlayerMenu(ViewModel aModel){
		lModel = aModel;
		
		int xSize = lModel.getScreenDimensions().width/2;
		int ySize = lModel.getScreenDimensions().height/2;
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		setLocation(0, ySize);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		lMove = new JButton("Move");
		c.gridx = 0;
		c.gridy = 0;
		add(lMove, c);
		
		lHide = new JButton("Hide");
		c.gridx = 1;
		c.gridy = 0;
		add(lHide, c);
		
		lRest = new JButton("Rest");
		c.gridx = 2;
		c.gridy = 0;
		add(lRest, c);
		
		lSearch = new JButton("Search");
		c.gridx = 3;
		c.gridy = 0;
		add(lSearch, c);
		
		lSendActionsOrCancel = new JButton("Send Actions");
		c.gridx = 4;
		c.gridy = 0;
		add(lSendActionsOrCancel, c);
		
		
		lActionTable = new JTable(new DefaultTableModel(new Object[]{"Turn", "Day", "Actions", "Die"}, 0))
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = -4125272036513549574L;
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int column)
			{
				return getValueAt(0, column).getClass();
			}
		};
		
		lActionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		lActionTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		lActionTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		lActionTable.getColumnModel().getColumn(2).setPreferredWidth(400);
		lActionTable.getColumnModel().getColumn(3).setPreferredWidth(50);
		lActionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		lActionPane = new JScrollPane(lActionTable,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.gridheight = 0;
		c.gridwidth = 6;
		c.ipadx = xSize - 100;
		c.ipady = ySize - 100;
		c.gridx = 0;
		c.gridy = 1;
		add(lActionPane, c);
		
		createButtonListeners();
		setVisible(true);
	}
	
	protected void createButtonListeners(){
		lMove.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				lModel.setLocalActionState(ActionState.MOVING);
				lSendActionsOrCancel.setText("Cancel");
				lMove.setEnabled(false);
				lHide.setEnabled(false);
				lRest.setEnabled(false);
				lSearch.setEnabled(false);
			}
		});
		
		
		lSendActionsOrCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(lSendActionsOrCancel.getText().equals("Cancel")){
					lSendActionsOrCancel.setText("Send Actions");
					lModel.setLocalActionState(ActionState.NOTHING);
					lMove.setEnabled(true);
					lHide.setEnabled(true);
					lRest.setEnabled(true);
					lSearch.setEnabled(true);
				}
			}
		});
	}
}
