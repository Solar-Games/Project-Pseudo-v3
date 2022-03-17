package io.itch.SolarGames.ProjectPsuedo.level.blocks;

import io.itch.SolarGames.ProjectPsuedo.GameDisplay;
import io.itch.SolarGames.ProjectPsuedo.gfx.Art;
import me.sjplus.SJEngine.renderer.Sprite;

public class UGWaterBlock extends WaterBlock {

	public UGWaterBlock() {
		
		this.ceilTex = 3;
		
	}
	
	public Sprite getCeilTexture() {
		
		if (ceilTexture == null) {
		
			ceilTexture = GameDisplay.tiles.copySpriteFromSheet((ceilTex % 8) + 1, (ceilTex / 8) + 1, 16, 16, 16);
			
			ceilTexture = Art.shiftColorsInSprite(ceilTexture, 0xBFBFBF);
			
		}
			
		return ceilTexture;
		
	}
	
}
