package catan.window;

import java.awt.Dimension;

import javax.swing.JFrame;

import catan.CatanGame;

public class Window {
	
	public Window(String title, int width, int height, CatanGame game) {
		JFrame frame = new JFrame(title);
        
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();
	}

}
