package catan.graphics.objects.map;

import java.util.ArrayList;
import java.util.List;

import catan.player.Team;

public class MapIntersection {
	
	private int x, y;
	
	private List<GameTile> adjacentTiles = new ArrayList<GameTile>();
	
	private List<MapIntersection> adjacentIntersections = new ArrayList<MapIntersection>();
	
	private List<MapEdge> adjacentEdges = new ArrayList<MapEdge>();
	
	private SettlementType settlementType;
	
	private Team team;
	
	private PortType portType;
	
	public MapIntersection(int x, int y) {
		this.x = x;
		this.y = y;
		settlementType = SettlementType.EMPTY;
		team = Team.NONE;
	}
	
	public MapIntersection(int x, int y, List<GameTile> adjacentTiles, List<MapIntersection> adjacentIntersections, List<MapEdge> adjacentEdges) {
		this.x = x;
		this.y = y;
		settlementType = SettlementType.EMPTY;
		team = Team.NONE;
		this.adjacentTiles = adjacentTiles;
		this.adjacentIntersections = adjacentIntersections;
		this.adjacentEdges = adjacentEdges;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public SettlementType getSettlementType() {
		return settlementType;
	}

	public void setType(SettlementType settlementType, Team team) {
		this.settlementType = settlementType;
		this.team = team;
	}

	public Team getTeam() {
		return team;
	}

	public List<GameTile> getAdjacentTiles() {
		return adjacentTiles;
	}
	
	public List<MapIntersection> getAdjacentIntersections() {
		return adjacentIntersections;
	}

	public void setAdjacentTiles(List<GameTile> adjacentTiles) {
		this.adjacentTiles = adjacentTiles;
	}

	public void setAdjacentIntersections(List<MapIntersection> adjacentIntersections) {
		this.adjacentIntersections = adjacentIntersections;
	}

	public List<MapEdge> getAdjacentEdges() {
		return adjacentEdges;
	}

	public void setAdjacentEdges(List<MapEdge> adjacentEdges) {
		this.adjacentEdges = adjacentEdges;
	}

	public void addGameTile(GameTile tile) {
		adjacentTiles.add(tile);
	}

	public void addMapIntersection(MapIntersection intersection) {
		adjacentIntersections.add(intersection);
	}
	
	public void addEdge(MapEdge edge) {
		adjacentEdges.add(edge);
	}

	public PortType getPortType() {
		return portType;
	}

	public void setPortType(PortType portType) {
		this.portType = portType;
	}

}
