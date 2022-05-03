package settings;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import catan.player.ResourceType;

public class Settings {
	//Debug Information and General code settings
	
	//screen settings
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	
	public static final Color BACKGROUND_COLOR = Color.BLACK;
	
	
	//tile sizes
	public static final int GAME_TILE_SIZE_Y = 100;
	public static final int GAME_TILE_SIZE_X = 112;
	public static final int GAME_TILE_REAL_SIZE_X = 86;
	public static final int GAME_TILE_REAL_SIZE_Y = GAME_TILE_SIZE_Y + 2;
	
	
	//resource colours
	public static final Color DESERT_COLOUR = new Color(255, 228, 132);
	public static final Color BRICK_COLOUR = new Color(210, 77, 44);
	public static final Color WOOD_COLOUR = new Color(26, 96, 32);
	public static final Color PASTURE_COLOUR = new Color(76, 230, 65);
	public static final Color WHEAT_COLOUR = new Color(245, 219, 24);
	public static final Color STONE_COLOUR = new Color(113, 113, 116);
	
	public static final Color DESERT_TEXT_COLOUR = null;//never displayed
	public static final Color BRICK_TEXT_COLOUR = Color.BLACK;
	public static final Color WOOD_TEXT_COLOUR = Color.WHITE;
	public static final Color PASTURE_TEXT_COLOUR = Color.BLACK;
	public static final Color WHEAT_TEXT_COLOUR = Color.BLACK;
	public static final Color STONE_TEXT_COLOUR = Color.WHITE;
	
	
	//port render colours
	public static final Color PORT_OUTLINE = Color.WHITE;
	public static final Color PORT_TEXT_COLOUR = Color.BLACK;
	
	
	//map generation
	public static final String GENERATOR_SETTINGS = "a1-a2-a3-a4-a5-a6";
	
	public static final int MAP_OFFSET_X = 90;
	public static final int MAP_OFFSET_Y = 20;
	
	
	//robber
	public static final Color ROBBER_COLOUR = new Color(76, 76, 76);
	
	
	//UI
	public static final int UI_PADDING = 20;
	
	
	//things the player can buy
	public static Map<ResourceType, Integer> getRoadCost(){
		Map<ResourceType, Integer> cost = new HashMap<ResourceType, Integer>();
		cost.put(ResourceType.BRICK, 1);
		cost.put(ResourceType.WOOD, 1);
		return cost;
	}
	public static Map<ResourceType, Integer> getVillageCost(){
		Map<ResourceType, Integer> cost = new HashMap<ResourceType, Integer>();
		cost.put(ResourceType.BRICK, 1);
		cost.put(ResourceType.WOOD, 1);
		cost.put(ResourceType.PASTURE, 1);
		cost.put(ResourceType.WHEAT, 1);
		return cost;
	}
	public static Map<ResourceType, Integer> getCityCost(){
		Map<ResourceType, Integer> cost = new HashMap<ResourceType, Integer>();
		cost.put(ResourceType.WHEAT, 2);
		cost.put(ResourceType.STONE, 3);
		return cost;
	}
	public static Map<ResourceType, Integer> getDevelopementCardCost(){
		Map<ResourceType, Integer> cost = new HashMap<ResourceType, Integer>();
		cost.put(ResourceType.PASTURE, 1);
		cost.put(ResourceType.WHEAT, 1);
		cost.put(ResourceType.STONE, 1);
		return cost;
	}
	
}
