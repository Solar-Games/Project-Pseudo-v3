package io.itch.SolarGames.ProjectPsuedo.inv.armtype;

import io.itch.SolarGames.ProjectPsuedo.inv.Armor;

public class Boots extends Armor {

	public Boots(String name, int col, int id, float defense) {
	
		super(name, col, (id * 3) + 2, defense);
	
	}

}
