package main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import catan.CatanGame;
import catan.window.Window;
import log.Log;
import log.LogLevel;

public class Main extends Canvas implements Runnable, MouseListener, MouseMotionListener {
	
	private Log log = new Log(this.getClass().getSimpleName(), LogLevel.ERROR);
	    
	private static final long serialVersionUID = 1550691097823471818L;
	    
	private Thread thread;
    private boolean running = false;
    
    public static void main(String[] args) {
    	new Main();
    }
    
    public Main() {
		log.info("Initializing...", 8);
        new Window("catan", this, new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent evt) {
                running = false;
            }
        });
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    public synchronized void start() {
    	log.info("Starting...", 10);
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    
    public synchronized void stop() {
    	log.info("Stopping...", 10);
    	CatanGame.stop();
        running = false;
        try {
            thread.interrupt();
        } catch(Exception e){
            e.printStackTrace();
        }
        System.exit(0);
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
        
        CatanGame.render(g);
        
        g.dispose();
        bs.show();
    }

	@Override
	public void mouseMoved(MouseEvent e) {
		CatanGame.mouseMoved(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		CatanGame.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		CatanGame.mouseReleased(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mouseDragged(MouseEvent e) { }
    
    

}
