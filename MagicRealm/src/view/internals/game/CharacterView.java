package view.internals.game;

import game.entity.Player;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
		super("Character List",false,false,false,true);
		
		lModel = aModel;

		int xSize = aModel.getScreenDimensions().width/2;
		int ySize = 400;
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
		setLocation(0, 0);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		chooseCharacter = new JButton("Select Character");
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0,0,5,10);
		add(chooseCharacter, c);
		
		removeCharacter = new JButton("Remove Character");
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0,0,5,10);
		add(removeCharacter, c);
		
		characterList = new JTable(new DefaultTableModel(new Object[]{"Character", "Player Name", "Symbol"}, 0))
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 264630044747863505L;
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
		characterList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		characterList.getColumnModel().getColumn(0).setPreferredWidth(200);
		characterList.getColumnModel().getColumn(1).setPreferredWidth(200);
		
		characterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		characterPane = new JScrollPane(characterList,
										JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
										JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = (int)(lModel.getScreenDimensions().width/2.25);
		c.ipady = (int)(lModel.getScreenDimensions().height/4);
		c.weightx = 0;
		c.gridheight = 4;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0,0,0,0);
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
				  if(lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero().getName().equals(
						  characterList.getValueAt(characterList.getSelectedRow(), 0))){
				  	DefaultTableModel model = (DefaultTableModel) characterList.getModel();
				  	model.removeRow(characterList.getSelectedRow());
				  	characterList.setModel(model);
				  	chooseCharacter.setEnabled(true); 
				  }
			  }
		  }
		});
	}
	
	public void setCharacterTableData(String characterName, String playerName){
		
		Image tempSymbol = getScaledImage(lModel.getGameManager().getImage(lModel.getCharacters().get(characterName).getCharChit()), 30, 30);
		ImageIcon symbol = new ImageIcon(tempSymbol);
		
		DefaultTableModel model = (DefaultTableModel) characterList.getModel();
		model.addRow(new Object[] {characterName, playerName, symbol});
		characterList.setRowHeight(symbol.getIconHeight());
		characterList.setModel(model);
	}
	
	private Image getScaledImage(Image srcImg, int w, int h){
		BufferedImage symbol = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = symbol.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return symbol;
	}
	
	public JButton getChooseCharacter(){ return chooseCharacter; }
	
	public void updateCharacterTable(ArrayList<Player> aPlayers){	
		for(Player player: aPlayers){
			if(player.getChosenHero() != null){
				setCharacterTableData(player.getChosenHero().getName(), player.getUserName());
			}
		}
	}
}
