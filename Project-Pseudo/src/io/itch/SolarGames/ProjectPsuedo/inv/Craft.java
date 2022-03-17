package io.itch.SolarGames.ProjectPsuedo.inv;

import io.itch.SolarGames.ProjectPsuedo.entities.Player;

public abstract class Craft {

	public abstract boolean hasMaterials(Player player);
	
	public abstract void craft(Player player);

}
