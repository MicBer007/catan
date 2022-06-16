package catan.player;

import java.awt.Color;

public enum Team {
	
	RED(Color.RED),
	ORANGE(new Color(245, 130, 0)),
	WHITE(Color.WHITE),
	BLUE(Color.BLUE);
	
	private Color colour;
	
	private Team(Color colour) {
		this.colour = colour;
	}
	
	public Color getColour() {
		return colour;
	}

}
