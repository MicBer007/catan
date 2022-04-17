package catan.graphics.objects.map;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import catan.player.ResourceType;

public enum GameTileType {
	
	DESERT, BRICK, WOOD, PASTURE, WHEAT, STONE;
	
	private static List<Integer> typeIDs = Arrays.asList(new Integer[] {0, 1, 2, 3, 4, 5});
	
	private static List<String> names = Arrays.asList(new String[] {"desert", "brick", "wood", "pasture", "wheat", "stone"});
	
	private static List<ResourceType> correspondingResources = Arrays.asList(new ResourceType[] {null, ResourceType.BRICK, ResourceType.WOOD, ResourceType.PASTURE, ResourceType.WHEAT, ResourceType.STONE});
	
	public static List<Color> colours = Arrays.asList(new Color[] {new Color(255, 228, 132), new Color(210, 77, 44), new Color(26, 96, 32), new Color(76, 230, 65), new Color(245, 219, 24), new Color(113, 113, 116)});
	
	public int getTypeID() {
		return typeIDs.get(ordinal());
	}
	
	public String getName() {
		return names.get(ordinal());
	}
	
	public static GameTileType getCorrespondingType(int ID) {
		return values()[ID];
	}
	
	public static GameTileType getCorrespondingType(String name) {
		return values()[names.indexOf(name)];
	}
	
	public Color getTileColour() {
		return colours.get(ordinal());
	}
	
	public ResourceType getCorrespondingResource() {
		return correspondingResources.get(ordinal());
	}

}
