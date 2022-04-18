package io.itch.SolarGames.ProjectPsuedo.gfx;

import me.sjplus.SJEngine.renderer.*;

public class BlockGlyph {

	public static final SpriteSheet blocks = new SpriteSheet("block.png");
	
	public Sprite[] parts;
	
	public BlockGlyph(int x, int y, int color) {

		parts = new Sprite[5];
		
		parts[0] = Art.shiftColorsInSprite(blocks.copySpriteFromSheet(((x * 64) + 1 * 16) + 1, (y * 28) + 1, 16, 16, 1), color);
		
		for (int i = 0; i < 4; i++) {
			
			parts[1 + i] = Art.shiftColorsInSprite(blocks.copySpriteFromSheet(((x * 64) + i * 16) + 1, ((y * 28) + 16) + 1, 16, 12, 1), color);
			
		}
		
	}

}
