package client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import catan.CatanGame;
import catan.GamePhase;
import catan.player.Team;
import map.RoadType;
import map.SettlementType;
import packets.ConnectionGrantedPacket;
import packets.ConnectionRequestedPacket;
import packets.GameInitPacket;
import packets.GameInitPhasePacket;
import packets.GamePlayingPhasePacket;
import packets.NextTurnPacket;
import packets.Packet;
import packets.PlayerFinishTurnPacket;
import packets.BuildStructurePacket;
import packets.PlayerInitData;
import packets.ConnectionTerminatedPacket;
import packets.StructureBuiltPacket;

public class Client implements Runnable, ClientEventListener {
	
	private Thread thread;
	
	public GamePhase phase;
	
	private String host;
	private int port;

	private int id;
	private String name;
	public Team team;
	
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private boolean running = false;
	
	public List<Team> turnOrder = new ArrayList<Team>();
	public int currentPlayer;
	
	public int numberOfPlayers;
	
	private ClientEventManager eventManager;
	
	public Client(String host, int port, String name) {
		this.host = host;
		this.port = port;
		this.name = name;
		this.eventManager = new ClientEventManager(this);
	}
	
	public boolean connect() {
		try {
			socket = new Socket(host, port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			thread = new Thread(this);
			thread.start();
			ConnectionRequestedPacket packet = new ConnectionRequestedPacket(name);
			sendObject(packet);
		} catch(ConnectException e) {
			System.out.println("[Client] Unable to connect to the server.");
			return false;
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void disconnect() {
		if(!running) return;
		running = false;
		try {
			ConnectionTerminatedPacket packet = new ConnectionTerminatedPacket();
			packet.id = id;
			sendObject(packet);
			in.close();
			try {
				out.close();
			} catch(SocketException e) { }
			socket.close();
			thread.interrupt();
			System.out.println("[Client] You have disconnected from the server.");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendObject(Object packet) {
		try {
			out.writeObject(packet);
		}catch(IOException e) {
			if(running) {
				e.printStackTrace();
			}
		}
	}
	
	public void buildRoad(RoadType road, int index) {
		sendObject(new StructureBuiltPacket(road, index, team));
		if(phase == GamePhase.INIT) {
			sendObject(new PlayerFinishTurnPacket());
		}
	}
	
	public void buildSettlement(SettlementType settlement, int index) {
		sendObject(new StructureBuiltPacket(settlement, index, team));
		if(phase == GamePhase.INIT) {
			sendObject(new PlayerFinishTurnPacket());
		}
	}
	
	@Override
	public void run() {
		running = true;
		while(running) {
			try {
				Object data = in.readObject();
				eventManager.received(((Packet) data));
			} catch(EOFException e) {
				
			} catch(SocketException e) {
				disconnect();
			} catch(ClassNotFoundException | IOException e) {
				e.printStackTrace();
			} 
		}
	}

	@Override
	public void connectionGranted(ConnectionGrantedPacket packet) {
		System.out.println("[Client] You have successfully joined the server!");
		id = packet.id;
	}

	@Override
	public void playerTerminatedConnection(ConnectionTerminatedPacket packet) {
		ClientConnection connection = ConnectionHandler.connections.remove(packet.id);
		System.out.println("[Client] " + connection.name + " has disconnected.");
	}

	@Override
	public void gameInit(GameInitPacket packet) {
		numberOfPlayers = packet.turnOrder.length;
		for(PlayerInitData data: packet.turnOrder) {
			turnOrder.add(data.team);
			if(data.id == id) {
				team = data.team;
				continue;
			}
			ConnectionHandler.connections.put(data.id, new ClientConnection(data.id, data.name, data.team));
		}
		CatanGame.startGame(packet.mapGeneratorSetting);
	}

	@Override
	public void structureBuilt(StructureBuiltPacket packet) {
		if(packet.settlementType != null) {
			CatanGame.map.getIntersections().get(packet.index).setType(packet.settlementType, packet.team);
		}
		if(packet.roadType != null) {
			CatanGame.map.getRoads().get(packet.index).setRoad(packet.roadType, packet.team);
		}
	}

	@Override
	public void structureBuildCommand(BuildStructurePacket packet) {
		CatanGame.player.addItemToBuildList(packet.action);
	}

	@Override
	public void nextTurn(NextTurnPacket packet) {
		currentPlayer = turnOrder.indexOf(packet.teamToPlay);
	}

	@Override
	public void gameInPlayingPhaseEvent(GamePlayingPhasePacket packet) {
		phase = GamePhase.PLAYING;
		
	}

	@Override
	public void gameInInitPhaseEvent(GameInitPhasePacket packet) {
		phase = GamePhase.INIT;
	}

}