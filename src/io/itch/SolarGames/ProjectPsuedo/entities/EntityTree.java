package io.itch.SolarGames.ProjectPsuedo.entities;

import io.itch.SolarGames.ProjectPsuedo.GameDisplay;
import io.itch.SolarGames.ProjectPsuedo.gui.GameState;
import io.itch.SolarGames.ProjectPsuedo.inv.Item;
import io.itch.SolarGames.ProjectPsuedo.level.Level;
import me.sjplus.SJEngine.math.FVector2;
import me.sjplus.SJEngine.math.Vector3;
import me.sjplus.SJEngine.renderer.Sprite;
import me.sjplus.SJEngine.util.Logger;

public class EntityTree extends Entity {

	private int stage;
	
	public EntityTree(Vector3 pos, Level level) {
	
		super(pos, 8, level, GameDisplay.entities.copySpriteFromSheet(2, 1, 16, 16, 16), 0x5D9F00);

	}
	
	public void use(Entity e, Item item) {
		
		int damage = 0;
		
		if (item.equals(Item.hand)) damage = 1;
		
		stage += damage;
		
		if (e instanceof Player && damage > 0)
			GameDisplay.woodSound.play();
		
		if (stage >= 20) {
			
			level.removeEntity(this);
			
			if (e instanceof Player) {
			
				Player player = ((Player) e);
				
				player.giveItem(Item.wood, 5);
				
			}
			
		}
			
	}
	
}
