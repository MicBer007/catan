package map;

import java.util.ArrayList;
import java.util.List;

import catan.player.Team;

public class Intersection {
	
	private int x, y;
	
	private List<Tile> adjacentTiles = new ArrayList<Tile>();
	
	private List<Intersection> adjacentIntersections = new ArrayList<Intersection>();
	
	private List<Road> adjacentEdges = new ArrayList<Road>();
	
	private SettlementType settlementType;
	
	private Team team;
	
	private PortType portType;
	
	public Intersection(int x, int y) {
		this.x = x;
		this.y = y;
		settlementType = SettlementType.EMPTY;
		team = null;
	}
	
	public Intersection(int x, int y, List<Tile> adjacentTiles, List<Intersection> adjacentIntersections, List<Road> adjacentEdges) {
		this.x = x;
		this.y = y;
		settlementType = SettlementType.EMPTY;
		team = null;
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

	public List<Tile> getAdjacentTiles() {
		return adjacentTiles;
	}
	
	public List<Intersection> getAdjacentIntersections() {
		return adjacentIntersections;
	}

	public void setAdjacentTiles(List<Tile> adjacentTiles) {
		this.adjacentTiles = adjacentTiles;
	}

	public void setAdjacentIntersections(List<Intersection> adjacentIntersections) {
		this.adjacentIntersections = adjacentIntersections;
	}

	public List<Road> getAdjacentEdges() {
		return adjacentEdges;
	}

	public void setAdjacentEdges(List<Road> adjacentEdges) {
		this.adjacentEdges = adjacentEdges;
	}

	public void addGameTile(Tile tile) {
		adjacentTiles.add(tile);
	}

	public void addMapIntersection(Intersection intersection) {
		adjacentIntersections.add(intersection);
	}
	
	public void addEdge(Road edge) {
		adjacentEdges.add(edge);
	}

	public PortType getPortType() {
		return portType;
	}

	public void setPortType(PortType portType) {
		this.portType = portType;
	}

}
