package io.itch.SolarGames.ProjectPsuedo.inv.crafts;

import io.itch.SolarGames.ProjectPsuedo.entities.Player;
import io.itch.SolarGames.ProjectPsuedo.inv.Craft;
import io.itch.SolarGames.ProjectPsuedo.inv.Item;

public class CraftPick extends Craft {

	public static final Craft wood_pick_craft = new CraftPick(Item.wood_pick, Item.wood);
	public static final Craft stone_pick_craft = new CraftPick(Item.stone_pick, Item.rock);

	private Item material;
	private Item pick;
	
	public CraftPick(Item pick, Item material) {
		
		this.material = material;
		this.pick = pick;
		
	}
	
	@Override
	public boolean hasMaterials(Player player) {
	
		if (material == Item.wood)
			return player.hasItem(Item.wood, 30);
			
		return player.hasItem(Item.wood, 10) && player.hasItem(material, 20);
	
	}

	@Override
	public void craft(Player player) {
		
		if (hasMaterials(player)) {
			
			player.giveItem(pick, 0);
			
			player.removeItem(Item.wood_pick, 10);
			player.removeItem(material, 20);
			
		}
			
		
	}

}
