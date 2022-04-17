package catan.graphics.objects.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PopulatedTileGroup {
	
	private String name;
	
	private int size;
	
	private List<String> tags = new ArrayList<String>();
	
	private List<GameTileType> tileTypes = new ArrayList<GameTileType>();
	
	private List<Integer> numbers = new ArrayList<Integer>();
	
	private List<PortType> portTypes = new ArrayList<PortType>();

	public PopulatedTileGroup(String name, String[] tags, GameTileType[] tileTypes, PortType[] portTypes, Integer[] numbers) {
		this.name = name;
		this.size = tags.length;
		this.tags = Arrays.asList(tags);
		this.tileTypes = Arrays.asList(tileTypes);
		this.portTypes = Arrays.asList(portTypes);
		this.numbers = Arrays.asList(numbers);
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public List<String> getTags() {
		return tags;
	}

	public List<GameTileType> getTileTypes() {
		return tileTypes;
	}
	
	public List<PortType> getPortTypes() {
		return portTypes;
	}

	public List<Integer> getNumbers() {
		return numbers;
	}

	public GameTileType getCorrespondingTileType(String tag) {
		return tileTypes.get(tags.indexOf(tag));
	}

	public PortType getCorrespondingPortType(String tag) {
		return portTypes.get(tags.indexOf(tag));
	}

	public int getCorrespondingDiceNumber(String tag) {
		return numbers.get(tags.indexOf(tag));
	}

}
