package io.itch.SolarGames.ProjectPsuedo.entities;

import io.itch.SolarGames.ProjectPsuedo.GameDisplay;
import io.itch.SolarGames.ProjectPsuedo.inv.*;
import io.itch.SolarGames.ProjectPsuedo.level.Level;
import me.sjplus.SJEngine.math.Vector3;
import me.sjplus.SJEngine.sound.Sound;

public class EntityStoneRock extends EntityResource {

	public EntityStoneRock(Vector3 pos, Level level) {
	
		super(pos, level, 20, 0xBFBFBF, GameDisplay.rockSound, GameDisplay.entities.copySpriteFromSheet(1, 1, 16, 16, 16), new Drop(Item.rock, 3, 5));

	}

	@Override
	public void use(Entity e, Item item) {
		
		int damage = 0;
		
		if (item.equals(Item.wood_pick)) damage = 1;
		if (item.equals(Item.stone_pick)) damage = 2;
		
		super.use(e, damage);
		
	}
	
}
