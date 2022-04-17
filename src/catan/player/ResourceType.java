package catan.player;

import java.awt.Color;

public enum ResourceType {
	
	BRICK, WOOD, PASTURE, WHEAT, STONE;
	
	private Color[] colours = new Color[] {new Color(210, 77, 44), new Color(26, 96, 32), new Color(76, 230, 65), new Color(245, 219, 24), new Color(113, 113, 116)};

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
