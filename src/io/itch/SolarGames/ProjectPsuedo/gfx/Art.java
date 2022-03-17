package io.itch.SolarGames.ProjectPsuedo.gfx;

import me.sjplus.SJEngine.renderer.*;

public class Art {

	public static Sprite shiftColorsInSprite(Sprite tex, int col) {
		
		for (int i = 0; i < tex.width * tex.height; i++) {
			
			tex.pixels[i] = shiftColorInInt(tex.pixels[i], col);
			
		}

		return tex;
		
	}
	
	public static int shiftColorInInt(int i, int col) {
		
		int r0 = (i >> 16) & 0xff;
		int g0 = (i >> 8) & 0xff;
		int b0 = (i) & 0xff;
		
		int r1 = (col >> 16) & 0xff;
		int g1 = (col >> 8) & 0xff;
		int b1 = (col) & 0xff;
		
		int nr = r0 * r1 / 255;
		int ng = g0 * g1 / 255;
		int nb = b0 * b1 / 255;
		
		return nr << 16 | ng << 8 | nb;
		
	}
	
}
