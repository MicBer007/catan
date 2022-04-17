package catan.graphics.objects;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import catan.graphics.objects.map.GameMap;
import catan.graphics.objects.map.GameTile;
import catan.graphics.objects.map.MapEdge;
import catan.graphics.objects.map.MapIntersection;
import catan.graphics.objects.map.MapManager;
import catan.graphics.objects.map.PopulatedTileGroup;
import catan.graphics.objects.map.Port;
import catan.graphics.rendering.MasterRenderer;
import debug.Log;
import debug.LogLevel;

public class ObjectManager {
	
	private Log log = new Log(this.getClass().getSimpleName(), LogLevel.ERROR);
	
	private MapManager mapManager;
	
	private GameMap map;
	
	public ObjectManager() {
		mapManager = new MapManager();
		List<PopulatedTileGroup> mapPopulations = new ArrayList<PopulatedTileGroup>();
		mapPopulations.add(mapManager.getPopulation("a1"));
		mapPopulations.add(mapManager.getPopulation("a2"));
		mapPopulations.add(mapManager.getPopulation("a3"));
		mapPopulations.add(mapManager.getPopulation("a4"));
		mapPopulations.add(mapManager.getPopulation("a5"));
		mapPopulations.add(mapManager.getPopulation("a6"));
		map = mapManager.generateMap(mapManager.getRegions(), mapPopulations, 1, 2);
		log.info("The map has " + map.getIntersections().size() + " intersections.", 10);
		log.info("The map has " + map.getEdges().size() + " roads.", 10);
	}
	
	public void render(Graphics g, MasterRenderer renderer) {
		renderer.renderBackground(g);
		
		Font font = g.getFont();
		g.setFont(font.deriveFont(1));
		for(GameTile tile: map.getTiles()) {
			renderer.renderGameTile(g, tile);
		}
		g.setFont(font);
		for(MapEdge edge: map.getEdges()) {
			renderer.renderMapEdge(g, edge);
		}
		for(MapIntersection intersection: map.getIntersections()) {
			renderer.renderMapIntersection(g, intersection);
		}
		for(Port port: map.getPorts()) {
			renderer.renderMapPort(g, port);
		}
	}
	
	public MapIntersection getIntersectionOnPosition(int x, int y) {
		for(MapIntersection intersection: map.getIntersections()) {
			if(Math.abs(x - intersection.getX()) < 40 && Math.abs(y - intersection.getY()) < 20) {
				return intersection;
			}
		}
		return null;
	}
	
	public MapEdge getEdgeOnPosition(int x, int y) {
		for(MapEdge edge: map.getEdges()) {
			if(Math.abs(x - edge.getXMid()) < 25 && Math.abs(y - edge.getYMid()) < 25) {
				return edge;
			}
		}
		return null;
	}
	
	public GameTile getTileOnPosition(int x, int y) {
		for(GameTile tile: map.getTiles()) {
			if(Math.abs(x - tile.getXMid()) < 40 && Math.abs(y - tile.getYMid()) < 40) {
				return tile;
			}
		}
		return null;
	}
	
	public void putRobberOnTile(GameTile tile) {
		for(GameTile gameTile: map.getTiles()) {
			gameTile.setRobber(false);
		}
		tile.setRobber(true);
	}
	
	public List<MapIntersection> getIntersectionsForRoll(int number) {
		List<MapIntersection> intersections = new ArrayList<MapIntersection>();
		for(MapIntersection intersection: map.getIntersections()) {
			for(GameTile tile: intersection.getAdjacentTiles()) {
				if(tile.getDiceNumber() == number) {
					intersections.add(intersection);
					break;
				}
			}
		}
		return intersections;
	}
	
	public GameMap getMap() {
		return map;
	}

}
