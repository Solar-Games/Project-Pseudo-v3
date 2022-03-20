package io.itch.SolarGames.ProjectPsuedo.gfx;

import io.itch.SolarGames.ProjectPsuedo.entities.Entity;
import io.itch.SolarGames.ProjectPsuedo.inv.Item;
import io.itch.SolarGames.ProjectPsuedo.level.Level;
import io.itch.SolarGames.ProjectPsuedo.level.blocks.Block;
import me.sjplus.SJEngine.renderer.Render;
import me.sjplus.SJEngine.renderer.Screen;
import me.sjplus.SJEngine.renderer.Sprite;

public class Render2D extends GameRenderer {

	public Render2D(int width, int height) {
	
		super(width, height);
	
	}
	
	public void render(Screen screen, Camera camera, Level level) {
		
		renderFloor(level, camera);
		
		screen.scaledDraw(Item.hand.getTexture(Item.INV_TEXTURE).createRenderFromSprite(), width/2 - 16, height/2 - 16, 2);
		
		screen.pixels = pixels;
		
	}
	
	public void renderFloor(Level level, Camera camera) {
		
		double cos = Math.cos(Math.toRadians(camera.rotation.x - 180)),
				sin = Math.sin(Math.toRadians(camera.rotation.x - 180));
		
		int playerPosX = (int) (24 + camera.position.x) >> 4;
		int playerPosZ = (int) (24 + camera.position.z) >> 4;
		
		for (int y = 0; y < height; y++) {
			
			double yD = (y - height / 2.0) / 4;
			
			double zD = Math.max(2 + camera.position.y/4 + (yD - 150) / camera.fov, 2) / 4;
			zD /= (yD + y)/2000;
			
			yD *= zD;
			
			for (int x = 0; x < width; x++) {
				
				double xD = (x - width / 2.0) / 4;
				xD *= zD;
				
				double xx = xD * cos + yD * sin + (24 + camera.position.x);
				double yy = yD * cos - xD * sin + (24 + camera.position.z);
				
				int cX = (int) xx;
				int cY = (int) yy;
				
				int tX = cX >> 4;
				int tY = cY >> 4;
				
				int xPix = cX;
				int yPix = cY;
				
				int w = level.getWidth();
				int h = level.getHeight();
				
				if (tX >= w || tY >= h || tX < 0 || tY < 0 || playerPosX > tX + 5 || playerPosZ > tY + 5 || playerPosX < tX - 5 || playerPosZ < tY - 5)
					continue;
				
				Block block = level.getBlock(tX, tY);
				
				pixels[x + y * width] = block.getFloorTexture().pixels[(xPix & 15) + (yPix & 15) * block.getFloorTexture().width];
				
			}
			
		}
		
	}
	
	public void renderSprite(double x, double y, double z, Camera camera, Sprite texture) {
		
		
		
	}
	
}
