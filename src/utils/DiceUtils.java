package utils;

import java.util.Random;

public class DiceUtils {
	
	private static Random random = new Random();
	
	public static int generateRandomTwoDiceNumber() {
		return random.nextInt(5) + random.nextInt(5) + 2;
	}

}
