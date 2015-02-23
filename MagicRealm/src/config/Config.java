package config;

import java.awt.Dimension;
import java.awt.Toolkit;

public final class Config {
	
	public static String lIpAddress = "localhost";
	public static int lPort = 25000;
	public static boolean isDebugging = true;
	public static int lMaxPlayers = 7;
	
	public static boolean drawingHexCoords = false;
	public static boolean drawingClearingBoxes = false;
	public static boolean drawingRoadways = false;
		
	public enum RoadwayType {
		TUNNEL, OPEN_ROAD, HIDDEN_PATH, SECRET_PASSAGE
	}

	public enum ClearingType {
		MOUNTAIN, CAVE, WOODS
	}
	
	public enum HextileType {
		MOUNTAIN, CAVE, VALLEY, WOODS
	}

	public enum IncompleteRoadwayDirection {
		TOP, TOP_RIGHT, BOTTOM_RIGHT, BOTTOM, BOTTOM_LEFT, TOP_LEFT
	}
	
	public enum CharacterImageType {
		captainPage, swordsmanPage, amazonPage, dwarfPage, elfPage, bKnightPage, captainChit, swordsmanChit, amazonChit, dwarfChit, elfChit, bKnightChit
	};
	
	public enum ChitType {
		SOUND, WARNING, SITE, WEATHER, VISTOR_MISSION
	};
	
	public enum DwellingType {
		INN, HOUSE, GUARD, CHAPEL
	}
	
	public enum ActionType {
		MOVE, HIDE, REST, SEARCH
	}
	
	public enum ActionState {
		MOVING, NOTHING
	}
	
	public enum SearchType {
		PEER, LOCATE, LOOT
	}
	
	public enum TurnState{
		SELECTING, EXECUTING
	}
	
	public enum DelayPrompt{
		HIDING
	}
	
	public enum MonsterType{
		GHOST
	}
}
