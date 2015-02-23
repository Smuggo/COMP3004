package view;

import game.GameState;
import game.entity.Hero;
import game.entity.Player;
import game.environment.hex.Clearing;
import game.environment.hex.Hextile;

import java.awt.Dimension;
import java.util.ArrayList;

import config.Config.ActionType;
import config.Config.DelayPrompt;
import config.Config.SearchType;
import config.Config.TurnState;
import network.packet.PlayerPacket;
import model.ViewModel;
import view.externals.MagicRealmWindow;
import view.internals.game.GameBoardView;
import view.internals.menu.ClientLobbyView;
import view.internals.menu.JoinGameView;
import view.internals.menu.LoadGameView;
import view.internals.menu.MenuView;
import view.internals.menu.NewGameView;
import view.internals.menu.ServerMenuView;
import view.internals.game.CharacterView;
import view.internals.game.CharacterList;
import view.internals.game.ChitList;
import view.internals.game.ChitPlacementSelection;
import view.internals.game.PlayerMenu;
import view.internals.game.SearchChoiceSelection;
import view.internals.game.Searching;
import view.internals.game.StartingLocation;
import view.internals.game.VictoryPoints;
import view.internals.game.DieRoller;

public class ViewManager {
	
	private MagicRealmWindow lWindow;
	
	private MenuView lMenu;
	private NewGameView lNewGame;
	private JoinGameView lJoinGame;
	private LoadGameView lLoadGame;
	private ServerMenuView lServerMenu;
	private ClientLobbyView lClientLobby;
	private GameBoardView lGameBoard;
	private CharacterView lCharacterView;
	private CharacterList lCharacterList;
	private VictoryPoints lVictoryPoints;
	private StartingLocation lStartingLocation;
	private PlayerMenu lPlayerMenu;
	private Searching lSearching;
	private ChitPlacementSelection lChitPlacementSelection;
	private ChitList lChitList;	
	private ViewModel lModel;
	private DieRoller lDieRoller;
	private SearchChoiceSelection lSearchChoice;
	
	public ViewManager(ViewModel aModel){
		lModel = aModel;
		lWindow = new MagicRealmWindow();
	}
	
	public Dimension getScreenDimensions(){
		return lWindow.getSize();
	}
	
	
	public void newMenu(){		
		lMenu = new MenuView(lModel);
		lMenu.setVisible(true);
		lWindow.addWindow(lMenu);
	}
	
	public void newGame(){
	
		lNewGame = new NewGameView(lModel);
		lNewGame.setVisible(true);
		lWindow.addWindow(lNewGame);
	}
	
	public void newJoinGame(){
		
		if(lJoinGame != null){
			lJoinGame.dispose();
		}
		lJoinGame = new JoinGameView(lModel);
		lJoinGame.setVisible(true);
		lWindow.addWindow(lJoinGame);
	}
	
	public void newLoadGame(){
		
		lLoadGame = new LoadGameView(lModel);
		lLoadGame.setVisible(true);
		lWindow.addWindow(lLoadGame);
	}
	
	public void newServerMenu(){
		
		lServerMenu = new ServerMenuView(lModel);
		lServerMenu.setVisible(true);
		lWindow.addWindow(lServerMenu);
	}
	
	public void newClientLobby(){
		
		lClientLobby = new ClientLobbyView(lModel);
		lClientLobby.setVisible(true);
		lWindow.addWindow(lClientLobby);
	}
	
	public void notifyMenuNewClient(PlayerPacket aClientPacket){
		if(lServerMenu != null && lServerMenu.isVisible()){
			lServerMenu.newClient(aClientPacket);
		}
	}
	
	public void closeServerMenu(){
		if(lServerMenu != null){
			lServerMenu.dispose();
		}
	}
	
	public void newGameBoard(Dimension aCanvasSize){
		lGameBoard = new GameBoardView(lModel, aCanvasSize);
		lGameBoard.setVisible(true);
		lWindow.addWindow(lGameBoard);
		lGameBoard.center();
	}
	
	public void createCharacterView(){
		lCharacterView = new CharacterView(lModel);
		lCharacterView.setVisible(true);
		lWindow.addWindow(lCharacterView);
	}
	
	public void showCharacterList(){
		lCharacterList = new CharacterList(lModel, lCharacterView);
		lCharacterList.setVisible(true);
		lWindow.addWindow(lCharacterList);
	}
	
	public void setVictoryPoints(Hero aHero){
		lVictoryPoints = new VictoryPoints(aHero, lModel);
		lVictoryPoints.setVisible(true);
		lWindow.addWindow(lVictoryPoints);
	}
	
	public void clearMenu(){
		if(lServerMenu != null){
			lServerMenu.dispose();
		}
		if(lNewGame != null){
			lNewGame.dispose();
		}
		if(lClientLobby != null){
			lClientLobby.dispose();
		}
	}
	
	public void gameStateUpdated(){
		if(lGameBoard != null){
			lGameBoard.redraw();
		}
		GameState lGameState = lModel.getGameState();
		if(lGameState != null){
			if(lGameState.getTurnState().equals(TurnState.EXECUTING) && lGameState.getPlayerUpdating() == lModel.getLocalPlayerNum()){
				if(lGameState.getDelayPrompt().equals(DelayPrompt.HIDING)){
					lPlayerMenu.promptHiding();
				}
				
			}
		}
		
	}
	
	public void updatePlayerTable(ArrayList<Player> aPlayers){
		lCharacterView.updateCharacterTable(aPlayers);
	}
	
	public void showStartingLocations(){
		lStartingLocation = new StartingLocation(lModel);
		lWindow.addWindow(lStartingLocation);
	}
	
	public void showPlayerMenu(){
		lPlayerMenu = new PlayerMenu(lModel);
		lWindow.addWindow(lPlayerMenu);
	}
	
	public void newTurn(){
		if(lPlayerMenu != null){
			lPlayerMenu.newTurn();
		}
	}
	
	public void addToActionTable(String aClearingID){
		lPlayerMenu.addToActionTable(aClearingID);
	}
	
	public void showSearching(){
		lSearching = new Searching(lModel);
		lWindow.addWindow(lSearching);
	}
	
	public void enableOrDisablePlayer(boolean aButtonState){
		lPlayerMenu.enableOrDisableButtons(aButtonState);
	}
	
	public void showChitPlacementSelection(){
		lChitPlacementSelection = new ChitPlacementSelection(lModel);
		lChitPlacementSelection.setVisible(true);
		lWindow.addWindow(lChitPlacementSelection);
	}
	
	public void showChitList(){		
		lChitList = new ChitList(lModel);
		lChitList.setVisible(true);
		lWindow.addWindow(lChitList);
	}
	
	public void addClearingChits(Clearing aClearing){
		lGameBoard.addClearingChits(aClearing);
	}
	
	public void showDieRoller(ActionType aActionType, SearchType aSearchType){
		lDieRoller = new DieRoller(lModel, aActionType, aSearchType);
		lWindow.addWindow(lDieRoller);
	}
	
	public void showSearchChoiceSelection(SearchType aSearchType){
		lSearchChoice = new SearchChoiceSelection(lModel, aSearchType);
		lWindow.addWindow(lSearchChoice);
	}
}
