package catan.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import catan.graphics.objects.map.PortType;

public class Player {
	
	private Map<ResourceType, Integer> resources = new HashMap<ResourceType, Integer>();
	
	private List<PortType> portsOwned = new ArrayList<PortType>();
	
	private Team team;
	
	private String name;

	private int victoryPoints;

	public Player(Team team, String name) {
		this.team = team;
		this.name = name;
		this.victoryPoints = 0;
		addResources(ResourceType.BRICK, 0);
		addResources(ResourceType.WOOD, 0);
		addResources(ResourceType.PASTURE, 0);
		addResources(ResourceType.WHEAT, 0);
		addResources(ResourceType.STONE, 0);
	}
	
	public int getTradingInfluence(ResourceType type) {
		int max = 4;
		for(PortType portType: portsOwned) {
			if(portType == PortType.THREE_FOR_ONE) {
				max = portType.getTradingInfluence();
				continue;
			}
			if(portType.getResourceType() == type) {
				return portType.getTradingInfluence();
			}
		}
		return max;
	}
	
	public boolean hasResources(ResourceType resource, int amount) {
		return resources.get(resource) >= amount;
	}
	
	public boolean hasResources(Map<ResourceType, Integer> resources) {
		for(ResourceType resource: resources.keySet()) {
			if(this.resources.get(resource) < resources.get(resource)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean removeResources(ResourceType type, int amount) {
		if(!hasResources(type, amount)) {
			return false;
		}
		resources.replace(type, resources.get(type) - amount);
		return true;
	}
	
	public boolean removeResources(Map<ResourceType, Integer> resources) {
		if(!hasResources(resources)) {
			return false;
		}
		for(ResourceType type: resources.keySet()) {
			this.resources.replace(type, this.resources.get(type) - resources.get(type));
		}
		return true;
	}

	public Map<ResourceType, Integer> getResources() {
		return resources;
	}
	
	public void addResource(ResourceType type) {
		addResources(type, 1);
	}
	
	public void addResources(ResourceType type, int number) {
		if(resources.get(type) == null) {
			resources.put(type, number);
			return;
		}
		int numResources = resources.get(type);
		resources.replace(type, numResources + number);
	}

	public List<PortType> getPortsOwned() {
		return portsOwned;
	}
	
	public void addPort(PortType type) {
		portsOwned.add(type);
	}

	public void setPortsOwned(List<PortType> portsOwned) {
		this.portsOwned = portsOwned;
	}

	public Team getTeam() {
		return team;
	}

	public String getName() {
		return name;
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}

	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}

	public void changeVictoryPoints(int change) {
		this.victoryPoints += change;
	}

}
