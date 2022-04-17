package catan.graphics.objects.map;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import debug.Debug;

public class GameTile {
	
	private Polygon polygon;
	
	private int x, y;
	private int xMid, yMid;
	private int tileX, tileY;
	
	private GameTileType tileType;
	
	private int diceNumber;
	
	private boolean robber = false;
	
	private List<MapIntersection> adjacentIntersections = new ArrayList<MapIntersection>();
	
	public GameTile(int tileX, int tileY, GameTileType tileType, int diceNumber) {
		this.tileX = tileX;
		this.tileY = tileY;
		this.tileType = tileType;
		this.diceNumber = diceNumber;
		if(diceNumber == 7) {
			robber = true;
		}
		this.x = tileX * Debug.GAME_TILE_REAL_SIZE_X;
		this.y = tileY * Debug.GAME_TILE_REAL_SIZE_Y / 2;
		generateTilePolygon();
	}
	
	private void generateTilePolygon() {
		int[] xPoints = {0, Debug.GAME_TILE_SIZE_X / 4, Debug.GAME_TILE_SIZE_X / 4 * 3, Debug.GAME_TILE_SIZE_X, Debug.GAME_TILE_SIZE_X / 4 * 3, Debug.GAME_TILE_SIZE_X / 4};
		int[] yPoints = {Debug.GAME_TILE_SIZE_Y / 2, 0, 0, Debug.GAME_TILE_SIZE_Y / 2, Debug.GAME_TILE_SIZE_Y, Debug.GAME_TILE_SIZE_Y};
		polygon = new Polygon(xPoints, yPoints, 6);
		polygon.translate(x, y);
		this.xMid = x + Debug.GAME_TILE_SIZE_X / 2;
		this.yMid = y + Debug.GAME_TILE_SIZE_Y / 2;
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

	public boolean isRobber() {
		return robber;
	}

	public void setRobber(boolean robber) {
		this.robber = robber;
	}

	public List<MapIntersection> getAdjacentIntersections() {
		return adjacentIntersections;
	}

	public void setAdjacentIntersections(List<MapIntersection> adjacentIntersections) {
		this.adjacentIntersections = adjacentIntersections;
	}
	
	public void addAdjacentIntersection(MapIntersection intersection) {
		adjacentIntersections.add(intersection);
	}

}
