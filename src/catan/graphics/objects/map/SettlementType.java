package catan.graphics.objects.map;

public enum SettlementType {
	
	EMPTY, VILLAGE, CITY;
	
	private Integer[] numberOfResourcesToCollect = new Integer[] {0, 1, 2};
	
	public int getNumberOfResourcesToCollect() {
		return numberOfResourcesToCollect[ordinal()];
	}

}
