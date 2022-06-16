package map;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import settings.Settings;

public class Tile {
	
	private Polygon polygon;
	
	private int x, y;
	private int xMid, yMid;
	private int tileX, tileY;
	
	private GameTileType tileType;
	
	private int diceNumber;
	
	private List<Intersection> adjacentIntersections = new ArrayList<Intersection>();
	
	public Tile(int tileX, int tileY, GameTileType tileType, int diceNumber) {
		this.tileX = tileX;
		this.tileY = tileY;
		this.tileType = tileType;
		this.diceNumber = diceNumber;
		this.x = tileX * Settings.GAME_TILE_REAL_SIZE_X + Settings.MAP_OFFSET_X;
		this.y = tileY * Settings.GAME_TILE_REAL_SIZE_Y / 2 + Settings.MAP_OFFSET_Y;
		this.xMid = x + Settings.GAME_TILE_SIZE_X / 2;
		this.yMid = y + Settings.GAME_TILE_SIZE_Y / 2;
		generateTilePolygon();
	}
	
	private void generateTilePolygon() {
		int[] xPoints = {0, Settings.GAME_TILE_SIZE_X / 4, Settings.GAME_TILE_SIZE_X / 4 * 3, Settings.GAME_TILE_SIZE_X, Settings.GAME_TILE_SIZE_X / 4 * 3, Settings.GAME_TILE_SIZE_X / 4};
		int[] yPoints = {Settings.GAME_TILE_SIZE_Y / 2, 0, 0, Settings.GAME_TILE_SIZE_Y / 2, Settings.GAME_TILE_SIZE_Y, Settings.GAME_TILE_SIZE_Y};
		polygon = new Polygon(xPoints, yPoints, 6);
		polygon.translate(x, y);
	}

	public Polygon getPolygon() {
		return polygon;
	}

	public int getTileX() {
		return tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public GameTileType getTileType() {
		return tileType;
	}

	public int getDiceNumber() {
		return diceNumber;
	}

	public int getRealX() {
		return x;
	}

	public int getRealY() {
		return y;
	}

	public int getXMid() {
		return xMid;
	}

	public int getYMid() {
		return yMid;
	}

	public List<Intersection> getAdjacentIntersections() {
		return adjacentIntersections;
	}

	public void setAdjacentIntersections(List<Intersection> adjacentIntersections) {
		this.adjacentIntersections = adjacentIntersections;
	}
	
	public void addAdjacentIntersection(Intersection intersection) {
		adjacentIntersections.add(intersection);
	}

}
