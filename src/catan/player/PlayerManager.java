package catan.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import catan.CatanGame;
import catan.graphics.objects.ObjectManager;
import catan.graphics.objects.map.GameTile;
import catan.graphics.objects.map.GameTileType;
import catan.graphics.objects.map.MapEdge;
import catan.graphics.objects.map.MapIntersection;
import catan.graphics.objects.map.SettlementType;
import catan.graphics.objects.ui.Button;
import catan.graphics.objects.ui.Hitbox;
import catan.graphics.objects.ui.OptionBox;
import catan.graphics.objects.ui.Panel;
import catan.graphics.objects.ui.TextComponent;
import catan.graphics.rendering.PlayerRenderer;
import debug.Debug;

public class PlayerManager {
	
	private List<Player> players = new ArrayList<Player>();
	
	private ObjectManager objectManager;
	
	private List<Integer> actionTakers = new ArrayList<Integer>();
	private List<PlayerAction> actions = new ArrayList<PlayerAction>();

	private int actionTaker;
	private PlayerAction action;
	
	private boolean playing;
	private int whoseTurn = 0;
	private Player activePlayer;
	
	private Panel panel;
	
	private Panel tradingPanel;
	
	private PlayerRenderer pRenderer;
	
	private Random random;
	
	public PlayerManager(CatanGame game, ObjectManager objectManager) {
		players.add(new Player(Team.RED, "red player"));
		players.add(new Player(Team.BLUE, "blue player"));
		this.objectManager = objectManager;
		pRenderer = new PlayerRenderer(new Hitbox(10, 10, 50, 50), Debug.SCREEN_WIDTH / 2 + 50, 50);
		playing = false;
		action = PlayerAction.NONE;
		random = new Random();
		startGame();
	}
	
	public void startGame() {
		for(int i = 0; i < players.size(); i++) {
			addItemToQueue(i, PlayerAction.PLACE_TOWN_START);
		}
//		for(int i = players.size() - 1; i >= 0; i--) {
//			addItemToQueue(i, PlayerAction.PLACE_TOWN_START_WITH_RESOURCES);
//		}
		for(int i = 0; i < players.size(); i++) {
			addItemToQueue(i, PlayerAction.PLACE_ROAD_START);
		}
//		for(int i = players.size() - 1; i >= 0; i--) {
//			addItemToQueue(i, PlayerAction.PLACE_ROAD_START);
//		}
	}
	
	public void emptyQueue() {
		if(!playing) {
			setActivePlayer(0);
			Runnable nextTurnButton = new Runnable() {@Override public void run(){nextPlayer();}};
			Runnable buyRoad = new Runnable() {@Override public void run(){playerBuyRoad();}};
			Runnable buyVillage = new Runnable() {@Override public void run(){playerBuyVillage();}};
			Runnable buyCity = new Runnable() {@Override public void run(){playerBuyCity();}};
			Runnable popUpPlayerTradePanel = new Runnable() {@Override public void run(){popUpPlayerTradePanel();}};
			panel = new Panel(new Hitbox(Debug.SCREEN_WIDTH / 2, 0, Debug.SCREEN_WIDTH / 2, Debug.SCREEN_HEIGHT), new Color(153, 74, 15));
			panel.addComponent(new Button(new Hitbox(Debug.SCREEN_WIDTH - 140, 20, 90, 40), "finish turn", new Color(122, 40, 0), new Color(201, 85, 17), new Color(185, 51, 18), nextTurnButton));
			panel.addComponent(new Button(new Hitbox(Debug.SCREEN_WIDTH / 2 + 40, Debug.SCREEN_HEIGHT - 100, 90, 40), "buy road", new Color(142, 57, 10), new Color(201, 85, 17), new Color(185, 51, 18), buyRoad));
			panel.addComponent(new Button(new Hitbox(Debug.SCREEN_WIDTH / 2 + 190, Debug.SCREEN_HEIGHT - 100, 90, 40), "buy village", new Color(142, 57, 10), new Color(201, 85, 17), new Color(185, 51, 18), buyVillage));
			panel.addComponent(new Button(new Hitbox(Debug.SCREEN_WIDTH / 2 + 340, Debug.SCREEN_HEIGHT - 100, 90, 40), "buy city", new Color(142, 57, 10), new Color(201, 85, 17), new Color(185, 51, 18), buyCity));
			panel.addComponent(new Button(new Hitbox(Debug.SCREEN_WIDTH / 2 + 490, Debug.SCREEN_HEIGHT - 100, 90, 40), "trade", new Color(142, 57, 10), new Color(201, 85, 17), new Color(185, 51, 18), popUpPlayerTradePanel));
			
			initializeTradingPanel();
			playing = true;
		}
	}
	
	public void setActivePlayer(int index) {
		whoseTurn = index;
		if(whoseTurn >= players.size()) {
			whoseTurn = 0;
		}
		activePlayer = players.get(whoseTurn);
	}
	
	public void nextPlayer() {
		if(action == PlayerAction.NONE) {
			setActivePlayer(whoseTurn + 1);
			simulateNumberRoll(getRandomDiceNumber());
		}
	}
	
	public void playerBuyRoad() {
		if(playerHasResource(activePlayer, ResourceType.WOOD, 1) && playerHasResource(activePlayer, ResourceType.BRICK, 1)) {
			removeResourceFromPlayer(activePlayer, ResourceType.WOOD, 1);
			removeResourceFromPlayer(activePlayer, ResourceType.BRICK, 1);
			addItemToQueue(whoseTurn, PlayerAction.PLACE_ROAD);
		}
	}
	
	public void playerBuyVillage() {
		if(playerHasResource(activePlayer, ResourceType.WOOD, 1) && playerHasResource(activePlayer, ResourceType.BRICK, 1) && playerHasResource(activePlayer, ResourceType.WHEAT, 1) && playerHasResource(activePlayer, ResourceType.PASTURE, 1)) {
			removeResourceFromPlayer(activePlayer, ResourceType.WOOD, 1);
			removeResourceFromPlayer(activePlayer, ResourceType.BRICK, 1);
			removeResourceFromPlayer(activePlayer, ResourceType.WHEAT, 1);
			removeResourceFromPlayer(activePlayer, ResourceType.PASTURE, 1);
			addItemToQueue(whoseTurn, PlayerAction.PLACE_TOWN);
		}
	}
	
	public void playerBuyCity() {
		if(playerHasResource(activePlayer, ResourceType.WHEAT, 2) && playerHasResource(activePlayer, ResourceType.STONE, 3)) {
			removeResourceFromPlayer(activePlayer, ResourceType.WHEAT, 2);
			removeResourceFromPlayer(activePlayer, ResourceType.STONE, 3);
			addItemToQueue(whoseTurn, PlayerAction.UPGRADE_CITY);
		}
	}
	
	public void playerClickConfirmForTrade() {
		OptionBox tradeIn = (OptionBox) (tradingPanel.getSubComponents().get(2));
		OptionBox tradeOut = (OptionBox) (tradingPanel.getSubComponents().get(3));
		ResourceType in = ResourceType.getResourceTypeBasedOnName(tradeIn.getCurrentOption().getTextComponent().getText());
		ResourceType out = ResourceType.getResourceTypeBasedOnName(tradeOut.getCurrentOption().getTextComponent().getText());
		playerTradeResources(activePlayer, in, out);
	}
	
	public void popUpPlayerTradePanel() {
		tradingPanel.setVisible(!tradingPanel.isVisible());
	}
	
	public void playerTradeResources(Player player, ResourceType itemToBeGiven, ResourceType itemToBeGot) {
		int amountNeeded = player.getTradingInfluence(itemToBeGiven);
		if(playerHasResource(player, itemToBeGiven, amountNeeded)) {
			removeResourceFromPlayer(player, itemToBeGiven, amountNeeded);
			player.addResource(itemToBeGot);
		}
	}
	
	private boolean playerHasResource(Player player, ResourceType resource, int amount) {
		if(!player.getResources().containsKey(resource)) {
			return false;
		}
		return player.getResources().get(resource) >= amount;
	}
	
	private void removeResourceFromPlayer(Player player, ResourceType type, int amount) {
		if(player.getResources().containsKey(type)) {
			int current = player.getResources().get(type);
			if(current >= amount) {
				player.getResources().replace(type, current - amount);
			}
		}
	}
	
	public int getRandomDiceNumber() {
		return random.nextInt(6) + random.nextInt(6) + 2;
	}
	
	public void addItemToQueue(int actionTaker, PlayerAction action) {
		if(this.action == PlayerAction.NONE) {
			this.actionTaker = actionTaker;
			this.action = action;
			setActivePlayer(actionTaker);
			return;
		}
		actionTakers.add(actionTaker);
		actions.add(action);
	}
	
	public void actionCompleted() {
		if(actions.size() > 0) {
			actionTaker = actionTakers.remove(0);
			action = actions.remove(0);
			setActivePlayer(actionTaker);
			return;
		}
		action = PlayerAction.NONE;
		emptyQueue();
	}
	
	public void mouseReleasedEvent(MouseEvent e) {
		if(playing) {
			if(panel.getHitbox().isPointInHitbox(e.getX(), e.getY())) {
				panel.mouseReleased(e);
				tradingPanel.mouseReleased(e);
				return;
			}
		}
		if(action == PlayerAction.PLACE_TOWN) {
			MapIntersection intersection = objectManager.getIntersectionOnPosition(e.getX(), e.getY());
			if(intersection != null && intersection.getSettlementType() == SettlementType.EMPTY) {
				for(MapIntersection adjacent: intersection.getAdjacentIntersections()) {
					if(adjacent.getSettlementType() != SettlementType.EMPTY) {
						return;
					}
				}
				for(MapEdge edge: intersection.getAdjacentEdges()) {
					if(edge.getTeam() == players.get(actionTaker).getTeam()) {
						intersection.setType(SettlementType.VILLAGE, players.get(actionTaker).getTeam());
						if(intersection.getPortType() != null) {
							players.get(actionTaker).addPort(intersection.getPortType());
						}
						actionCompleted();
					}
				}
			}
			return;
		}
		if(action == PlayerAction.PLACE_TOWN_START) {
			MapIntersection intersection = objectManager.getIntersectionOnPosition(e.getX(), e.getY());
			if(intersection != null && intersection.getSettlementType() == SettlementType.EMPTY) {
				for(MapIntersection adjacent: intersection.getAdjacentIntersections()) {
					if(adjacent.getSettlementType() != SettlementType.EMPTY) {
						return;
					}
				}
				intersection.setType(SettlementType.VILLAGE, players.get(actionTaker).getTeam());
				if(intersection.getPortType() != null) {
					players.get(actionTaker).addPort(intersection.getPortType());
				}
				actionCompleted();
			}
			return;
		}
		if(action == PlayerAction.PLACE_TOWN_START_WITH_RESOURCES) {
			MapIntersection intersection = objectManager.getIntersectionOnPosition(e.getX(), e.getY());
			if(intersection != null && intersection.getSettlementType() == SettlementType.EMPTY) {
				for(MapIntersection adjacent: intersection.getAdjacentIntersections()) {
					if(adjacent.getSettlementType() != SettlementType.EMPTY) {
						return;
					}
				}
				intersection.setType(SettlementType.VILLAGE, players.get(actionTaker).getTeam());
				if(intersection.getPortType() != null) {
					players.get(actionTaker).addPort(intersection.getPortType());
				}
				for(GameTile tile: intersection.getAdjacentTiles()) {
					if(tile.getTileType() != GameTileType.DESERT) {
						activePlayer.addResource(tile.getTileType().getCorrespondingResource());
					}
				}
				actionCompleted();
			}
			return;
		}
		if(action == PlayerAction.PLACE_ROAD) {
			MapEdge edge = objectManager.getEdgeOnPosition(e.getX(), e.getY());
			if(edge != null && !edge.isRoad()) {
				for(MapEdge adjacentEdge: edge.getAdjacentEdges()) {
					if(adjacentEdge.isRoad() && adjacentEdge.getTeam() == players.get(actionTaker).getTeam()) {
						edge.setTeam(players.get(actionTaker).getTeam());
						actionCompleted();
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
					if(intersection.getSettlementType() == SettlementType.VILLAGE && intersection.getTeam() == players.get(actionTaker).getTeam()) {
						for(MapEdge adjacentEdge: intersection.getAdjacentEdges()) {
							if(adjacentEdge.isRoad()) {
								return;
							}
						}
						edge.setTeam(players.get(actionTaker).getTeam());
						actionCompleted();
					}
				}
			}
			return;
		}
		if(action == PlayerAction.UPGRADE_CITY) {
			MapIntersection intersection = objectManager.getIntersectionOnPosition(e.getX(), e.getY());
			if(intersection != null && intersection.getSettlementType() == SettlementType.VILLAGE && intersection.getTeam() == players.get(actionTaker).getTeam()) {
				intersection.setType(SettlementType.CITY, players.get(actionTaker).getTeam());
				actionCompleted();
			}
			return;
		}
		if(action == PlayerAction.MOVE_ROBBER) {
			GameTile tile = objectManager.getTileOnPosition(e.getX(), e.getY());
			if(tile != null && !tile.isRobber()) {
				objectManager.putRobberOnTile(tile);
				action = PlayerAction.CHOOSE_PLAYER_TO_STEAL_FROM;
			}
			return;
		}
		if(action == PlayerAction.CHOOSE_PLAYER_TO_STEAL_FROM) {
			MapIntersection intersection = objectManager.getIntersectionOnPosition(e.getX(), e.getY());
			if(intersection.getTeam() != activePlayer.getTeam() && intersection.getTeam() != Team.NONE) {
				
			}
			return;
		}
	}
	
	public void mousePressedEvent(MouseEvent e) {
		if(playing) {
			tradingPanel.mousePressed(e);
			panel.mousePressed(e);
		}
	}
	
	public void mouseMoveEvent(MouseEvent e) {
		if(playing) {
			panel.mouseMoved(e);
			tradingPanel.mouseMoved(e);
		}
	}
	
	public void simulateNumberRoll(int number) {
		if(number == 7) {
			addItemToQueue(whoseTurn, PlayerAction.MOVE_ROBBER);
			return;
		}
		for(GameTile tile: objectManager.getMap().getTiles()) {
			if(tile.getDiceNumber() == number && !tile.isRobber()) {
				for(MapIntersection intersection: tile.getAdjacentIntersections()) {
					if(intersection.getSettlementType() != SettlementType.EMPTY) {
						Player player = getPlayerOfTeam(intersection.getTeam());
						ResourceType resource = tile.getTileType().getCorrespondingResource();
						if(resource != null) {
							player.addResources(resource, intersection.getSettlementType().getNumberOfResourcesToCollect());
						}
					}
				}
			}
		}
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public Player getPlayerOfTeamName(String name) {
		for(Player player: players) {
			if(player.getName().equalsIgnoreCase(name)) {
				return player;
			}
		}
		return null;
	}
	
	public Player getPlayerOfTeam(Team team) {
		for(Player player: players) {
			if(player.getTeam() == team) {
				return player;
			}
		}
		return null;
	}

	public int getActionTaker() {
		return actionTaker;
	}

	public PlayerAction getAction() {
		return action;
	}

	public boolean isPlaying() {
		return playing;
	}

	public int getWhoseTurn() {
		return whoseTurn;
	}

	public Player getActivePlayer() {
		return activePlayer;
	}
	
	public void render(Graphics g) {
		if(playing) {
			panel.render(g);
			pRenderer.renderPlayerResources(g, activePlayer.getResources());
			tradingPanel.render(g);
		}
		pRenderer.renderCurrentPlayer(g, activePlayer);
	}
	
	private void initializeTradingPanel() {
		TextComponent tradeText1 = new TextComponent(new Hitbox(Debug.SCREEN_WIDTH / 2 - 210, Debug.SCREEN_HEIGHT / 2 - 40, 100, 40), "I want to sell", Color.BLACK, new Color(130, 63, 10));
		TextComponent tradeText2 = new TextComponent(new Hitbox(Debug.SCREEN_WIDTH / 2 - 48, Debug.SCREEN_HEIGHT / 2 - 40, 30, 40), "for", Color.BLACK, new Color(130, 63, 10));
		
		OptionBox tradeBox = new OptionBox(new Hitbox(Debug.SCREEN_WIDTH / 2 - 120, Debug.SCREEN_HEIGHT / 2 - 35, 70, 30), Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE);
		tradeBox.addOption("Brick", new Color(184, 66, 19), new Color(156, 56, 16), new Color(207, 74, 21));
		tradeBox.addOption("Wood", new Color(21, 121, 26), new Color(16, 95, 20), new Color(30, 173, 37));
		tradeBox.addOption("Pasture", new Color(76, 230, 65), new Color(48, 171, 37), new Color(86, 215, 75));
		tradeBox.addOption("Wheat", new Color(245, 219, 24), new Color(237, 178, 2), new Color(235, 222, 40));
		tradeBox.addOption("Stone", new Color(113, 113, 116), new Color(96, 96, 102), new Color(153, 153, 153));

		OptionBox tradeBox2 = new OptionBox(new Hitbox(Debug.SCREEN_WIDTH / 2 - 15, Debug.SCREEN_HEIGHT / 2 - 35, 70, 30), Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE);
		tradeBox2.addOption("Brick", new Color(184, 66, 19), new Color(156, 56, 16), new Color(207, 74, 21));
		tradeBox2.addOption("Wood", new Color(21, 121, 26), new Color(16, 95, 20), new Color(30, 173, 37));
		tradeBox2.addOption("Pasture", new Color(76, 230, 65), new Color(48, 171, 37), new Color(86, 215, 75));
		tradeBox2.addOption("Wheat", new Color(245, 219, 24), new Color(237, 178, 2), new Color(235, 222, 40));
		tradeBox2.addOption("Stone", new Color(113, 113, 116), new Color(96, 96, 102), new Color(153, 153, 153));
		
		Runnable run = new Runnable() {@Override public void run() {playerClickConfirmForTrade();}};
		Button confirm = new Button(new Hitbox(Debug.SCREEN_WIDTH / 2 + 80, Debug.SCREEN_HEIGHT / 2 - 40, 100, 40), "Confirm", new Color(89, 197, 26), new Color(86, 167, 15), new Color(73, 228, 26), run);
		
		tradingPanel = new Panel(new Hitbox(Debug.SCREEN_WIDTH / 2 - 210, Debug.SCREEN_HEIGHT / 2 - 70, 420, 100), new Color(130, 63, 10));
		tradingPanel.addComponent(tradeText1);
		tradingPanel.addComponent(tradeText2);
		tradingPanel.addComponent(tradeBox);
		tradingPanel.addComponent(tradeBox2);
		tradingPanel.addComponent(confirm);
		tradingPanel.setVisible(false);
		tradingPanel.setNotifyComponentsAlways(true);
	}

}
