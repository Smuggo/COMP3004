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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import game.entity.Character;
import model.ViewModel;

public class CharacterList extends JFrame{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = -8608031632449364941L;

	private JList<String> characterList;
	private String[] allCharacters = {"Captain", "Swordsman"};
	private ArrayList<String> availableCharacters = new ArrayList<>();
	
	private JLabel charSheet;
	
			
	private JButton cancel;

	private ViewModel lModel;
	
	private Map<String, Character> characterMap;
	
	public CharacterList(ViewModel aModel){
		super("Character Selection");
		
		int xSize = 1000;
		int ySize = 800;
		
		lModel = aModel;
		
		characterMap = lModel.requestCharacters();
		
		for (String chName : allCharacters){
			if(characterMap.get(chName).getAvailable())
				availableCharacters.add(chName);
		}
		
		setPreferredSize(new Dimension(xSize, ySize));
		setSize(xSize, ySize);
		
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
		c.anchor = c.SOUTHWEST;
		c.fill = c.NONE;
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 0;
		c.ipady = 0;
		add(cancel, c);
		
		charSheet = new JLabel();
		c.anchor = c.NORTH;
		c.gridx = 1;
		c.gridy = 0;
		add(charSheet, c);
		
		
		createButtonListeners();
	}
	protected void createButtonListeners(){
		
		cancel.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  dispose();
		  }
		});
		

		characterList.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				charSheet.setIcon(new ImageIcon(characterMap.get(characterList.getSelectedValue()).getCharSheet()));
				getContentPane().repaint();
			}
		});
	}
}
