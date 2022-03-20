package io.itch.SolarGames.ProjectPsuedo.entities;

import io.itch.SolarGames.ProjectPsuedo.GameDisplay;
import io.itch.SolarGames.ProjectPsuedo.inv.Item;
import io.itch.SolarGames.ProjectPsuedo.level.Level;
import me.sjplus.SJEngine.math.Vector3;

public class EntityIronRock extends EntityRock {

	public EntityIronRock(Vector3 pos, Level level) {
		super(pos, level, Item.iron_ore, 30, 0xFFD1B2);
	}

	@Override
	public void use(Entity e, Item item) {
		
		int damage = 0;
		
		if (item.equals(Item.stone_pick)) damage = 1;
		
		stage += damage;
		
		if (e instanceof Player && damage > 0)
			GameDisplay.rockSound.play();
		
		if (stage >= health) {
			
			level.removeEntity(this);
			
			if (e instanceof Player) {
				
				Player player = ((Player) e);
				
				player.giveItem(this.item, 5);
			
			}
		
		}
		
	}
	
}
