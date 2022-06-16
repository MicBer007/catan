package catan.player;

import java.util.ArrayList;
import java.util.List;

public class PlayerInventory {
	
	private List<ResourceType> items = new ArrayList<ResourceType>();
	
	public PlayerInventory() { }
	
	public void addResource(ResourceType resource) {
		items.add(resource);
	}
	
	public void addResource(ResourceType resource, int amount) {
		for(int i = 0; i < amount; i++) {
			items.add(resource);
		}
	}
	
	public void removeResources(ResourceType resource) {
		items.remove(resource);
	}
	
	public boolean hasItems(List<ResourceType> resources) {
		List<ResourceType> duplicateItems = new ArrayList<ResourceType>();
		for(ResourceType resource: items) {
			duplicateItems.add(resource);
		}
		for(ResourceType resource: resources) {
			if(!duplicateItems.contains(resource)) {
				return false;
			}
			duplicateItems.remove(resource);
		}
		return true;
	}
	
	public boolean removeResources(List<ResourceType> resources) {
		if(!hasItems(resources)) {
			return false;
		}
		for(ResourceType resource: resources) {
			items.remove(resource);
		}
		return true;
	}

}
