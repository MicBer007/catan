package catan;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import debug.Log;
import debug.LogLevel;
import settings.Settings;
import catan.eventListeners.EventManager;
import catan.eventListeners.MouseListener;
import catan.graphics.objects.ObjectManager;
import catan.graphics.rendering.MasterRenderer;
import catan.player.PlayerManager;
import catan.window.Window;

public class CatanGame extends Canvas implements Runnable {
	
	private Log log = new Log(this.getClass().getSimpleName(), LogLevel.INFO);
	    
	private static final long serialVersionUID = 1550691097823471818L;
	    
	private Thread thread;
    private boolean running = false;
    
    private ObjectManager objectManager;
    
    private MasterRenderer renderer;
    
    private PlayerManager playerManager;
    
    private EventManager eventManager;
    
    public CatanGame() {
		log.info("Initializing...");
		
		objectManager = new ObjectManager();
		
		renderer = new MasterRenderer(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
		
		playerManager = new PlayerManager(objectManager);
		
		eventManager = new EventManager(new MouseListener(this));
		
        new Window("catan", Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT, this);

        this.addMouseListener(eventManager.getMouseListener());
        this.addMouseMotionListener(eventManager.getMouseListener());
    }
    
    public synchronized void start() {
    	log.info("Starting...");
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    
    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 10.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
            if(running){
                render();
            }
            frames++;
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                log.info("FPS: " + frames);
                frames = 0;
            }
        }
    	log.info("Stopping...");
        stop();
    }
    
    private void tick() {
        
    }
    
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        
        objectManager.render(g, renderer);
        playerManager.render(g);
        
        g.dispose();
        bs.show();
    }

	public ObjectManager getObjectManager() {
		return objectManager;
	}

	public MasterRenderer getRenderer() {
		return renderer;
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

}
