package menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import settings.Settings;
import ui.Button;
import ui.Hitbox;
import ui.Panel;
import utils.HitboxUtils;

public class Menu {
	
	private List<Panel> pages = new ArrayList<Panel>();
	
	private int selectedPage;
	
	public Menu(Runnable play, Runnable join) {
		selectedPage = 0;
		Panel mainPage = new Panel(new Hitbox(0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT), new Color(153, 75, 19));
		mainPage.addComponent(new Button(HitboxUtils.centreHitboxAtY(260, 120, 40), "Create Game", new Color(130, 60, 10), play));
		mainPage.addComponent(new Button(HitboxUtils.centreHitboxAtY(320, 120, 40), "Join Game", new Color(130, 60, 10), join));
		pages.add(mainPage);
	}
	
	public void setPage(int page) {
		selectedPage = page;
	}
	
	public void render(Graphics g) {
		if(selectedPage == -1) {
			return;
		}
		pages.get(selectedPage).render(g);
	}

	public int getSelectedPage() {
		return selectedPage;
	}

	public void setSelectedPage(int selectedPage) {
		this.selectedPage = selectedPage;
	}
	
	public void mouseMoved(MouseEvent e) {
		for(Panel panel: pages) {
			panel.mouseMoved(e);
		}
	}
	
	public void mousePressed(MouseEvent e) {
		for(Panel panel: pages) {
			panel.mousePressed(e);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		for(Panel panel: pages) {
			panel.mouseReleased(e);
		}
	}

}
