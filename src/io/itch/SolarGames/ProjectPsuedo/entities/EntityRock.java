package io.itch.SolarGames.ProjectPsuedo.entities;

import io.itch.SolarGames.ProjectPsuedo.GameDisplay;
import io.itch.SolarGames.ProjectPsuedo.inv.Item;
import io.itch.SolarGames.ProjectPsuedo.level.Level;
import me.sjplus.SJEngine.math.Vector3;
import me.sjplus.SJEngine.renderer.Sprite;

public class EntityRock extends Entity {

	protected int stage;
	protected Item item;
	protected int health;
	
	public EntityRock(Vector3 pos, Level level, Item item, int health, int color) {
	
		super(pos, 8, level, GameDisplay.entities.copySpriteFromSheet(1, 1, 16, 16, 16), color);
		this.item = item;
		this.health = health;
	
	}
	
	public void use(Entity e, Item item) {
		
		int damage = 0;
		
		if (item.equals(Item.wood_pick)) damage = 1;
		if (item.equals(Item.stone_pick)) damage = 2;
		
		stage += damage;
		
		if (stage >= health) {
			
			level.removeEntity(this);
			
			if (e instanceof Player) {
			
				Player player = ((Player) e);
				
				player.giveItem(this.item, 5);
			
			}
		
		}
	
	}

}
