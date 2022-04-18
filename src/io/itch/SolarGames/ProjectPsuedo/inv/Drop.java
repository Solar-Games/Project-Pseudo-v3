package io.itch.SolarGames.ProjectPsuedo.inv;

import java.util.Random;

import io.itch.SolarGames.ProjectPsuedo.entities.Player;

public class Drop {

	Item item;
	int minValue, maxValue;
	public static Random r = new Random();
	
	public Drop(Item item, int minValue, int maxValue) {

		this.item = item;
		this.minValue = minValue;
		this.maxValue = maxValue;
		
	}

	public void giveItems(Player p) {
		
		int value = (int) (minValue + r.nextInt(maxValue - minValue));
		
		p.giveItem(item, value);
		
	}
	
}
