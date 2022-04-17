package catan.player;

import java.awt.Color;

public enum Team {
	
	RED, BLUE, WHITE, ORANGE, NONE;
	
	private Color[] teamColours = new Color[] {Color.RED, Color.BLUE, Color.WHITE, Color.ORANGE, Color.BLACK};
	
	public Color getColour() {
		return teamColours[ordinal()];
	}

}
