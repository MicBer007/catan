package catan.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import catan.graphics.objects.ObjectManager;
import catan.graphics.objects.ui.Button;
import catan.graphics.objects.ui.Hitbox;
import catan.graphics.objects.ui.OptionBox;
import catan.graphics.objects.ui.Panel;
import catan.graphics.objects.ui.TextComponent;
import catan.graphics.rendering.PlayerRenderer;
import settings.Settings;
import utils.HitboxUtils;

public class PlayerManager {
	
	private List<Player> players = new ArrayList<Player>();
	
	private ObjectManager objectManager;
	
	private PlayerActionManager playerActionManager;
	private PlayerCardManager playerCardManager;
	
	private boolean playing;
	private int whoseTurn = 0;
	private Player activePlayer;
	
	private Panel panel;
	
	private Panel tradingPanel;
	
	private PlayerRenderer pRenderer;
	
	public PlayerManager(ObjectManager objectManager) {
		this.objectManager = objectManager;
		players.add(new Player(Team.RED, "red player"));
		pRenderer = new PlayerRenderer(new Hitbox(10, 10, 50, 50));
		playerCardManager = new PlayerCardManager(this);
		playing = false;
		playerActionManager = new PlayerActionManager(this, objectManager, players);
		initializeUI();
	}
	
	public void startGame() {
		if(!playing) {
			setActivePlayer(0);
			panel.setVisible(true);
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
		if(playerActionManager.getAction() == PlayerAction.NONE) {
			setActivePlayer(whoseTurn + 1);
			playerCardManager.simulateNumberRoll();
		}
	}
	
	public void playerBuyRoad() {
		if(playerCardManager.removePlayerResources(activePlayer, Settings.getRoadCost())) {
			playerActionManager.addItemToQueue(whoseTurn, PlayerAction.PLACE_ROAD);
		}
	}
	
	public void playerBuyVillage() {
		if(playerCardManager.removePlayerResources(activePlayer, Settings.getVillageCost())) {
			playerActionManager.addItemToQueue(whoseTurn, PlayerAction.PLACE_TOWN);
		}
	}
	
	public void playerBuyCity() {
		if(playerCardManager.removePlayerResources(activePlayer, Settings.getCityCost())) {
			playerActionManager.addItemToQueue(whoseTurn, PlayerAction.UPGRADE_CITY);
		}
	}
	
	public void playerTradeWithBank() {
		OptionBox tradeIn = (OptionBox) (tradingPanel.getSubComponents().get(2));
		OptionBox tradeOut = (OptionBox) (tradingPanel.getSubComponents().get(3));
		ResourceType in = ResourceType.getResourceTypeBasedOnName(tradeIn.getCurrentOption().getTextComponent().getText());
		ResourceType out = ResourceType.getResourceTypeBasedOnName(tradeOut.getCurrentOption().getTextComponent().getText());
		playerTradeResources(activePlayer, in, out);
	}
	
	public void playerTradeResources(Player player, ResourceType itemToBeGiven, ResourceType itemToBeGot) {
		if(player.removeResources(itemToBeGiven, player.getTradingInfluence(itemToBeGiven))) {
			player.addResource(itemToBeGot);
		}
	}
	
	public void mouseReleasedEvent(MouseEvent e) {
		if(playing) {
			panel.mouseReleased(e);
			tradingPanel.mouseReleased(e);
		}
		playerActionManager.mouseReleasedEvent(e);
		playerCardManager.mouseReleasedEvent(e);
	}
	
	public void mousePressedEvent(MouseEvent e) {
		if(playing) {
			tradingPanel.mousePressed(e);
			panel.mousePressed(e);
		}
		playerCardManager.mousePressedEvent(e);
	}
	
	public void mouseMoveEvent(MouseEvent e) {
		if(playing) {
			panel.mouseMoved(e);
			tradingPanel.mouseMoved(e);
		}
		playerCardManager.mouseMovedEvent(e);
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
			playerCardManager.renderPlayerCards(g, activePlayer);
			tradingPanel.render(g);
		}
		pRenderer.renderCurrentPlayer(g, activePlayer);
	}
	
	private void initializeUI() {
		panel = new Panel(new Hitbox(Settings.SCREEN_WIDTH / 2, 0, Settings.SCREEN_WIDTH / 2, Settings.SCREEN_HEIGHT), new Color(150, 70, 13));
		List<Hitbox> hitboxes = HitboxUtils.generateHitboxes(Settings.SCREEN_WIDTH / 2 + Settings.UI_PADDING, Settings.SCREEN_HEIGHT - Settings.UI_PADDING - 80, 5, Settings.SCREEN_WIDTH - Settings.UI_PADDING + 5, Settings.SCREEN_HEIGHT - Settings.UI_PADDING - 80, 90, 40);
		panel.addComponent(new Button(hitboxes.get(0), "finish turn", new Color(142, 57, 10), new Runnable() {@Override public void run(){nextPlayer();}}));
		panel.addComponent(new Button(hitboxes.get(1), "buy road", new Color(142, 57, 10), new Runnable() {@Override public void run(){playerBuyRoad();}}));
		panel.addComponent(new Button(hitboxes.get(2), "buy village", new Color(142, 57, 10), new Runnable() {@Override public void run(){playerBuyVillage();}}));
		panel.addComponent(new Button(hitboxes.get(3), "buy city", new Color(142, 57, 10), new Runnable() {@Override public void run(){playerBuyCity();}}));
		panel.addComponent(new Button(hitboxes.get(4), "trade", new Color(142, 57, 10), new Runnable() {@Override public void run(){tradingPanel.toggleVisibility();}}));
		panel.setVisible(false);
		
		OptionBox tradeBox = new OptionBox(new Hitbox(Settings.SCREEN_WIDTH / 2 - 120, Settings.SCREEN_HEIGHT / 2 - 35, 70, 30), Color.WHITE);
		tradeBox.addOption("Brick", new Color(184, 66, 19));
		tradeBox.addOption("Wood", new Color(21, 121, 26));
		tradeBox.addOption("Pasture", new Color(76, 230, 65));
		tradeBox.addOption("Wheat", new Color(245, 219, 24));
		tradeBox.addOption("Stone", new Color(113, 113, 116));

		OptionBox tradeBox2 = new OptionBox(new Hitbox(Settings.SCREEN_WIDTH / 2 - 15, Settings.SCREEN_HEIGHT / 2 - 35, 70, 30), Color.WHITE);
		tradeBox2.addOption("Brick", new Color(184, 66, 19));
		tradeBox2.addOption("Wood", new Color(21, 121, 26));
		tradeBox2.addOption("Pasture", new Color(76, 230, 65));
		tradeBox2.addOption("Wheat", new Color(245, 219, 24));
		tradeBox2.addOption("Stone", new Color(113, 113, 116));
		
		Button confirm = new Button(new Hitbox(Settings.SCREEN_WIDTH / 2 + 80, Settings.SCREEN_HEIGHT / 2 - 40, 100, 40), "Confirm", new Color(89, 197, 26), new Runnable() {@Override public void run() {playerTradeWithBank();}});
		
		tradingPanel = new Panel(new Hitbox(Settings.SCREEN_WIDTH / 2 - 210, Settings.SCREEN_HEIGHT / 2 - 70, 420, 100), new Color(130, 63, 10));
		tradingPanel.addComponent(new TextComponent(new Hitbox(Settings.SCREEN_WIDTH / 2 - 210, Settings.SCREEN_HEIGHT / 2 - 40, 100, 40), "I want to sell", Color.BLACK, new Color(130, 63, 10)));
		tradingPanel.addComponent(new TextComponent(new Hitbox(Settings.SCREEN_WIDTH / 2 - 48, Settings.SCREEN_HEIGHT / 2 - 40, 30, 40), "for", Color.BLACK, new Color(130, 63, 10)));
		tradingPanel.addComponent(tradeBox);
		tradingPanel.addComponent(tradeBox2);
		tradingPanel.addComponent(confirm);
		tradingPanel.setVisible(false);
		tradingPanel.setNotifyComponentsAlways(true);
	}

	public PlayerActionManager getPlayerActionManager() {
		return playerActionManager;
	}

	public PlayerCardManager getPlayerCardManager() {
		return playerCardManager;
	}

	public ObjectManager getObjectManager() {
		return objectManager;
	}
	
	public void putRobberOnPlayer(Player player, Player playerToRecieve) {
		int playerCards = player.getResources().size();
		int random = new Random().nextInt(playerCards);
		playerCardManager.addPlayerCard(playerToRecieve, playerCardManager.getResources().get(player).remove(random));
		if(playerCards >= 8) {
			playerCardManager.setNumberOfDiscardedCardsNeeded((int) Math.floor(playerCards / 2));
		}
	}

}
