package io.itch.SolarGames.ProjectPsuedo.entities;

import io.itch.SolarGames.ProjectPsuedo.GameDisplay;
import io.itch.SolarGames.ProjectPsuedo.gui.GameState;
import io.itch.SolarGames.ProjectPsuedo.inv.Drop;
import io.itch.SolarGames.ProjectPsuedo.inv.Item;
import io.itch.SolarGames.ProjectPsuedo.level.Level;
import me.sjplus.SJEngine.math.FVector2;
import me.sjplus.SJEngine.math.Vector3;
import me.sjplus.SJEngine.renderer.Sprite;
import me.sjplus.SJEngine.util.Logger;

public class EntityTree extends EntityResource {

	private int stage;
	
	public EntityTree(Vector3 pos, Level level) {
	
		super(pos, level, 15, 0x5D9F00, GameDisplay.woodSound, GameDisplay.entities.copySpriteFromSheet(2, 1, 16, 16, 16), new Drop(Item.wood, 1, 5));

	}
	
	public void use(Entity e, Item item) {
		
		int damage = 0;
		
		if (item.equals(Item.hand)) damage = 1;
		
		super.use(e, damage);
			
	}
	
}
