package io.itch.SolarGames.ProjectPsuedo.entities;

import java.util.*;

import io.itch.SolarGames.ProjectPsuedo.inv.*;
import io.itch.SolarGames.ProjectPsuedo.level.*;
import me.sjplus.SJEngine.math.*;
import me.sjplus.SJEngine.renderer.*;
import me.sjplus.SJEngine.sound.*;

public class EntityResource extends Entity {

	private Sound sound;
	private List<Drop> drops;
	private int damaged = 0;
	private final int health;
	
	@SafeVarargs
	public EntityResource(Vector3 pos, Level level, int health, int col, Sound sound, Sprite texture, Drop... drops) {

		super(pos, null, 8, level, texture, col);
		
		this.sound = sound;
		this.health = health;
		this.drops = Arrays.asList(drops);
		
	}

	public void use(Entity e, int damage) {
		
		if (damage <= 0)
			return;
		
		damaged += damage;
		System.out.println(health - damaged);
			
		sound.play();
		
		if (health - damaged <= 0) {

			if (e instanceof Player) {
				
				Player p = (Player) e;
				
				for (Drop drop : drops) {

					drop.giveItems(p);
					System.out.println("drops");
					
				}
				
			}
		
			level.removeEntity(this);
			
		}
			
	}
	
}
