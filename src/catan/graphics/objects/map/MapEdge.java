package catan.graphics.objects.map;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import catan.player.Team;

public class MapEdge {
	
	private int x1, x2, y1, y2;
	
	private int xMid, yMid;
	
	private Polygon polygon;
	
	private boolean isRoad;
	
	private Team team;
	
	private List<MapIntersection> adjacentIntersections = new ArrayList<MapIntersection>();
	
	private List<MapEdge> adjacentEdges = new ArrayList<MapEdge>();
	
	public MapEdge(int x1, int y1, int x2, int y2, List<MapIntersection> adjacentIntersections) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.polygon = determineRenderedPoints();
		this.xMid = (x1 + x2) / 2;
		this.yMid = (y1 + y2) / 2;
		this.adjacentIntersections = adjacentIntersections;
		this.isRoad = false;
		this.team = Team.NONE;
	}
	
	public MapEdge(int x1, int y1, int x2, int y2, List<MapIntersection> adjacentIntersections, List<MapEdge> adjacentEdges) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.polygon = determineRenderedPoints();
		this.xMid = (x1 + x2) / 2;
		this.yMid = (y1 + y2) / 2;
		this.adjacentIntersections = adjacentIntersections;
		this.adjacentEdges = adjacentEdges;
		this.isRoad = false;
		this.team = Team.NONE;
	}
	
	public Polygon determineRenderedPoints() {
		float thickness = 3;
		if(y1 == y2 || x1 == x2) thickness = 2.5f;
		int xChange = y1 - y2;
		int yChange = -(x1 - x2);
		float lineLength = (float) Math.sqrt(xChange * xChange + yChange * yChange);
		float ratio =  thickness / lineLength;
		xChange = (int) (xChange * ratio);
		yChange = (int) (yChange * ratio);
		return new Polygon(new int[] {xChange + x1, xChange + x2, -xChange + x2, -xChange + x1}, new int[] {yChange + y1, yChange + y2, -yChange + y2, -yChange + y1}, 4);
	}

	public int getX1() {
		return x1;
	}

	public int getX2() {
		return x2;
	}

	public int getY1() {
		return y1;
	}

	public int getY2() {
		return y2;
	}

	public Polygon getPolygon() {
		return polygon;
	}

	public int getXMid() {
		return xMid;
	}

	public int getYMid() {
		return yMid;
	}

	public boolean isRoad() {
		return isRoad;
	}

	public Team getTeam() {
		return team;
	}

	public List<MapIntersection> getAdjacentIntersections() {
		return adjacentIntersections;
	}

	public List<MapEdge> getAdjacentEdges() {
		return adjacentEdges;
	}

	public void setAdjacentEdges(List<MapEdge> adjacentEdges) {
		this.adjacentEdges = adjacentEdges;
	}

	public void setTeam(Team team) {
		this.team = team;
		if(team == Team.NONE) {
			this.isRoad = false;
		} else {
			this.isRoad = true;
		}
	}

}
