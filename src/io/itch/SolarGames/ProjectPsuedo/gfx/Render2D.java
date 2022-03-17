package io.itch.SolarGames.ProjectPsuedo.gfx;

import io.itch.SolarGames.ProjectPsuedo.inv.Item;
import io.itch.SolarGames.ProjectPsuedo.level.Level;
import io.itch.SolarGames.ProjectPsuedo.level.blocks.Block;
import me.sjplus.SJEngine.renderer.Render;
import me.sjplus.SJEngine.renderer.Screen;

public class Render2D extends Render {

	public Render2D(int width, int height) {
	
		super(width, height);
	
	}
	
	public void render(Screen screen, Camera camera, Level level) {
		
		renderFloor(screen, camera, level);
		
		screen.scaledDraw(Item.hand.getTexture(Item.INV_TEXTURE).createRenderFromSprite(), width/2 - 16, height/2 - 16, 2);
		
		screen.pixels = pixels;
		
	}
	
	public void renderFloor(Screen screen, Camera camera, Level level) {
		
		for (int y = 0; y < height; y++) {
			
			double yD = (y - height / 2.0) / 2;
			
			for (int x = 0; x < width; x++) {
				
				double cos = Math.cos(Math.toRadians(camera.rotation.x - 90)),
						sin = Math.sin(Math.toRadians(camera.rotation.x - 90));
				
				double xD = (x - width / 2.0) / 2;
				
				double xx = xD * cos + yD * sin + (camera.position.z + 24);
				double yy = yD * cos - xD * sin + (camera.position.x + 24);
				
				int cX = (int) xx;
				int cY = (int) yy;
				
				int tX = cX * 2 >> 4;
				int tY = cY * 2 >> 4;
				
				int xPix = cX;
				int yPix = cY;
				
				int w = level.getWidth();
				int h = level.getHeight();
				
				if (tX >= w || tY >= h || tX < 0 || tY < 0)
					continue;
				
				Block block = level.getBlock(tX, tY);
				
				pixels[x + y * width] = block.getFloorTexture().pixels[(xPix & 15) + (yPix & 15) * block.getFloorTexture().width];
				
			}
			
		}
		
	}
	
}
