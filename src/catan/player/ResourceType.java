package catan.player;

import java.awt.Color;

import settings.Settings;

public enum ResourceType {
	
	BRICK, WOOD, PASTURE, WHEAT, STONE;
	
	private Color[] colours = new Color[] {Settings.BRICK_COLOUR, Settings.WOOD_COLOUR, Settings.PASTURE_COLOUR, Settings.WHEAT_COLOUR, Settings.STONE_COLOUR};

	public Color getColour() {
		return colours[ordinal()];
	}
	
	public static ResourceType getResourceTypeBasedOnName(String name) {
		for(ResourceType type: values()) {
			if(type.name().equalsIgnoreCase(name)) {
				return type;
			}
		}
		return null;
	}
	
}
