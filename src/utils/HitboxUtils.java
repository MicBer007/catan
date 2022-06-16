package utils;

import java.util.ArrayList;
import java.util.List;

import settings.Settings;
import ui.Hitbox;

public class HitboxUtils {
	
	public static List<Hitbox> generateHitboxes(int startX, int startY, int amount, int endX, int endY, int hitboxWidth, int hitboxHeight){
		int differenceX = endX - startX;
		int differenceY = endY - startY;
		int xBetween = differenceX / amount;
		int yBetween = differenceY / amount;
		List<Hitbox> hitboxes = new ArrayList<Hitbox>();
		for(int i = 0; i < amount; i++) {
			hitboxes.add(new Hitbox(startX + xBetween * i, startY + yBetween * i, hitboxWidth, hitboxHeight));
		}
		return hitboxes;
	}
	
	public static Hitbox centreHitboxAtY(int y, int width, int height) {
		int middleX = Settings.SCREEN_WIDTH / 2;
		return new Hitbox(middleX - width / 2, y - height / 2, width, height);
	}

}
