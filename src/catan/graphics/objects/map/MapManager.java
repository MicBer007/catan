package catan.graphics.objects.map;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import debug.Debug;

public class MapManager {
	
	private List<RigidTileGroup> regions = new ArrayList<RigidTileGroup>();
	
	private List<PopulatedTileGroup> populations = new ArrayList<PopulatedTileGroup>();
	
	public MapManager() {
		//defining map regions
		regions.add(new RigidTileGroup("large_1", new String[] {"inner", "left", "outer", "right"}, new Integer[] {1, 1, 0, 0}, new Integer[] {3, 1, 2, 4}, new Integer[] {-1, 1, 6, 5})); //top-left
		regions.add(new RigidTileGroup("small_1", new String[] {"inner", "outer"}, new Integer[] {1, 0}, new Integer[] {5, 6}, new Integer[] {-1, -1})); //bottom left
		regions.add(new RigidTileGroup("large_2", new String[] {"inner", "left", "outer", "right"}, new Integer[] {2, 1, 2, 3}, new Integer[] {6, 7, 8, 7}, new Integer[] {-1, 5, 4, 3})); //bottom
		regions.add(new RigidTileGroup("small_2", new String[] {"inner", "outer"}, new Integer[] {3, 4}, new Integer[] {5, 6}, new Integer[] {-1, -1})); //bottom right
		regions.add(new RigidTileGroup("large_3", new String[] {"inner", "left", "outer", "right"}, new Integer[] {3, 4, 4, 3}, new Integer[] {3, 4, 2, 1}, new Integer[] {-1, 3, 2, 1})); //top-right
		regions.add(new RigidTileGroup("long_1", new String[] {"inner", "middle", "outer"}, new Integer[] {2, 2, 2}, new Integer[] {4, 2, 0}, new Integer[] {-1, -1, -1})); //top
		
		//defining region populations
		populations.add(new PopulatedTileGroup("a1", new String[] {"inner", "left", "outer", "right"}, new GameTileType[] {GameTileType.BRICK, GameTileType.PASTURE, GameTileType.STONE, GameTileType.WHEAT}, new PortType[] {null, PortType.TWO_FOR_ONE_WHEAT, PortType.THREE_FOR_ONE, PortType.TWO_FOR_ONE_WOOD}, new Integer[] {6, 2, 10, 12}));
		populations.add(new PopulatedTileGroup("a3", new String[] {"inner", "left", "outer", "right"}, new GameTileType[] {GameTileType.WOOD, GameTileType.PASTURE, GameTileType.STONE, GameTileType.BRICK}, new PortType[] {null, PortType.TWO_FOR_ONE_PASTURE, PortType.THREE_FOR_ONE, PortType.TWO_FOR_ONE_STONE}, new Integer[] {3, 5, 8, 10}));
		populations.add(new PopulatedTileGroup("a5", new String[] {"inner", "left", "outer", "right"}, new GameTileType[] {GameTileType.STONE, GameTileType.WOOD, GameTileType.BRICK, GameTileType.WHEAT}, new PortType[] {null, PortType.TWO_FOR_ONE_BRICK, PortType.THREE_FOR_ONE, PortType.THREE_FOR_ONE}, new Integer[] {3, 8, 5, 6}));
		
		populations.add(new PopulatedTileGroup("a2", new String[] {"inner", "outer"}, new GameTileType[] {GameTileType.PASTURE, GameTileType.WOOD}, new PortType[] {null, null}, new Integer[] {4, 9}));
		populations.add(new PopulatedTileGroup("a4", new String[] {"inner", "outer"}, new GameTileType[] {GameTileType.WHEAT, GameTileType.PASTURE}, new PortType[] {null, null}, new Integer[] {4, 11}));
		
		populations.add(new PopulatedTileGroup("a6", new String[] {"inner", "middle", "outer"}, new GameTileType[] {GameTileType.DESERT, GameTileType.WOOD, GameTileType.WHEAT}, new PortType[] {null, null, null}, new Integer[] {7, 11, 9}));

		populations.add(new PopulatedTileGroup("b1", new String[] {"inner", "left", "outer", "right"}, new GameTileType[] {GameTileType.STONE, GameTileType.WHEAT, GameTileType.PASTURE, GameTileType.BRICK}, new PortType[] {null, PortType.TWO_FOR_ONE_WHEAT, PortType.THREE_FOR_ONE, PortType.TWO_FOR_ONE_WOOD}, new Integer[] {10, 12, 6, 2}));
		populations.add(new PopulatedTileGroup("b3", new String[] {"inner", "left", "outer", "right"}, new GameTileType[] {GameTileType.PASTURE, GameTileType.WOOD, GameTileType.BRICK, GameTileType.STONE}, new PortType[] {null, PortType.TWO_FOR_ONE_PASTURE, PortType.THREE_FOR_ONE, PortType.TWO_FOR_ONE_STONE}, new Integer[] {8, 10, 3, 5}));
		populations.add(new PopulatedTileGroup("b5", new String[] {"inner", "left", "outer", "right"}, new GameTileType[] {GameTileType.WOOD, GameTileType.BRICK, GameTileType.WHEAT, GameTileType.STONE}, new PortType[] {null, PortType.TWO_FOR_ONE_BRICK, PortType.THREE_FOR_ONE, PortType.THREE_FOR_ONE}, new Integer[] {5, 8, 3, 6}));
		
		populations.add(new PopulatedTileGroup("b2", new String[] {"inner", "outer"}, new GameTileType[] {GameTileType.WOOD, GameTileType.PASTURE}, new PortType[] {null, null}, new Integer[] {4, 9}));
		populations.add(new PopulatedTileGroup("b4", new String[] {"inner", "outer"}, new GameTileType[] {GameTileType.PASTURE, GameTileType.WHEAT}, new PortType[] {null, null}, new Integer[] {11, 4}));
		
		populations.add(new PopulatedTileGroup("b6", new String[] {"inner", "middle", "outer"}, new GameTileType[] {GameTileType.WHEAT, GameTileType.WOOD, GameTileType.DESERT}, new PortType[] {null, null, null}, new Integer[] {9, 11, 7}));
	}
	
	public GameMap generateMap(List<RigidTileGroup> regions, List<PopulatedTileGroup> populations, int xOffset, int yOffset) {
		List<Port> ports = new ArrayList<Port>();
		List<GameTile> tiles = new ArrayList<GameTile>();
		for(int i = 0; i < regions.size(); i++) {
			RigidTileGroup region = regions.get(i);
			PopulatedTileGroup population = populations.get(i);
			for(int j = 0; j < region.getSize(); j++) {
				GameTile tile = new GameTile(region.getXPositions().get(j) + xOffset, region.getYPositions().get(j) + yOffset, population.getCorrespondingTileType(region.getTileTags().get(j)), population.getCorrespondingDiceNumber(region.getTileTags().get(j)));
				tiles.add(tile);
				if(region.getPortPositions().get(j) != -1) {
					ports.add(getPort(population.getCorrespondingPortType(region.getTileTags().get(j)), region.getPortPositions().get(j), tile.getRealX(), tile.getRealY()));
				}
			}
		}
		
		List<MapIntersection> intersections = new ArrayList<MapIntersection>();
		for(GameTile tile: tiles) {
			Polygon polygon = tile.getPolygon();
			for(int i = 0; i < polygon.npoints; i++) {
				int pointX = polygon.xpoints[i];
				int pointY = polygon.ypoints[i];
				MapIntersection intersection = searchForNearbyIntersection(intersections, pointX, pointY);
				if(intersection == null) {
					intersection = new MapIntersection(pointX, pointY);
					intersection.addGameTile(tile);
					intersections.add(intersection);
				} else {
					intersection.addGameTile(tile);
				}
			}
		}
		for(MapIntersection intersection: intersections) {
			List<MapIntersection> adjacentIntersections = searchForNearbyIntersectionsWithinDistance(intersections, intersection.getX(), intersection.getY(), 70);
			for(int i = 0; i < adjacentIntersections.size(); i++) {
				if(adjacentIntersections.get(i).getX() == intersection.getX()) {
					adjacentIntersections.remove(i);
					break;
				}
			}
			intersection.setAdjacentIntersections(adjacentIntersections);
		}
		
		List<MapEdge> edges = new ArrayList<MapEdge>();
		for(MapIntersection intersection: intersections) {
			for(MapIntersection adjacentIntersection: intersection.getAdjacentIntersections()) {
				boolean alreadyExists = false;
				for(MapEdge edge: edges) {
					if(edge.getX1() == adjacentIntersection.getX() && edge.getY1() == adjacentIntersection.getY()) {
						alreadyExists = true;
					}
				}
				if(!alreadyExists) {
					List<MapIntersection> adjacentIntersections = Arrays.asList(new MapIntersection[] {intersection, adjacentIntersection});
					edges.add(new MapEdge(intersection.getX(), intersection.getY(), adjacentIntersection.getX(), adjacentIntersection.getY(), adjacentIntersections));
				}
			}
		}
		for(MapIntersection intersection: intersections) {
			intersection.setAdjacentEdges(searchForNearbyEdgesWithinDistance(edges, intersection.getX(), intersection.getY(), 40));
		}
		for(MapEdge edge: edges) {
			List<MapEdge> adjacentEdges = new ArrayList<MapEdge>();
			for(MapIntersection intersection: edge.getAdjacentIntersections()) {
				for(MapEdge adjacentEdge: intersection.getAdjacentEdges()) {
					if(adjacentEdge.getXMid() != edge.getXMid() || adjacentEdge.getYMid() != edge.getYMid()) {
						adjacentEdges.add(adjacentEdge);
					}
				}
			}
			edge.setAdjacentEdges(adjacentEdges);
		}
		for(MapIntersection intersection: intersections) {
			for(GameTile tile: intersection.getAdjacentTiles()) {
				tile.addAdjacentIntersection(intersection);
			}
		}
		for(Port port: ports) {
			List<MapIntersection> nearbyIntersections = searchForNearbyIntersectionsWithinDistance(intersections, port.getX(), port.getY(), 40);
			for(MapIntersection intersection: nearbyIntersections) {
				intersection.setPortType(port.getType());
			}
		}
		return new GameMap(tiles, intersections, edges, ports, "newMap");
	}
	
	private Port getPort(PortType type, int portLocation, int tileX, int tileY) {
		int portX;
		int portY;
		switch(portLocation) {
			case 1:
				portX = Debug.GAME_TILE_SIZE_X / 2;
				portY = -Debug.GAME_TILE_SIZE_Y / 5 + 2;
				break;
			case 2:
				portX = Debug.GAME_TILE_SIZE_X;
				portY = Debug.GAME_TILE_SIZE_Y / 6 - 4;
				break;
			case 3:
				portX = Debug.GAME_TILE_SIZE_X + 2;
				portY = Debug.GAME_TILE_SIZE_Y / 6 * 5 + 3;
				break;
			case 4:
				portX = Debug.GAME_TILE_SIZE_X / 2;
				portY = Debug.GAME_TILE_SIZE_Y / 5 * 6 - 2;
				break;
			case 5:
				portX = -Debug.GAME_TILE_SIZE_X / 6 + 14;
				portY = Debug.GAME_TILE_SIZE_Y / 6 * 5 + 4;
				break;
			case 6:
				portX = -Debug.GAME_TILE_SIZE_X / 6 + 14;
				portY = Debug.GAME_TILE_SIZE_Y / 6 - 2;
				break;
			default:
				return null;
		}
		return new Port(type, portX + tileX, portY + tileY);
	}
	
	public MapIntersection searchForNearbyIntersection(List<MapIntersection> intersections, int xPoint, int yPoint) {
		for(MapIntersection intersection: intersections) {
			if(Math.abs(intersection.getX() - xPoint) < 8 && Math.abs(intersection.getY() - yPoint) < 8){
				return intersection;
			}
		}
		return null;
	}
	
	public List<MapIntersection> searchForNearbyIntersectionsWithinDistance(List<MapIntersection> intersections, int xPoint, int yPoint, int dist) {
		List<MapIntersection> intersectionsToReturn = new ArrayList<MapIntersection>();
		for(MapIntersection intersection: intersections) {
			if(Math.abs(intersection.getX() - xPoint) < dist && Math.abs(intersection.getY() - yPoint) < dist){
				intersectionsToReturn.add(intersection);
			}
		}
		return intersectionsToReturn;
	}
	
	public List<MapEdge> searchForNearbyEdgesWithinDistance(List<MapEdge> edges, int xPoint, int yPoint, int dist) {
		List<MapEdge> edgesToReturn = new ArrayList<MapEdge>();
		for(MapEdge edge: edges) {
			if(Math.abs(edge.getXMid() - xPoint) < dist && Math.abs(edge.getYMid() - yPoint) < dist){
				if(Math.abs(edge.getXMid() - xPoint) > 0) {
					edgesToReturn.add(edge);
				}
			}
		}
		return edgesToReturn;
	}

	public List<RigidTileGroup> getRegions() {
		return regions;
	}

	public List<PopulatedTileGroup> getPopulations() {
		return populations;
	}
	
	public PopulatedTileGroup getPopulation(String populationID) {
		for(PopulatedTileGroup population: populations) {
			if(population.getName() == populationID) {
				return population;
			}
		}
		return null;
	}

}
