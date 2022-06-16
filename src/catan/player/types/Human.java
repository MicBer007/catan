package catan.player.types;

import java.awt.event.MouseEvent;

import catan.player.PlayerBuildAction;
import catan.player.Team;
import map.Intersection;
import map.Map;
import map.Road;
import map.Tile;

public class Human extends Player {

	public Human(Team team, String name) {
		super(team, name);
	}
	
	public void mousePressed(MouseEvent e, Map m) {
		if(buildList.size() == 0) {
			return;
		}
		if(buildList.get(0) == PlayerBuildAction.BUILD_ROAD) {
			Road r = m.getRoadOnPosition(e.getX(),e.getY());
			if(r == null) {
				return;
			}
			buildRoad(r, m.getRoads().indexOf(r));
		} else if(buildList.get(0) == PlayerBuildAction.BUILD_ROAD_START) {
			Road r = m.getRoadOnPosition(e.getX(),e.getY());
			if(r == null) {
				return;
			}
			buildRoadStart(r, m.getRoads().indexOf(r));
		} else if(buildList.get(0) == PlayerBuildAction.BUILD_VILLAGE) {
			Intersection i = m.getIntersectionOnPosition(e.getX(), e.getY());
			if(i == null) {
				return;
			}
			buildVillage(i, m.getIntersections().indexOf(i));
		} else if(buildList.get(0) == PlayerBuildAction.BUILD_VILLAGE_START) {
			Intersection i = m.getIntersectionOnPosition(e.getX(), e.getY());
			if(i == null) {
				return;
			}
			buildVillageStart(i, m.getIntersections().indexOf(i));
		} else if(buildList.get(0) == PlayerBuildAction.BUILD_VILLAGE_START_WITH_RESOURCES) {
			Intersection i = m.getIntersectionOnPosition(e.getX(), e.getY());
			if(i == null) {
				return;
			}
			buildVillageStart(i, m.getIntersections().indexOf(i));
			for(Tile t: i.getAdjacentTiles()) {
				inventory.addResource(t.getTileType().getCorrespondingResource());
			}
		} else if(buildList.get(0) == PlayerBuildAction.BUILD_CITY) {
			Intersection i = m.getIntersectionOnPosition(e.getX(), e.getY());
			if(i == null) {
				return;
			}
			buildCity(i, m.getIntersections().indexOf(i));
		}
	}

}
