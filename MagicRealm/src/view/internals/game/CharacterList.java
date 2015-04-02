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
		super("Select a Character",false,false,false,true);
		
		lModel = aModel;
		lCharacterView = aCharacterView;
		characterMap = aModel.getCharacters();
		
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
		
		setPreferredSize(new Dimension(960, 675));
		setSize(960, 675);
		
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

		charSheet = new JLabel(new ImageIcon(aModel.getGameManager().getCharacterImage(characterMap.get(characterList.getSelectedValue()).getCharSheet())));
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
				characterMap.get(characterList.getSelectedValue()).setHiddenRoadways(lModel.getHiddenRoadways());
				Hero lHero = characterMap.get(characterList.getSelectedValue());
				lModel.updatePlayerCharacter(lHero);
				lCharacterView.getChooseCharacter().setEnabled(false);
				lModel.requestVictoryPoints(lHero);
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
				charSheet.setIcon(new ImageIcon(lModel.getGameManager().getCharacterImage(characterMap.get(characterList.getSelectedValue()).getCharSheet())));
				getContentPane().repaint();
			}
		});
	}
}
