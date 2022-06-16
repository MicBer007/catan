package catan.player.types;

import java.util.ArrayList;
import java.util.List;

import catan.CatanGame;
import catan.player.PlayerBuildAction;
import catan.player.PlayerInventory;
import catan.player.Team;
import map.Intersection;
import map.Road;
import map.RoadType;
import map.SettlementType;

public class Player {
	
	protected String name;
	
	protected PlayerInventory inventory;
	
	protected Team team;
	
	protected List<PlayerBuildAction> buildList = new ArrayList<PlayerBuildAction>();
	
	public Player(Team team, String name) {
		inventory = new PlayerInventory();
		this.team = team;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public PlayerInventory getInventory() {
		return inventory;
	}

	public Team getTeam() {
		return team;
	}

	public List<PlayerBuildAction> getBuildList() {
		return buildList;
	}
	
	public void addItemToBuildList(PlayerBuildAction a) {
		buildList.add(a);
	}
	
	public void buildActionComplete() {
		buildList.remove(0);
	}

	public boolean buildRoad(Road e, int index) {
		for(Road adjacent: e.getAdjacentEdges()) {
			if(adjacent.getRoadType() == RoadType.ROAD && adjacent.getTeam() == team) {
				e.setRoad(RoadType.ROAD, team);
				CatanGame.client.buildRoad(RoadType.ROAD, index);
				buildActionComplete();
				return true;
			}
		}
		return false;
	}

	public boolean buildRoadStart(Road e, int index) {
		if(e == null || e.getRoadType() == RoadType.ROAD) {
			return false;
		}
		for(Intersection adjacent: e.getAdjacentIntersections()) {
			if(adjacent.getSettlementType() == SettlementType.VILLAGE && adjacent.getTeam() == team) {
				for(Road r: adjacent.getAdjacentEdges()) {
					if(r.getTeam() != null) {
						return false;
					}
				}
				e.setRoad(RoadType.ROAD, team);
				CatanGame.client.buildRoad(RoadType.ROAD, index);
				buildActionComplete();
				return true;
			}
		}
		return false;
	}
	
	public boolean buildVillage(Intersection i, int index) {
		if(i == null || i.getSettlementType() != SettlementType.EMPTY) {
			return false;
		}
		for(Intersection adjacent: i.getAdjacentIntersections()) {
			if(adjacent.getTeam() != null) {
				return false;
			}
		}
		for(Road adjacent: i.getAdjacentEdges()) {
			if(adjacent.getTeam() == team) {
				i.setType(SettlementType.VILLAGE, team);
				CatanGame.client.buildSettlement(SettlementType.VILLAGE, index);
				buildActionComplete();
				return true;
			}
		}
		return false;
	}
	
	public boolean buildVillageStart(Intersection i, int index) {
		if(i == null || i.getSettlementType() != SettlementType.EMPTY) {
			return false;
		}
		for(Intersection adjacent: i.getAdjacentIntersections()) {
			if(adjacent.getTeam() != null) {
				return false;
			}
		}
		i.setType(SettlementType.VILLAGE, team);
		CatanGame.client.buildSettlement(SettlementType.VILLAGE, index);
		buildActionComplete();
		return true;
	}
	
	public boolean buildCity(Intersection i, int index) {
		if(i != null && i.getSettlementType() == SettlementType.VILLAGE && i.getTeam() == team) {
			i.setType(SettlementType.CITY, team);
			CatanGame.client.buildSettlement(SettlementType.CITY, index);
			buildActionComplete();
			return true;
		}
		return false;
	}

}
