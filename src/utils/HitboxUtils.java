package utils;

import java.util.ArrayList;
import java.util.List;

import catan.graphics.objects.ui.Hitbox;

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

}
