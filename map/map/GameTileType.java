package map;

import java.awt.Color;

import catan.player.ResourceType;
import settings.Settings;

public enum GameTileType {
	
	DESERT, BRICK, WOOD, PASTURE, WHEAT, STONE;
	
	private static int[] typeIDs = new int[] {0, 1, 2, 3, 4, 5};
	
	private static String[] names = new String[] {"desert", "brick", "wood", "pasture", "wheat", "stone"};
	
	private static ResourceType[] correspondingResources = new ResourceType[] {null, ResourceType.BRICK, ResourceType.WOOD, ResourceType.PASTURE, ResourceType.WHEAT, ResourceType.STONE};
	
	public static Color[] colours = new Color[] {Settings.DESERT_COLOUR, Settings.BRICK_COLOUR, Settings.WOOD_COLOUR, Settings.PASTURE_COLOUR, Settings.WHEAT_COLOUR, Settings.STONE_COLOUR};
	
	public static Color[] textColours = new Color[] {Settings.DESERT_TEXT_COLOUR, Settings.BRICK_TEXT_COLOUR, Settings.WOOD_TEXT_COLOUR, Settings.PASTURE_TEXT_COLOUR, Settings.WHEAT_TEXT_COLOUR, Settings.STONE_TEXT_COLOUR};
	
	public int getTypeID() {
		return typeIDs[ordinal()];
	}
	
	public String getName() {
		return names[ordinal()];
	}
	
	public static GameTileType getCorrespondingType(int ID) {
		return values()[ID];
	}
	
	public static GameTileType getCorrespondingType(String name) {
		for(int i = 0; i < GameTileType.values().length; i++) {
			if(names[i] == name) {
				return GameTileType.values()[i];
			}
		}
		return null;
	}
	
	public Color getTileColour() {
		return colours[ordinal()];
	}
	
	public Color getTextColour() {
		return textColours[ordinal()];
	}
	
	public ResourceType getCorrespondingResource() {
		return correspondingResources[ordinal()];
	}

}
