package catan.graphics.objects.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RigidTileGroup {
	
	private List<Integer> xPositions = new ArrayList<Integer>();
	private List<Integer> yPositions = new ArrayList<Integer>();
	
	private List<Integer> portPositions = new ArrayList<Integer>();
	
	private List<String> tileTags = new ArrayList<String>();
	
	private String groupName;
	
	private int size;
	
	public RigidTileGroup(String groupName, String[] tags, Integer[] xPositions, Integer[] yPositions, Integer[] portPositions) {
		this.groupName = groupName;
		this.size = Math.min(tags.length, Math.min(xPositions.length, yPositions.length));
		this.xPositions = Arrays.asList(xPositions);
		this.yPositions = Arrays.asList(yPositions);
		this.tileTags = Arrays.asList(tags);
		this.portPositions = Arrays.asList(portPositions);
	}

	public List<Integer> getXPositions() {
		return xPositions;
	}

	public List<Integer> getYPositions() {
		return yPositions;
	}

	public List<Integer> getPortPositions() {
		return portPositions;
	}

	public List<String> getTileTags() {
		return tileTags;
	}

	public String getGroupName() {
		return groupName;
	}

	public int getSize() {
		return size;
	}

}
