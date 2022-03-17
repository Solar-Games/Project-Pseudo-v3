package io.itch.SolarGames.ProjectPsuedo.inv.armtype;

import io.itch.SolarGames.ProjectPsuedo.inv.Armor;

public class Helmet extends Armor {

	public Helmet(String name, int col, int id, float defense) {
	
		super(name, col, (id * 3), defense);

	}

}
