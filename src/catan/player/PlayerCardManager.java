package catan.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import catan.graphics.objects.map.GameTile;
import catan.graphics.objects.map.MapIntersection;
import catan.graphics.objects.map.SettlementType;
import catan.graphics.objects.ui.Button;
import catan.graphics.objects.ui.Hitbox;
import settings.Settings;

public class PlayerCardManager {
	
	private Random random = new Random();
	
	private Hitbox areaForCards = new Hitbox(Settings.SCREEN_WIDTH / 2 + 50, 50 + random.nextInt(2), 1040, 60);
	private Hitbox areaForDiscardedCards = new Hitbox(Settings.SCREEN_WIDTH - 110, 100, 50, 600);
	
	private Button dusbin = new Button(new Hitbox(Settings.SCREEN_WIDTH - 135, 40, 100, 40), "discard items", new Color(120, 120, 120), new Runnable() {@Override public void run() {discardItems();}});
	
	private int rolledNumber;
	
	private List<Hitbox> cardPositions = new ArrayList<Hitbox>();
	
	private List<Hitbox> discardedCardPositions = new ArrayList<Hitbox>();
	private List<ResourceType> discardedCardTypes = new ArrayList<ResourceType>();
	
	private Map<Player, List<ResourceType>> resources = new HashMap<Player, List<ResourceType>>();
	
	private PlayerManager playerManager;
	
	private int numberOfDiscardedCardsNeeded = 4;
	
	public PlayerCardManager(PlayerManager playerManager) {
		this.playerManager = playerManager;
		for(int i = 0; i < 30; i++) {
			cardPositions.add(new Hitbox(areaForCards.getX1() + i * 20, areaForCards.getY1() + random.nextInt(2), 38, 58));
		}
		for(int i = 0; i < 15; i++) {
			discardedCardPositions.add(new Hitbox(areaForDiscardedCards.getX1() + random.nextInt(8), areaForDiscardedCards.getY1() + i * 40, 38, 58));
		}
		for(Player player: playerManager.getPlayers()) {
			resources.put(player, new ArrayList<ResourceType>());
		}
	}
	
	public void mouseReleasedEvent(MouseEvent e) {
		if(numberOfDiscardedCardsNeeded > 0) {
			if(areaForCards.isPointInHitbox(e.getX(), e.getY()) && discardedCardTypes.size() < numberOfDiscardedCardsNeeded) {
				for(int i = resources.get(playerManager.getActivePlayer()).size() - 1; i >= 0; i--) {
					if(cardPositions.get(i).isPointInHitbox(e.getX(), e.getY())) {
						discardedCardTypes.add(resources.get(playerManager.getActivePlayer()).remove(i));
						break;
					}
				}
			} else if(areaForDiscardedCards.isPointInHitbox(e.getX(), e.getY())) {
				for(int i = discardedCardTypes.size() - 1; i >= 0; i--) {
					if(discardedCardPositions.get(i).isPointInHitbox(e.getX(), e.getY())) {
						resources.get(playerManager.getActivePlayer()).add(discardedCardTypes.remove(i));
						break;
					}
				}
			}
		}
		dusbin.mouseReleased(e);
	}
	
	public void mouseMovedEvent(MouseEvent e) {
		dusbin.mouseMoved(e);
	}
	
	public void mousePressedEvent(MouseEvent e) {
		dusbin.mousePressed(e);
	}
	
	public void addPlayerCard(Player player, ResourceType card) {
		player.addResource(card);
		List<ResourceType> playerResources = resources.get(player);
		playerResources.add(card);
		resources.replace(player, playerResources);
	}
	
	public void addPlayerCard(Player player, ResourceType card, int amount) {
		player.addResources(card, amount);
		List<ResourceType> playerResources = resources.get(player);
		for(int i = 0; i < amount; i++) {
			playerResources.add(card);
		}
		resources.replace(player, playerResources);
	}
	
	public boolean removePlayerResources(Player player, Map<ResourceType, Integer> resources) {
		if(!player.hasResources(resources)) {
			return false;
		}
		player.removeResources(resources);
		List<ResourceType> playerResources = this.resources.get(player);
		for(ResourceType type: resources.keySet()) {
			int amount = resources.get(type);
			for(int i = 0; i < amount; i++) {
				playerResources.remove(type);
			}
		}
		return true;
	}
	
	public void renderPlayerCards(Graphics g, Player player) {
		if(numberOfDiscardedCardsNeeded > 0) {
			dusbin.render(g);
		}
		List<ResourceType> playerResources = resources.get(player);
		for(int i = 0; i < playerResources.size(); i++) {
			ResourceType type = playerResources.get(i);
			Hitbox h = cardPositions.get(i);
			g.setColor(Color.WHITE);
			h.draw(g);
			g.setColor(type.getColour());
			g.fillRect(h.getX1() + 1, h.getY1() + 1, h.getWidth() - 2, h.getHeight() - 2);
		}
		for(int i = 0; i < discardedCardTypes.size(); i++) {
			ResourceType type = discardedCardTypes.get(i);
			Hitbox h = discardedCardPositions.get(i);
			g.setColor(Color.WHITE);
			h.draw(g);
			g.setColor(type.getColour());
			g.fillRect(h.getX1() + 1, h.getY1() + 1, h.getWidth() - 2, h.getHeight() - 2);
		}
	}
	
	public void simulateNumberRoll() {
		rolledNumber = getRandomDiceNumber();
		if(rolledNumber == 7) {
			return;
		}
		for(GameTile tile: playerManager.getObjectManager().getMap().getTiles()) {
			if(tile.getDiceNumber() == rolledNumber) {
				for(MapIntersection intersection: tile.getAdjacentIntersections()) {
					if(intersection.getSettlementType() != SettlementType.EMPTY) {
						Player player = playerManager.getPlayerOfTeam(intersection.getTeam());
						ResourceType resource = tile.getTileType().getCorrespondingResource();
						if(resource != null) {
							addPlayerCard(player, resource, intersection.getSettlementType().getNumberOfResourcesToCollect());
						}
					}
				}
			}
		}
	}
	
	public int getRandomDiceNumber() {
		return random.nextInt(6) + random.nextInt(6) + 2;
	}
	
	public void discardItems() {
		if(numberOfDiscardedCardsNeeded == discardedCardTypes.size()) {
			discardedCardTypes.clear();
			numberOfDiscardedCardsNeeded = 0;
		}
	}

	public int getNumberOfDiscardedCardsNeeded() {
		return numberOfDiscardedCardsNeeded;
	}

	public void setNumberOfDiscardedCardsNeeded(int numberOfDiscardedCardsNeeded) {
		this.numberOfDiscardedCardsNeeded = numberOfDiscardedCardsNeeded;
	}

	public Map<Player, List<ResourceType>> getResources() {
		return resources;
	}

}
