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
	public FVector2 rot;
	
	public Vector3 vel;
	
	protected Level level;
	
	public float radius;
	
	public boolean unloaded = false;
	
	protected Entity(Vector3 pos, FVector2 rot, float radius, Level level, Sprite texture, int col) {
		
		this.pos = pos;
		this.rot = rot;
		this.vel = new Vector3(0, 0, 0);
		this.level = level;
		this.radius = radius;
		
		if (texture != null)
			this.texture = Art.shiftColorsInSprite(texture, col);
		
	}

	public void update() {
		
		pos.add(vel);
		pos.set(MathUtil.clamp(pos.x, -24, level.getWidth() * 16 - 25), pos.y, MathUtil.clamp(pos.z, -24, level.getWidth() * 16 - 25));
	
	}
	
	public Vector3 getPos() {
		
		return pos;
		
	}
	
	public Sprite getTexture() {
		
		return texture;
		
	}
	
	public void move(float forward, float strafe, double speed) {
		
		float yaw = rot.x;
		
		double cos = Math.cos(Math.toRadians(yaw)),
				sin = Math.sin(Math.toRadians(yaw));
		
		vel.add(strafe * speed * cos + forward * speed * sin, 0, forward * speed * cos - strafe * speed * sin);
		
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