package io.itch.SolarGames.ProjectPsuedo.level.blocks;

import io.itch.SolarGames.ProjectPsuedo.GameDisplay;
import io.itch.SolarGames.ProjectPsuedo.entities.Entity;
import io.itch.SolarGames.ProjectPsuedo.gfx.Art;
import me.sjplus.SJEngine.renderer.*;
import me.sjplus.SJEngine.util.*;

public class Block {
	
	protected int color;
	protected int floorTex;
	protected int wallTex;
	protected int ceilTex;
	
	protected boolean wall;
	
	protected boolean blocksEntities;
	
	protected Timer timer = new Timer();
	
	protected int frame = 0;
	protected float speed = 0;
	protected boolean animated = false;
	
	protected Sprite[] floorTexture;
	protected Sprite[] wallTexture;
	protected Sprite[] ceilTexture;
	
	public Block(int frames, float speed, boolean animated) {
		
		floorTexture = new Sprite[frames];
		wallTexture = new Sprite[frames];
		ceilTexture = new Sprite[frames];
		this.speed = speed;
		this.animated = animated;
		
	}
	
	public Block() {
		
		floorTexture = new Sprite[1];
		wallTexture = new Sprite[1];
		ceilTexture = new Sprite[1];
		this.animated = false;
		
	}
	
	public Sprite getFloorTexture() {
		
		if (floorTexture[frame] == null) {
			
			floorTexture[frame] = GameDisplay.tiles.copySpriteFromSheet(((floorTex + frame) % 8) + 1, ((floorTex + frame) / 8) + 1, 16, 16, 16);
			
			floorTexture[frame] = Art.shiftColorsInSprite(floorTexture[frame], color);
			
		}
			
		return floorTexture[frame];
		
	}
	
	public Sprite getWallTexture() {
		
		if (wallTexture[frame] == null) {
		
			wallTexture[frame] = GameDisplay.walls.copySpriteFromSheet(((wallTex + frame) % 8) + 1, ((wallTex + frame) / 8) + 1, 16, 24, 16, 24);
			
			wallTexture[frame] = Art.shiftColorsInSprite(wallTexture[frame], color);
			
		}
			
		return wallTexture[frame];
		
	}
	
	public Sprite getCeilTexture() {
		
		if (ceilTexture[frame] == null) {
		
			ceilTexture[frame] = GameDisplay.tiles.copySpriteFromSheet(((ceilTex + frame) % 8) + 1, ((ceilTex + frame) / 8) + 1, 16, 16, 16);
			
			ceilTexture[frame] = Art.shiftColorsInSprite(ceilTexture[frame], color);
			
		}
			
		return ceilTexture[frame];
		
	}

	public boolean blocks(Entity e) {
		
		return blocksEntities;
		
	}
	
	public double getBlockHeight() {
	
		return 0;
	
	}
	
	public double getWalkSpeed() {
		
		return 1/3.334f;
		
	}
	
	public double getFriction() {

		return 0.628;
		
	}
	
	public boolean isWall() {
		
		return wall;
		
	}
	
	public void onBlock(Entity e) {
		
	}
	
	public void update() {
		
		if (timer.hasTimeElapsed((long) (speed * 1000), true) && animated) {
			
			frame += 1;
			frame %= floorTexture.length;
			
		}
		
	}
	
}
