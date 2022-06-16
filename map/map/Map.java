package map;

import java.util.ArrayList;
import java.util.List;

public class Map {
	
	private List<Tile> tiles = new ArrayList<Tile>();
	
	private List<Intersection> intersections = new ArrayList<Intersection>();
	
	private List<Road> roads = new ArrayList<Road>();
	
	private List<Port> ports = new ArrayList<Port>();
	
	private String name;
	
	public Map(List<Tile> tiles, List<Intersection> intersections, List<Road> roads, List<Port> ports, String name) {
		this.tiles = tiles;
		this.intersections = intersections;
		this.roads = roads;
		this.ports = ports;
		this.name = name;
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public List<Intersection> getIntersections() {
		return intersections;
	}

	public List<Road> getRoads() {
		return roads;
	}

	public List<Port> getPorts() {
		return ports;
	}

	public String getName() {
		return name;
	}
	
	public Intersection getIntersectionOnPosition(int x, int y) {
		for(Intersection intersection: intersections) {
			if(Math.abs(x - intersection.getX()) < 40 && Math.abs(y - intersection.getY()) < 20) {
				return intersection;
			}
		}
		return null;
	}
	
	public Road getRoadOnPosition(int x, int y) {
		for(Road road: roads) {
			if(Math.abs(x - road.getXMid()) < 25 && Math.abs(y - road.getYMid()) < 25) {
				return road;
			}
		}
		return null;
	}
	
	public Tile getTileOnPosition(int x, int y) {
		for(Tile tile: tiles) {
			if(Math.abs(x - tile.getXMid()) < 40 && Math.abs(y - tile.getYMid()) < 40) {
				return tile;
			}
		}
		return null;
	}
	
	public List<Intersection> getIntersectionsForRoll(int number) {
		List<Intersection> intersections = new ArrayList<Intersection>();
		for(Intersection intersection: intersections) {
			for(Tile tile: intersection.getAdjacentTiles()) {
				if(tile.getDiceNumber() == number) {
					intersections.add(intersection);
					break;
				}
			}
		}
		return intersections;
	}

}
