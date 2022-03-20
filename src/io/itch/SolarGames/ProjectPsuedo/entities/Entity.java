package io.itch.SolarGames.ProjectPsuedo.entities;

import io.itch.SolarGames.ProjectPsuedo.gfx.Art;
import io.itch.SolarGames.ProjectPsuedo.inv.Item;
import io.itch.SolarGames.ProjectPsuedo.level.Level;
import me.sjplus.SJEngine.*;
import me.sjplus.SJEngine.input.*;
import me.sjplus.SJEngine.math.*;
import me.sjplus.SJEngine.physics.*;
import me.sjplus.SJEngine.renderer.*;

public abstract class Entity {

	private Sprite texture;
	public Vector3 pos;
	
	protected Level level;
	
	public float radius;
	
	public boolean unloaded = false;
	
	protected Entity(Vector3 pos, float radius, Level level, Sprite texture, int col) {
		
		this.pos = pos;
		this.level = level;
		this.radius = radius;
		this.texture = Art.shiftColorsInSprite(texture, col);
		
	}

	public void update(Level level) {
		
	}
	
	public Vector3 getPos() {
		
		return pos;
		
	}
	
	public Sprite getTexture() {
		
		return texture;
		
	}
	
	public void use(Entity e, Item item) {
		
	}
	
	public boolean collides(Entity e) {
		
		return e.pos.x + e.radius >  pos.x - radius &&
				e.pos.z + e.radius > pos.z - radius &&
				e.pos.x - e.radius < pos.x + radius &&
				e.pos.z - e.radius < pos.z + radius;
		
	}
	
	public void inEntity(Entity e) {
		
	}

}