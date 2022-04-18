package io.itch.SolarGames.ProjectPsuedo.entities;

import io.itch.SolarGames.ProjectPsuedo.GameDisplay;
import io.itch.SolarGames.ProjectPsuedo.inv.Drop;
import io.itch.SolarGames.ProjectPsuedo.inv.Item;
import io.itch.SolarGames.ProjectPsuedo.level.Level;
import me.sjplus.SJEngine.math.Vector3;

public class EntityIronRock extends EntityResource {

	public EntityIronRock(Vector3 pos, Level level) {
		super(pos, level, 30, 0xFFD1B2, GameDisplay.rockSound, GameDisplay.entities.copySpriteFromSheet(1, 1, 16, 16, 16), new Drop(Item.iron_ore, 2, 5));
	}

	@Override
	public void use(Entity e, Item item) {
		
		int damage = 0;
		
		if (item.equals(Item.stone_pick)) damage = 1;
		
		super.use(e, damage);
		
	}
	
}
