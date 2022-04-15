package io.itch.SolarGames.ProjectPsuedo.level.blocks;

import io.itch.SolarGames.ProjectPsuedo.GameDisplay;
import io.itch.SolarGames.ProjectPsuedo.gfx.Art;
import me.sjplus.SJEngine.renderer.Sprite;

public class UGWaterBlock extends WaterBlock {

	public UGWaterBlock() {
		
		this.ceilTex = 5;
		
	}
	
	public Sprite getCeilTexture() {
		
		if (ceilTexture == null) {
		
			ceilTexture[0] = GameDisplay.tiles.copySpriteFromSheet((ceilTex % 8) + 1, (ceilTex / 8) + 1, 16, 16, 16);
			
			ceilTexture[0] = Art.shiftColorsInSprite(ceilTexture[0], 0xBFBFBF);
			
		}
			
		return ceilTexture[0];
		
	}
	
}
