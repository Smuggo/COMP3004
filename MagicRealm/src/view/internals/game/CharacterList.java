package view.internals.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import game.entity.Hero;
import game.entity.Player;
import model.ViewModel;

public class CharacterList extends JInternalFrame{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -8608031632449364941L;

	private JList<String> characterList;
	private String[] allCharacters = {"Captain", "Swordsman", "Amazon", "Dwarf", "Elf", "Black Knight"};
	private ArrayList<String> availableCharacters = new ArrayList<>();
	
	private JLabel charSheet;
	
	private JButton cancel;
	private JButton select;

	private ViewModel lModel;
	
	private Map<String, Hero> characterMap;
	
	private CharacterView lCharacterView;
	
	public CharacterList(ViewModel aModel, CharacterView aCharacterView){
		super("Character Selection");
		
		lModel = aModel;
		lCharacterView = aCharacterView;
		characterMap = aModel.getCharacters();
		
		int xSize = lModel.getScreenDimensions().width;
		int ySize = lModel.getScreenDimensions().height;
		
		boolean available = true;
		
		for (String chName : allCharacters){
			for (Player player : aModel.getGameState().getPlayers()){
				if(player.getChosenHero() != null){
					if(player.getChosenHero().getName().equals(chName))
						available = false;
				}
			}
			if(available)
				availableCharacters.add(chName);
			
			available = true;
		}
		
		setPreferredSize(new Dimension(xSize/2, (int)(ySize/1.5)));
		setSize((int)(xSize/1.5), ySize);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		characterList = new JList<String>(availableCharacters.toArray(new String[0]));
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.VERTICAL;
		c.ipadx = 100;
		c.ipady = 500;
		c.gridx = 0;
		c.gridy = 0;
		add(characterList, c);
		
		cancel = new JButton("Cancel");
		c.anchor = GridBagConstraints.SOUTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.ipadx = 50;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 1;
		add(cancel, c);
		
		select = new JButton("Select");
		c.anchor = GridBagConstraints.SOUTHWEST;
		c.fill = GridBagConstraints.NONE;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = 1;
		c.gridy = 1;
		add(select, c);
		
		characterList.setSelectedIndex(0);

		charSheet = new JLabel(new ImageIcon(aModel.getGameManager().getImage(characterMap.get(characterList.getSelectedValue()).getCharSheet())));
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 1;
		c.gridy = 0;
		add(charSheet, c);
		

		createButtonListeners();
	}
	protected void createButtonListeners(){
		
		select.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				lCharacterView.setCharacterTableData(characterList.getSelectedValue(), lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getUserName());
				lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).setHero(characterMap.get(characterList.getSelectedValue()));
				lModel.updatePlayerCharacter();
				lCharacterView.getChooseCharacter().setEnabled(false);
				lModel.requestVictoryPoints(lModel.getGameState().getPlayer(lModel.getLocalPlayerNum()).getChosenHero());
				dispose();
			}
		});
		
		cancel.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  dispose();
		  }
		});
		
		

		characterList.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				charSheet.setIcon(new ImageIcon(lModel.getGameManager().getImage(characterMap.get(characterList.getSelectedValue()).getCharSheet())));
				getContentPane().repaint();
			}
		});
	}
}
