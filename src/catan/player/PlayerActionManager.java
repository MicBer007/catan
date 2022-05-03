package catan.player;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import catan.graphics.objects.ObjectManager;
import catan.graphics.objects.map.GameTile;
import catan.graphics.objects.map.GameTileType;
import catan.graphics.objects.map.MapEdge;
import catan.graphics.objects.map.MapIntersection;
import catan.graphics.objects.map.SettlementType;

public class PlayerActionManager {

	private int actionTaker;
	private PlayerAction action;
	
	private List<Integer> actionTakers = new ArrayList<Integer>();
	private List<PlayerAction> actions = new ArrayList<PlayerAction>();
	
	private ObjectManager objectManager;
	
	private PlayerManager playerManager;
	
	public PlayerActionManager(PlayerManager playerManager, ObjectManager objectManager, List<Player> players) {
		this.playerManager = playerManager;
		this.objectManager = objectManager;
		action = PlayerAction.NONE;
		startGame();
	}
	
	private void startGame() {
		for(int i = 0; i < playerManager.getPlayers().size(); i++) {
			addItemToQueue(i, PlayerAction.PLACE_TOWN_START);
		}
		for(int i = playerManager.getPlayers().size() - 1; i >= 0; i--) {
			addItemToQueue(i, PlayerAction.PLACE_TOWN_START_WITH_RESOURCES);
		}
		for(int i = 0; i < playerManager.getPlayers().size(); i++) {
			addItemToQueue(i, PlayerAction.PLACE_ROAD_START);
		}
		for(int i = playerManager.getPlayers().size() - 1; i >= 0; i--) {
			addItemToQueue(i, PlayerAction.PLACE_ROAD_START);
		}
	}
	
	public void addItemToQueue(int actionTaker, PlayerAction action) {
		if(this.action == PlayerAction.NONE) {
			this.actionTaker = actionTaker;
			this.action = action;
			playerManager.setActivePlayer(actionTaker);
		} else {
			actionTakers.add(actionTaker);
			actions.add(action);
		}
	}
	
	public void removeAction() {
		if(actions.size() > 0) {
			actionTaker = actionTakers.remove(0);
			action = actions.remove(0);
			playerManager.setActivePlayer(actionTaker);
		} else {
			action = PlayerAction.NONE;
			playerManager.startGame();
		}
	}
	
	public void mouseReleasedEvent(MouseEvent e) {
		if(action == PlayerAction.PLACE_TOWN) {
			MapIntersection intersection = objectManager.getIntersectionOnPosition(e.getX(), e.getY());
			if(!isTownPlacementObstructed(intersection) && canPlayerReachIntersection(playerManager.getPlayers().get(actionTaker), intersection)) {
				intersection.setType(SettlementType.VILLAGE, playerManager.getPlayers().get(actionTaker).getTeam());
				if(intersection.getPortType() != null) {
					playerManager.getPlayers().get(actionTaker).addPort(intersection.getPortType());
				}
				removeAction();
			}
			return;
		}
		
		if(action == PlayerAction.PLACE_TOWN_START) {
			MapIntersection intersection = objectManager.getIntersectionOnPosition(e.getX(), e.getY());
			if(!isTownPlacementObstructed(intersection)) {
				intersection.setType(SettlementType.VILLAGE, playerManager.getPlayers().get(actionTaker).getTeam());
				if(intersection.getPortType() != null) {
					playerManager.getPlayers().get(actionTaker).addPort(intersection.getPortType());
				}
				removeAction();
			}
			return;
		}
		
		if(action == PlayerAction.PLACE_TOWN_START_WITH_RESOURCES) {
			MapIntersection intersection = objectManager.getIntersectionOnPosition(e.getX(), e.getY());
			if(!isTownPlacementObstructed(intersection)) {
				intersection.setType(SettlementType.VILLAGE, playerManager.getPlayers().get(actionTaker).getTeam());
				if(intersection.getPortType() != null) {
					playerManager.getPlayers().get(actionTaker).addPort(intersection.getPortType());
				}
				for(GameTile tile: intersection.getAdjacentTiles()) {
					if(tile.getTileType() != GameTileType.DESERT) {
						playerManager.getPlayerCardManager().addPlayerCard(playerManager.getPlayers().get(actionTaker), tile.getTileType().getCorrespondingResource());
					}
				}
				removeAction();
			}
			return;
		}
		
		if(action == PlayerAction.PLACE_ROAD) {
			MapEdge edge = objectManager.getEdgeOnPosition(e.getX(), e.getY());
			if(edge != null && !edge.isRoad()) {
				for(MapEdge adjacentEdge: edge.getAdjacentEdges()) {
					if(adjacentEdge.isRoad() && adjacentEdge.getTeam() == playerManager.getPlayers().get(actionTaker).getTeam()) {
						edge.setTeam(playerManager.getPlayers().get(actionTaker).getTeam());
						removeAction();
						return;
					}
				}
			}
			return;
		}
		
		if(action == PlayerAction.PLACE_ROAD_START) {
			MapEdge edge = objectManager.getEdgeOnPosition(e.getX(), e.getY());
			if(edge != null && !edge.isRoad()) {
				for(MapIntersection intersection: edge.getAdjacentIntersections()) {
					if(intersection.getSettlementType() == SettlementType.VILLAGE && intersection.getTeam() == playerManager.getPlayers().get(actionTaker).getTeam()) {
						for(MapEdge adjacentEdge: intersection.getAdjacentEdges()) {
							if(adjacentEdge.isRoad()) {
								return;
							}
						}
						edge.setTeam(playerManager.getPlayers().get(actionTaker).getTeam());
						removeAction();
					}
				}
			}
			return;
		}
		
		if(action == PlayerAction.UPGRADE_CITY) {
			MapIntersection intersection = objectManager.getIntersectionOnPosition(e.getX(), e.getY());
			if(intersection != null && intersection.getSettlementType() == SettlementType.VILLAGE && intersection.getTeam() == playerManager.getPlayers().get(actionTaker).getTeam()) {
				intersection.setType(SettlementType.CITY, playerManager.getPlayers().get(actionTaker).getTeam());
				removeAction();
			}
			return;
		}
	}
	
	public boolean isTownPlacementObstructed(MapIntersection intersection) {
		if(intersection == null || intersection.getSettlementType() != SettlementType.EMPTY) {
			return true;
		}
		for(MapIntersection adjacent: intersection.getAdjacentIntersections()) {
			if(adjacent.getSettlementType() != SettlementType.EMPTY) {
				return true;
			}
		}
		return false;
	}
	
	public boolean canPlayerReachIntersection(Player player, MapIntersection intersection) {
		for(MapEdge edge: intersection.getAdjacentEdges()) {
			if(edge.getTeam() == player.getTeam()) {
				return true;
			}
		}
		return false;
	}

	public PlayerAction getAction() {
		return action;
	}

}
