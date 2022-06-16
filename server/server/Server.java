package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import packets.ConnectionRequestedPacket;
import packets.ConnectionGrantedPacket;
import packets.GameInitPacket;
import packets.GameInitPhasePacket;
import packets.GamePlayingPhasePacket;
import packets.NextTurnPacket;
import packets.Packet;
import packets.BuildStructurePacket;
import packets.PlayerFinishTurnPacket;
import packets.PlayerInitData;
import packets.ConnectionTerminatedPacket;
import packets.StructureBuiltPacket;
import settings.Settings;
import utils.DiceUtils;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map.Entry;

import catan.GamePhase;
import catan.player.PlayerBuildAction;
import catan.player.Team;
import log.Log;
import log.LogLevel;

public class Server implements Runnable, ServerEventListener {
	
	private Log log = new Log(this.getClass().getSimpleName(), LogLevel.ERROR);
	
	private Thread thread;
	
	private int port;
	
	private int numberOfExpectedPlayers;
	private int numberOfPlayingPlayers;
	
	private ServerSocket serverSocket;
	
	private boolean running = false;
	
	private int nextConnectionID = 0;
	private int connectionCount = 0;
	
	private ServerEventManager eventManager;
	
	private GamePhase phase;

	private List<Team> turnOrder = new ArrayList<Team>();
	private int currentPlayer;
	
	private List<PlayerBuildAction> initQueueActions = new ArrayList<PlayerBuildAction>();
	private List<Integer> initQueueConnectionIDs = new ArrayList<Integer>();
	
	public Server(int port, int numberOfExpectedPlayers) {
		this.port = port;
		this.numberOfExpectedPlayers = numberOfExpectedPlayers;
		eventManager = new ServerEventManager(this);
	}
	
	public boolean start() {
		try {
			phase = GamePhase.WAITING_FOR_PLAYERS;
			serverSocket = new ServerSocket(port);
			running = true;
			thread = new Thread(this);
			thread.start();
			System.out.println("[Server] Server has started on port: " + port);
		} catch(BindException e) {
			System.out.println("[Server] There is already a server being hosted on this port!");
			return false;
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void close() {
		System.out.println("[Server] Server is shutting down!");
		running = false;
		try {
			phase = GamePhase.POST_GAME;
			ConnectionHandler.shutdown();
			serverSocket.close();
			thread.interrupt();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initSocket(Socket socket) {
		ConnectionHandler.addConnection(socket, nextConnectionID, eventManager);
		nextConnectionID++;
	}
	
	private void sendPacketToAllPlayers(Packet packet) {
		boolean successful = false;
		while(!successful) {
			try {
				for(Integer key: ConnectionHandler.connections.keySet()) {
					ConnectionHandler.connections.get(key).sendObject(packet);
				}
				successful = true;
			} catch(ConcurrentModificationException e) {
				System.out.println("[Server] A player joined while packets were being sent to all players!");
			}
		}
	}
	
	private void sendPacketToAllOtherPlayers(Packet packet, int connectionID) {
		boolean successful = false;
		while(!successful) {
			try {
				for(Integer key: ConnectionHandler.connections.keySet()) {
					if(key == connectionID) {
						continue;
					}
					ConnectionHandler.connections.get(key).sendObject(packet);
				}
				successful = true;
			} catch(ConcurrentModificationException e) {
				System.out.println("[Server] A player joined while packets were being sent to all other players!");
			}
		}
		
	}
	
	private void startInitPhase() {
		System.out.println("[Server] Game is in init phase.");
		phase = GamePhase.INIT;
		sendPacketToAllPlayers(new GameInitPhasePacket());
		numberOfPlayingPlayers = numberOfExpectedPlayers;
		
		PlayerInitData[] turnOrder = new PlayerInitData[numberOfPlayingPlayers];
		int i = 0;
		for(Entry<Integer, ServerConnection> entry: ConnectionHandler.connections.entrySet()) {
			entry.getValue().setTeam(Team.values()[i]);
			turnOrder[i] = new PlayerInitData(entry.getValue().id, entry.getValue().getName(), Team.values()[i]);
			this.turnOrder.add(Team.values()[i]);
			i++;
		}
		GameInitPacket initPacket = new GameInitPacket(Settings.GENERATOR_SETTINGS, turnOrder);
		sendPacketToAllPlayers(initPacket);
		
		for(int j = 0; j < turnOrder.length; j++) {
			initQueueActions.add(PlayerBuildAction.BUILD_VILLAGE_START);
			initQueueConnectionIDs.add(turnOrder[j].id);
		}
		for(int j = turnOrder.length - 1; j >= 0; j--) {
			initQueueActions.add(PlayerBuildAction.BUILD_VILLAGE_START_WITH_RESOURCES);
			initQueueConnectionIDs.add(turnOrder[j].id);
		}
		for(int j = 0; j < turnOrder.length; j++) {
			initQueueActions.add(PlayerBuildAction.BUILD_ROAD_START);
			initQueueConnectionIDs.add(turnOrder[j].id);
		}
		for(int j = turnOrder.length - 1; j >= 0; j--) {
			initQueueActions.add(PlayerBuildAction.BUILD_ROAD_START);
			initQueueConnectionIDs.add(turnOrder[j].id);
		}
		doFirstItemOfInitQueue();
	}
	
	public void doFirstItemOfInitQueue() {
		PlayerBuildAction a = initQueueActions.remove(0);
		int connectionID = initQueueConnectionIDs.remove(0);
		ConnectionHandler.connections.get(connectionID).sendObject(new BuildStructurePacket(a));
	}
	
	public void startPlayingPhase() {
		System.out.println("[Server] Game is in playing phase.");
		phase = GamePhase.PLAYING;
		sendPacketToAllPlayers(new GamePlayingPhasePacket());
		currentPlayer = 0;
		int diceNumber = DiceUtils.generateRandomTwoDiceNumber();
		System.out.println("[Server] Dice number " + diceNumber + " rolled.");
		sendPacketToAllPlayers(new NextTurnPacket(diceNumber, turnOrder.get(currentPlayer)));
	}
	
	@Override
	public void run() {
		while(running) {
			try {
				Socket socket = serverSocket.accept();
				initSocket(socket);
			} catch(IOException e) {
				if(running) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void playerRequestConnection(ConnectionRequestedPacket packet, ServerConnection connection) {
		connection.sendObject(new ConnectionGrantedPacket(connection.id));
		connection.setName(packet.name);
		
		System.out.println("[Server] " + connection.getName() + " has connected.");

		connectionCount++;
		if(connectionCount == numberOfExpectedPlayers) {
			startInitPhase();
		}
	}

	@Override
	public void playerConnectionTerminated(ConnectionTerminatedPacket packet, ServerConnection connection) {
		if(!(phase == GamePhase.WAITING_FOR_PLAYERS || phase == GamePhase.POST_GAME)) {
			log.logMessage("Player should not have disconnected during a game!", LogLevel.WARN);
		}
		connectionCount--;
		System.out.println("[Server] " + connection.getName() + " has disconnected.");
		ConnectionHandler.removeConnection(packet.id);
		
		sendPacketToAllOtherPlayers(packet, connection.id);
		
		if(connectionCount == 0) {
			close();
		}
	}

	@Override
	public void structureBuilt(StructureBuiltPacket packet, ServerConnection connection) {
		System.out.println("[Server] Team " + packet.team + " has built a structure.");
		sendPacketToAllOtherPlayers(packet, connection.id);
	}

	@Override
	public void playerFinishTurn(PlayerFinishTurnPacket packet, ServerConnection connection) {
		if(phase == GamePhase.INIT) {
			if(initQueueActions.size() == 0) {
				startPlayingPhase();
			} else {
				sendPacketToAllPlayers(new NextTurnPacket(-1, ConnectionHandler.connections.get(initQueueConnectionIDs.get(0)).getTeam()));
				doFirstItemOfInitQueue();
				return;
			}
		} else {
			currentPlayer = (currentPlayer + 1) % numberOfPlayingPlayers;
			int diceNumber = DiceUtils.generateRandomTwoDiceNumber();
			System.out.println("[Server] Dice number " + diceNumber + " rolled.");
			sendPacketToAllPlayers(new NextTurnPacket(diceNumber, turnOrder.get(currentPlayer)));
		}
	}

}