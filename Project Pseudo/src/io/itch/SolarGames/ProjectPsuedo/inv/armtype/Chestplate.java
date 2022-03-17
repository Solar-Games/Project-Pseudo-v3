package io.itch.SolarGames.ProjectPsuedo.inv.armtype;

import io.itch.SolarGames.ProjectPsuedo.inv.Armor;

public class Chestplate extends Armor {

	public Chestplate(String name, int col, int id, float defense) {
	
		super(name, col, (id * 3) + 1, defense);

	}

}
