package mapGeneration;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import map.GameTileType;
import map.Intersection;
import map.Map;
import map.Port;
import map.PortType;
import map.Road;
import map.Tile;
import settings.Settings;

public class MapCreator {
	
	private static List<RigidTileGroup> regions;
	
	private static List<PopulatedTileGroup> populations;
	
	private static void defineRegions() {
		regions = new ArrayList<RigidTileGroup>();
		populations = new ArrayList<PopulatedTileGroup>();
		
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
	
	public static Map generateVanillaMap(String generatorSettings) {
		if(regions == null) {
			defineRegions();
		}
		List<PopulatedTileGroup> populations = getPopulationForGeneratorSettings(generatorSettings);
		
		List<Port> ports = new ArrayList<Port>();
		List<Tile> tiles = new ArrayList<Tile>();
		for(int i = 0; i < regions.size(); i++) {
			RigidTileGroup region = regions.get(i);
			PopulatedTileGroup population = populations.get(i);
			for(int j = 0; j < region.getSize(); j++) {
				Tile tile = new Tile(region.getXPositions().get(j), region.getYPositions().get(j), population.getCorrespondingTileType(region.getTileTags().get(j)), population.getCorrespondingDiceNumber(region.getTileTags().get(j)));
				tiles.add(tile);
				if(region.getPortPositions().get(j) != -1) {
					ports.add(getPort(population.getCorrespondingPortType(region.getTileTags().get(j)), region.getPortPositions().get(j), tile.getRealX(), tile.getRealY()));
				}
			}
		}
		
		List<Intersection> intersections = new ArrayList<Intersection>();
		for(Tile tile: tiles) {
			Polygon polygon = tile.getPolygon();
			for(int i = 0; i < polygon.npoints; i++) {
				int pointX = polygon.xpoints[i];
				int pointY = polygon.ypoints[i];
				Intersection intersection = searchForNearbyIntersection(intersections, pointX, pointY);
				if(intersection == null) {
					intersection = new Intersection(pointX, pointY);
					intersection.addGameTile(tile);
					intersections.add(intersection);
				} else {
					intersection.addGameTile(tile);
				}
			}
		}
		for(Intersection intersection: intersections) {
			List<Intersection> adjacentIntersections = searchForNearbyIntersectionsWithinDistance(intersections, intersection.getX(), intersection.getY(), 70);
			for(int i = 0; i < adjacentIntersections.size(); i++) {
				if(adjacentIntersections.get(i).getX() == intersection.getX()) {
					adjacentIntersections.remove(i);
					break;
				}
			}
			intersection.setAdjacentIntersections(adjacentIntersections);
		}
		
		List<Road> edges = new ArrayList<Road>();
		for(Intersection intersection: intersections) {
			for(Intersection adjacentIntersection: intersection.getAdjacentIntersections()) {
				boolean alreadyExists = false;
				for(Road edge: edges) {
					if(edge.getX1() == adjacentIntersection.getX() && edge.getY1() == adjacentIntersection.getY()) {
						alreadyExists = true;
					}
				}
				if(!alreadyExists) {
					List<Intersection> adjacentIntersections = Arrays.asList(new Intersection[] {intersection, adjacentIntersection});
					edges.add(new Road(intersection.getX(), intersection.getY(), adjacentIntersection.getX(), adjacentIntersection.getY(), adjacentIntersections));
				}
			}
		}
		for(Intersection intersection: intersections) {
			intersection.setAdjacentEdges(searchForNearbyEdgesWithinDistance(edges, intersection.getX(), intersection.getY(), 40));
		}
		for(Road edge: edges) {
			List<Road> adjacentEdges = new ArrayList<Road>();
			for(Intersection intersection: edge.getAdjacentIntersections()) {
				for(Road adjacentEdge: intersection.getAdjacentEdges()) {
					if(adjacentEdge.getXMid() != edge.getXMid() || adjacentEdge.getYMid() != edge.getYMid()) {
						adjacentEdges.add(adjacentEdge);
					}
				}
			}
			edge.setAdjacentEdges(adjacentEdges);
		}
		for(Intersection intersection: intersections) {
			for(Tile tile: intersection.getAdjacentTiles()) {
				tile.addAdjacentIntersection(intersection);
			}
		}
		for(Port port: ports) {
			List<Intersection> nearbyIntersections = searchForNearbyIntersectionsWithinDistance(intersections, port.getX(), port.getY(), 40);
			for(Intersection intersection: nearbyIntersections) {
				intersection.setPortType(port.getType());
			}
		}
		return new Map(tiles, intersections, edges, ports, "newMap");
	}
	
	private static Port getPort(PortType type, int portLocation, int tileX, int tileY) {
		int portX;
		int portY;
		switch(portLocation) {
			case 1:
				portX = Settings.GAME_TILE_SIZE_X / 2;
				portY = -Settings.GAME_TILE_SIZE_Y / 5 + 2;
				break;
			case 2:
				portX = Settings.GAME_TILE_SIZE_X;
				portY = Settings.GAME_TILE_SIZE_Y / 6 - 4;
				break;
			case 3:
				portX = Settings.GAME_TILE_SIZE_X + 2;
				portY = Settings.GAME_TILE_SIZE_Y / 6 * 5 + 3;
				break;
			case 4:
				portX = Settings.GAME_TILE_SIZE_X / 2;
				portY = Settings.GAME_TILE_SIZE_Y / 5 * 6 - 2;
				break;
			case 5:
				portX = -Settings.GAME_TILE_SIZE_X / 6 + 14;
				portY = Settings.GAME_TILE_SIZE_Y / 6 * 5 + 4;
				break;
			case 6:
				portX = -Settings.GAME_TILE_SIZE_X / 6 + 14;
				portY = Settings.GAME_TILE_SIZE_Y / 6 - 2;
				break;
			default:
				return null;
		}
		return new Port(type, portX + tileX, portY + tileY);
	}
	
	public static Intersection searchForNearbyIntersection(List<Intersection> intersections, int xPoint, int yPoint) {
		for(Intersection intersection: intersections) {
			if(Math.abs(intersection.getX() - xPoint) < 8 && Math.abs(intersection.getY() - yPoint) < 8){
				return intersection;
			}
		}
		return null;
	}
	
	public static List<Intersection> searchForNearbyIntersectionsWithinDistance(List<Intersection> intersections, int xPoint, int yPoint, int dist) {
		List<Intersection> intersectionsToReturn = new ArrayList<Intersection>();
		for(Intersection intersection: intersections) {
			if(Math.abs(intersection.getX() - xPoint) < dist && Math.abs(intersection.getY() - yPoint) < dist){
				intersectionsToReturn.add(intersection);
			}
		}
		return intersectionsToReturn;
	}
	
	public static List<Road> searchForNearbyEdgesWithinDistance(List<Road> edges, int xPoint, int yPoint, int dist) {
		List<Road> edgesToReturn = new ArrayList<Road>();
		for(Road edge: edges) {
			if(Math.abs(edge.getXMid() - xPoint) < dist && Math.abs(edge.getYMid() - yPoint) < dist){
				if(Math.abs(edge.getXMid() - xPoint) > 0) {
					edgesToReturn.add(edge);
				}
			}
		}
		return edgesToReturn;
	}
	
	public static List<PopulatedTileGroup> getPopulationForGeneratorSettings(String generatorSettings) {
		List<PopulatedTileGroup> populations = new ArrayList<PopulatedTileGroup>();
		String[] populationIDs = generatorSettings.split("-");
		for(int i = 0; i < 6; i++) {
			populations.add(getPopulation(populationIDs[i]));
		}
		return populations;
	}
	
	public static PopulatedTileGroup getPopulation(String populationID) {
		for(PopulatedTileGroup population: populations) {
			if(population.getName().equalsIgnoreCase(populationID)) {
				return population;
			}
		}
		return null;
	}

}
