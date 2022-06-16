package catan.window;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;

import main.Main;
import settings.Settings;

public class Window {
	
	public Window(String title, Main main) {
		JFrame frame = new JFrame(title);
        
        frame.setPreferredSize(new Dimension(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT + Settings.TOP_OF_SCREEN_OFFSET));
        frame.setMaximumSize(new Dimension(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT + Settings.TOP_OF_SCREEN_OFFSET));
        frame.setMinimumSize(new Dimension(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT + Settings.TOP_OF_SCREEN_OFFSET));
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(main);
        frame.setVisible(true);
        main.start();
	}
	
	public Window(String title, Main main, WindowAdapter windowListener) {
		JFrame frame = new JFrame(title);
        
        frame.setPreferredSize(new Dimension(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT + Settings.TOP_OF_SCREEN_OFFSET));
        frame.setMaximumSize(new Dimension(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT + Settings.TOP_OF_SCREEN_OFFSET));
        frame.setMinimumSize(new Dimension(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT + Settings.TOP_OF_SCREEN_OFFSET));
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(main);
        frame.addWindowListener(windowListener);
        frame.setVisible(true);
        main.start();
	}

}
