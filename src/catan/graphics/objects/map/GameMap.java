package catan.graphics.objects.map;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
	
	private List<GameTile> tiles = new ArrayList<GameTile>();
	
	private List<MapIntersection> intersections = new ArrayList<MapIntersection>();
	
	private List<MapEdge> edges = new ArrayList<MapEdge>();
	
	private List<Port> ports = new ArrayList<Port>();
	
	private String name;
	
	public GameMap(List<GameTile> tiles, List<MapIntersection> intersections, List<MapEdge> edges, List<Port> ports, String name) {
		this.tiles = tiles;
		this.intersections = intersections;
		this.edges = edges;
		this.ports = ports;
		this.name = name;
	}

	public List<GameTile> getTiles() {
		return tiles;
	}

	public List<MapIntersection> getIntersections() {
		return intersections;
	}

	public List<MapEdge> getEdges() {
		return edges;
	}

	public List<Port> getPorts() {
		return ports;
	}

	public String getName() {
		return name;
	}

}
