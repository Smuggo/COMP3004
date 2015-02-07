package view.internals.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import model.ViewModel;

//List of characters in the game, add characters to the game
public class CharacterView extends JInternalFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6931147843017384397L;
	
	private JScrollPane characterPane;
	private JButton chooseCharacter;
	private JButton removeCharacter;
	private JTable  characterList;
	
	private ViewModel lModel;
	
	public CharacterView(ViewModel aModel){
		super("Character List");
		
		lModel = aModel;

		int xSize = 500;
		int ySize = 200;
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		setLocation(0, 0);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		chooseCharacter = new JButton("CHOOSE");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		add(chooseCharacter, c);
		
		removeCharacter = new JButton("REMOVE");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 1;
		add(removeCharacter, c);
		
		characterList = new JTable(new DefaultTableModel(new Object[]{"Character", "Player Name", "Symbol"}, 0))
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
			public Class getColumnClass(int column)
			{
				return getValueAt(0, column).getClass();
			}
		};
		characterList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		characterList.getColumnModel().getColumn(0).setPreferredWidth(200);
		characterList.getColumnModel().getColumn(1).setPreferredWidth(100);
		
		characterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		characterPane = new JScrollPane(characterList,
										JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
										JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 400;
		c.ipady = 90;
		c.weightx = 0;
		c.gridheight = 4;
		c.gridx = 1;
		c.gridy = 0;
		add(characterPane, c);
		
		createButtonListeners();
	}
	protected void createButtonListeners(){
		
		chooseCharacter.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  lModel.requestCharacterSelection();
		  }
		});
		
		removeCharacter.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  if(characterList.getSelectedRow() == -1){
				  
			  }
			  else{
				lModel.requestCharacters().get(characterList.getValueAt(characterList.getSelectedRow(), 0)).setAvailalbe(true);
			  	DefaultTableModel model = (DefaultTableModel) characterList.getModel();
			  	model.removeRow(characterList.getSelectedRow());
			  	characterList.setModel(model);
			  }
		  }
		});
	}
	
	public void setCharacterTableData(String characterName){
		ImageIcon symbol = new ImageIcon(lModel.requestCharacters().get(characterName).getCharChit());
		DefaultTableModel model = (DefaultTableModel) characterList.getModel();
		model.addRow(new Object[] {characterName, "<PLACEHOLDER>", symbol});

		characterList.setRowHeight(symbol.getIconHeight());
		characterList.setModel(model);
	}
}
