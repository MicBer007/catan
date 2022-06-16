package map;

import java.awt.Color;

import catan.player.ResourceType;
import settings.Settings;

public enum PortType {
	
	THREE_FOR_ONE, TWO_FOR_ONE_BRICK, TWO_FOR_ONE_WOOD, TWO_FOR_ONE_PASTURE, TWO_FOR_ONE_WHEAT, TWO_FOR_ONE_STONE;
	
	private ResourceType[] types = new ResourceType[] {null, ResourceType.BRICK, ResourceType.WOOD, ResourceType.PASTURE, ResourceType.WHEAT, ResourceType.STONE};
	
	private int[] tradeInfluences = new int[] {3, 2, 2, 2, 2, 2};
	
	private Color[] colours = new Color[] {Color.WHITE, Settings.BRICK_COLOUR, Settings.WOOD_COLOUR, Settings.PASTURE_COLOUR, Settings.WHEAT_COLOUR, Settings.STONE_COLOUR};
	
	private String[] tradeNames = new String[] {"3:1", "2:1", "2:1", "2:1", "2:1", "2:1"};
	
	public ResourceType getResourceType() {
		return types[ordinal()];
	}
	
	public int getTradingInfluence() {
		return tradeInfluences[ordinal()];
	}
	
	public Color getColour() {
		return colours[ordinal()];
	}
	
	public String getTradeName() {
		return tradeNames[ordinal()];
	}
	
}
