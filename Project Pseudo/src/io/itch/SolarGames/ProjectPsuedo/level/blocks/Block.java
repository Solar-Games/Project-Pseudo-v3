package io.itch.SolarGames.ProjectPsuedo.level.blocks;

import io.itch.SolarGames.ProjectPsuedo.GameDisplay;
import io.itch.SolarGames.ProjectPsuedo.entities.Entity;
import io.itch.SolarGames.ProjectPsuedo.gfx.Art;
import me.sjplus.SJEngine.renderer.*;

public class Block {
	
	protected int color;
	protected int floorTex;
	protected int wallTex;
	protected int ceilTex;
	
	protected boolean wall;
	
	protected boolean blocksEntities;
	
	protected Sprite floorTexture;
	protected Sprite wallTexture;
	protected Sprite ceilTexture;
	
	public Sprite getFloorTexture() {
		
		if (floorTexture == null) {
		
			floorTexture = GameDisplay.tiles.copySpriteFromSheet((floorTex % 8) + 1, (floorTex / 8) + 1, 16, 16, 16);
			
			floorTexture = Art.shiftColorsInSprite(floorTexture, color);
			
		}
			
		return floorTexture;
		
	}
	
	public Sprite getCeilTexture() {
		
		if (ceilTexture == null) {
		
			ceilTexture = GameDisplay.tiles.copySpriteFromSheet((ceilTex % 8) + 1, (ceilTex / 8) + 1, 16, 16, 16);
			
			ceilTexture = Art.shiftColorsInSprite(ceilTexture, color);
			
		}
			
		return ceilTexture;
		
	}

	public boolean blocks(Entity e) {
		
		return blocksEntities;
		
	}
	
	public double getBlockHeight() {
	
		return 0;
	
	}

	public double getWalkSpeed() {
		
		return 1;
		
	}
	
	public boolean isWall() {
		
		return wall;
		
	}
	
	public void onBlock(Entity e) {
		
	}
	
}
