package catan.graphics.objects.map;

import java.awt.Color;

import catan.player.ResourceType;

public enum PortType {
	
	THREE_FOR_ONE, TWO_FOR_ONE_BRICK, TWO_FOR_ONE_WOOD, TWO_FOR_ONE_PASTURE, TWO_FOR_ONE_WHEAT, TWO_FOR_ONE_STONE;
	
	private ResourceType[] types = new ResourceType[] {null, ResourceType.BRICK, ResourceType.WOOD, ResourceType.PASTURE, ResourceType.WHEAT, ResourceType.STONE};
	
	private int[] tradeInfluences = new int[] {3, 2, 2, 2, 2, 2};
	
	private Color[] colours = new Color[] {Color.WHITE, new Color(210, 77, 44), new Color(26, 96, 32), new Color(76, 230, 65), new Color(245, 219, 24), new Color(113, 113, 116)};
	
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
