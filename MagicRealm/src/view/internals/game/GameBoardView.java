package view.internals.game;

import game.environment.hex.Clearing;
import game.entity.Player;
import game.entity.Monster;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.ViewModel;

public class GameBoardView extends JInternalFrame{

	private static final long serialVersionUID = -4478656155793467601L;

	ViewModel lModel;
	
	ScrollPane lScrollPane;
	
	GameBoardCanvas lCanvas;
	
	JScrollPane lChits;
	
	public GameBoardView(ViewModel aModel, Dimension aCanvasSize){
		super("Game Board",true,false,false,true);
		
		this.setDoubleBuffered(true);
		
		lModel = aModel;
		
		Dimension lScreenSize = lModel.getScreenDimensions();
		
		int xScreen = (int)lScreenSize.getWidth();
		int yScreen = (int)lScreenSize.getHeight();
		
		int xSize = (xScreen/2)-10;
		int ySize = yScreen-40;
		
		
		setPreferredSize(new Dimension(xSize,ySize));
		setSize(xSize,ySize);
		
		setLocation((xScreen/2), 0);
		
		
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,0,0,0);
		
		lScrollPane = new ScrollPane();
		lScrollPane.getVAdjustable().setUnitIncrement(16);
		
		fillScrollPane(aCanvasSize);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		add(lScrollPane, c);
		
		lChits = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_NEVER, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		lChits.setPreferredSize(new Dimension(xSize, 25));
		lChits.setSize(xSize, 25);
		lChits.setVisible(true);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 1;
		add(lChits, c);
		
	}

	public void fillScrollPane(Dimension aCanvasSize){
		lCanvas = new GameBoardCanvas(lModel);
		lCanvas.setPreferredSize(aCanvasSize);
		lCanvas.setSize(aCanvasSize);
		lScrollPane.add(lCanvas);
	}
	
	public void center(){
		lScrollPane.setScrollPosition(1000, 1000);
	}
	
	public void redraw(){
		if(lCanvas != null)
			lCanvas.repaint();
	}
	
	//Adds all of the characters and monsters to the clearing display
	public void addClearingChits(Clearing aClearing){
		ArrayList<String> lCharacterNames = new ArrayList<String>();
		JPanel lPanel = new JPanel();
		lPanel.setSize((int)lModel.getScreenDimensions().getWidth()/2, 25);
		
		if(lModel.getGameState() != null){
			//Adds Heroes
			for(Player aPlayer: lModel.getGameState().getPlayers()){
				if(aPlayer.getChosenHero() != null){
					if(aPlayer.getChosenHero().getClearing() != null){
						if(aPlayer.getChosenHero().getClearing().getIdentifier().equals(aClearing.getIdentifier())){
							lCharacterNames.add(aPlayer.getChosenHero().getName());
						}
					}
				}
			}
			
			//Adds Monsters
			for(Monster aMonster: lModel.getGameState().getHexGrid().getMonsters()){
				if(aMonster.getClearing() != null){
					if(aMonster.getClearing().getIdentifier().equals(aClearing.getIdentifier())){
						lCharacterNames.add(aMonster.getName());
					}
				}
			}
		
			for(int i = 0; i < lCharacterNames.size(); i++){
				/*A JScrollFrame only accepts one JPanel, so make a JPanel that has a bunch
				  of JPanels inside of it then add that to the JScrollFrame.*/
				JPanel imagePanel = new JPanel();
				imagePanel.add(new JLabel(lCharacterNames.get(i)));
				
				lPanel.add(imagePanel);
				lChits.add(lPanel);
				lChits.setViewportView(lPanel);
			}
		}
		lChits.setSize((int)lModel.getScreenDimensions().getWidth()/2, 25);
		lChits.validate();
		lChits.repaint();
	}
}
