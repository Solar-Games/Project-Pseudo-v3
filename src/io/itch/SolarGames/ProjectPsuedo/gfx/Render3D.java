package io.itch.SolarGames.ProjectPsuedo.gfx;

import java.util.*;

import io.itch.SolarGames.ProjectPsuedo.*;
import io.itch.SolarGames.ProjectPsuedo.entities.Entity;
import io.itch.SolarGames.ProjectPsuedo.level.*;
import io.itch.SolarGames.ProjectPsuedo.level.blocks.*;
import me.sjplus.SJEngine.math.*;
import me.sjplus.SJEngine.renderer.*;
import me.sjplus.SJEngine.util.*;

public class Render3D extends GameRenderer {

	public double[] zBuffer;
	public double[] zWallBuffer;
	public float brightness;
	
	public Render3D(int width, int height) {
		
		super(width, height);
		
		zBuffer = new double[width*height];
		zWallBuffer = new double[width];
		
	}
	
	public void render(Screen screen, Camera camera, Level level) {

		if (level != null) {
		
			for (int i = 0; i < zBuffer.length; i++)
				zBuffer[i] = 10000;
			
			screen.pixels = pixels;
			
			renderFloor(camera, level);
			
			renderBlock16x3(2, 2, camera, GameDisplay.block_glyphs.get("test"));
			
			camera.addRendering(this);
			
			for (int i = 0; i < level.blocks.length; i++) {
	
				Block block = level.getBlock(i % level.getWidth(), i / level.getWidth());
				
				if (block == null)
					continue;
					
				if (!block.isWall())
					continue;
				
				renderWall((i % level.getWidth()), (i / level.getWidth()) + 1, camera, block);
					
			}
			
			for (Entity e : level.entities)
				if (e.getTexture() != null)
					renderSprite(level, camera, e);
			
			if (!Settings.choppyFading)
				fpostProcess(camera);
			else
				cpostProcess(camera);
			
			if (level.brightness != brightness)
				brightness = level.brightness;
		
		}
		
	}

	public void renderFloor(Camera camera, Level level) {
		
		Vector3 pos = camera.getPos();
		
 		double cos = Math.cos(Math.toRadians(camera.getRot().x));
		double sin = Math.sin(Math.toRadians(camera.getRot().x));
		
		for (int y = 0; y < height; y++) {
			
			double yD = (y - height / 2.0 + Math.sin(Math.toRadians(camera.getRot().y)) * 360) / Settings.fov;
			
			boolean floor = true;
			double zD = (12 + pos.y) / yD;
			
			if (yD < 0) {
				
				floor = false;
				zD = (12 - pos.y) / -yD;
				
			}
			
			for (int x = 0; x < width; x++) {

				if (zBuffer[x + y * width] <= zD) continue;
				
				double xD = (x - width / 2.0) / Settings.fov;
				xD *= zD;
				
				double xx = xD * cos + zD * sin + (24 + pos.x) * 1;
				double yy = zD * cos - xD * sin + (24 + pos.z) * 1;
				
				int xPix = (int) (xx);
				int yPix = (int) (yy);
				
				int tileX = xPix >> 4;
				int tileY = yPix >> 4;
	
				int w = level.getWidth();
				int h = level.getHeight();
				
				if (tileX >= w || tileY >= h || tileX < 0 || tileY < 0 || (!level.hasACeiling() && !floor) || xx < 0 || yy < 0)
					continue;
				
				Block block = level.getBlock(tileX, tileY);

				if (floor) {
				
					zBuffer[x + y * width] = zD;
					if (block.getFloorTexture() != null)
						pixels[x + y * width] = block.getFloorTexture().pixels[(xPix & 15) + (yPix & 15) * block.getFloorTexture().width];
				
				} else {
					
					zBuffer[x + y * width] = zD;
					if (block.getCeilTexture() != null)
						pixels[x + y * width] = block.getCeilTexture().pixels[(xPix & 15) + (yPix & 15) * block.getCeilTexture().width];
					
				}
				
			}
			
		}
		
	}
	
	public void renderSprite(double x, double y, double z, Camera camera, Sprite texture) {
		 
		Vector3 pos = camera.getPos();
		
		double cos = Math.cos(Math.toRadians(camera.getRot().x)),
				sin = Math.sin(Math.toRadians(camera.getRot().x));
		
		double pitchSin = Math.sin(Math.toRadians(camera.getRot().y));
		
		double xc = (x + pos.x) / 8 + sin / 8;
		double yc = (y + pos.y) / 8 + pitchSin * 1.25;
		double zc = (z - pos.z) / 8 - cos / 8;
		
		double xx = xc * cos + zc * sin;
		double yy = yc - pitchSin;
		double zz = zc * cos - xc * sin;
		
		if (zz < 0.1) return;
		
		double xPixel = (width / 2.0) - (xx / zz * Settings.fov);
		double yPixel = (yy / zz * (Settings.fov) + (height / 2.0 - pitchSin * 360));
		
		double xPixel0 = xPixel - height / zz;
		double xPixel1 = xPixel + height / zz;
		
		double yPixel0 = yPixel - height / zz;
		double yPixel1 = yPixel + height / zz;
		
		int xp0 = (int) Math.ceil(xPixel0);
		int yp0 = (int) Math.ceil(yPixel0);
		int xp1 = (int) Math.ceil(xPixel1);
		int yp1 = (int) Math.ceil(yPixel1);
		
		xp0 = Math.max(xp0, 0);
		yp0 = Math.max(yp0, 0);
		xp1 = Math.min(xp1, width);
		yp1 = Math.min(yp1, height);
		
		zz *= 8;
		
		for (int yp = yp0; yp < yp1; yp++) {
			
			double ypr = (yp - yPixel0) / (yPixel1 - yPixel0);
			int yt = (int) (ypr * texture.height);
			
			for (int xp = xp0; xp < xp1; xp++) {
				
				double xpr = (xp - xPixel0) / (xPixel1 - xPixel0);
				int xt = (int) (xpr * texture.width);
				
				if (zBuffer[xp + yp * width] > zz) {
					
					if (texture.alpha[xt + yt * texture.width] != 1)
						continue;
					
					pixels[xp + yp * width] = texture.pixels[xt + yt * texture.width];
					zBuffer[xp + yp * width] = zz;
					
				}
				
			}
			
		}
		
	}
	
	public void renderSprite(Level level, Camera cam, Entity e) {
		
		renderSprite(-e.getPos().x, e.getPos().y + 4, e.getPos().z, cam, e.getTexture());
		
	}
	
	public void renderWall(double x0, double z0, double x1, double z1, double y0, double y1, double y2, double y3, Camera cam, Sprite sprite) {
		
		Vector3 pos = cam.getPos();
		
		double cos = Math.cos(Math.toRadians(cam.getRot().x)),
				sin = Math.sin(Math.toRadians(cam.getRot().x));
		
		double pitchSin = Math.sin(Math.toRadians(cam.getRot().y));
		
		double xc0 = ((x0) + pos.x) / 8;
		double zc0 = ((z0) - pos.z) / 8;
		
		double xx0 = xc0 * cos + zc0 * sin;
		double u0 = ((y0) + pos.y) / 8;
		double l0 = ((y1) + pos.y) / 8;
		double zz0 = zc0 * cos - xc0 * sin;
		
		double xc1 = ((x1) + pos.x) / 8;
		double zc1 = ((z1) - pos.z) / 8;
		
		double xx1 = xc1 * cos + zc1 * sin;
		double u1 = ((y2) + pos.y) / 8;
		double l1 = ((y3) + pos.y) / 8;
		double zz1 = zc1 * cos - xc1 * sin;
		
		double zClip = 0.2;
		
		if (zz0 < zClip && zz1 < zClip) return;
		
		if (zz0 < zClip) {
			
			double p = (zClip - zz0) / (zz1 - zz0);
			
			zz0 = lerp(zz0, zz1, p);
			xx0 = lerp(xx0, xx1, p);
			
		}
		
		if (zz1 < zClip) {
			
			double p = (zClip - zz0) / (zz1 - zz0);
			
			zz1 = lerp(zz0, zz1, p);
			xx1 = lerp(xx0, xx1, p);
			
		}
		
		double xPixel0 = (width / 2.0) - (xx0 / zz0 * Settings.fov);
		double xPixel1 = (width / 2.0) - (xx1 / zz1 * Settings.fov);
		
		if (xPixel0 >= xPixel1) return;
		
		int xp0 = (int) Math.ceil(xPixel0);
		int xp1 = (int) Math.ceil(xPixel1);
		
		xp0 = (int) Math.max(xp0, 0);
		xp1 = (int) Math.min(xp1, width);
		
		double yCent = height / 2.0 - pitchSin * 360;
		
		double yPixel00 = (u0 / zz0 * Settings.fov + yCent);
		double yPixel01 = (l0 / zz0 * Settings.fov + yCent);
		double yPixel10 = (u1 / zz1 * Settings.fov + yCent);
		double yPixel11 = (l1 / zz1 * Settings.fov + yCent);
		
		double iz0 = 1 / zz0;
		double iz1 = 1 / zz1;
		
		double iza = iz1 - iz0;
		double iw = 1 / (xPixel1 - xPixel0);
		
		double ixt0 = 0 * iz0;
		double ixta = sprite.width * iz1 - ixt0;
		
		for (int x = xp0; x < xp1; x++) {
			
			double pr = (x - xPixel0) * iw;
			double iz = iz0 + iza * pr;
			
			int xTex = (int) ((ixt0 + ixta * pr) / iz);
			
			double yPixel0 = lerp(yPixel00, yPixel10, pr);
			double yPixel1 = lerp(yPixel01, yPixel11, pr);
			
			int yp0 = (int) Math.ceil(yPixel0);
			int yp1 = (int) Math.ceil(yPixel1);
			
			yp0 = (int) Math.max(yp0, 0);
			yp1 = (int) Math.min(yp1, height);
			
			double ih = 1 / (yPixel1 - yPixel0);
			
			for (int y = yp0; y < yp1; y++) {
				
				double pry = (y - yPixel0) * ih;
				
				int yTex = (int) (sprite.height * pry);
				
				if (sprite.alpha[xTex + yTex * sprite.width] != 1 || zBuffer[x + y * width] < 1 / iz * 8) continue;
				
				pixels[x + y * width] = sprite.pixels[xTex + yTex * sprite.width];
				zBuffer[x+y*width] = 1 / iz * 8;
				
			}
			
		}
		
	}
	
	public void renderWall(double x0, double z0, double x1, double z1, double y0, double y1, Camera cam, Sprite sprite) {
		
		renderWall(x0, z0, x1, z1, y0, y1, y0, y1, cam, sprite);
		
	}
	
	public void renderWall(double x0, double z0, double x1, double z1, Camera cam, Sprite sprite) {
		
		renderWall(x0, z0, x1, z1, -12, +12, cam, sprite);
		
	}
	
	public void renderPlane(int x, double y, int z, Camera camera, Sprite sprite) {
		
		Vector3 pos = camera.getPos();
		FVector2 rot = camera.getRot();
		
		double cos = Math.cos(Math.toRadians(rot.x)),
				sin = Math.sin(Math.toRadians(rot.x));
		
		for (int y1 = 0; y1 < height; y1++) {
			
			double yD = (y1 - height / 2.0 + Math.sin(Math.toRadians(rot.y)) * 360) / Settings.fov;
			
			boolean floor = true;
			double zD = (y + pos.y) / yD;
			
			if (yD < 0) {
				
				floor = false;
				zD = -zD;
				
			}
			
			for (int x1 = 0; x1 < width; x1++) {
				
				if (zBuffer[x1+y1*width] <= zD) continue;
				
				double xD = (x1 - width / 2.0) / Settings.fov;
				xD *= zD;
				
				double xx = xD * cos + zD * sin + (pos.x + 24);
				double zz = zD * cos - xD * sin + (pos.z + 24);
				
				int xPix = (int) (xx);
				int yPix = (int) (zz);
				
				int tX = xPix >> 4;
				int tY = yPix >> 4;
				
				if ((int) (tX) != x || (int) (tY) != z || zz < 0 || xx < 0 || !floor)
					continue;
				
				zBuffer[x1 + y1 * width] = zD;
				pixels[x1 + y1 * width] = sprite.pixels[(xPix & 15) + (yPix & 15) * sprite.width];
				
			}
			
		}
		
	}
	
	public void renderBlock16x3(double x, double z, Camera camera, BlockGlyph bg) {
		
		if (bg != null) {
			
			renderPlane((int) x, 2, (int) z, camera, bg.parts[0]);
			
			renderWall(8 - (x * 16),
					-16 + (z * 16) + 8,
					(16 + 8) - (x * 16),
					-16 + (z * 16) + 8,
					-4 + 6, +12, camera, bg.parts[1]);
			
			renderWall((16 + 8) - (x * 16),
					-16 + (z * 16) + 8,
					(16 + 8) - (x * 16),
					-16 + (z * 16) - 8,
					-4 + 6, +12, camera, bg.parts[2]);
			
			renderWall((-8 - (x * 16)) + 32,
					-16 + (z * 16) - 8,
					(-(16 + 8) - (x * 16)) + 32,
					-16 + (z * 16) - 8,
					-4 + 6, +12, camera, bg.parts[3]);
			
			renderWall((-(16 + 8) - (x * 16)) + 32,
					-16 + (z * 16) - 8,
					(-(16 + 8) - (x * 16)) + 32,
					-16 + (z * 16) + 8,
					-4 + 6, +12, camera, bg.parts[4]);
		
		}
		
	}
	
	public void renderWall(double x, double z, Camera camera, Sprite sprite) {
		
		if (sprite != null) {
		
			renderWall(8 - (x * 16),
					16 + (z * 16) - 24,
					(16 + 8) - (x * 16),
					16 + (z * 16) - 24,
					camera, sprite);
			
			renderWall((16 + 8) - (x * 16) + 32,
					-16 + (z * 16) - 8,
					(16 + 8) - (x * 16) + 32,
					-16 + (z * 16) - 24,
					camera, sprite);
			
			renderWall((-8 - (x * 16)) + 32,
					16 + (z * 16) - 8,
					(-(16 + 8) - (x * 16)) + 32,
					16 + (z * 16) - 8,
					camera, sprite);
			
			renderWall((-(16 + 8) - (x * 16)),
					-16 + (z * 16) - 24,
					(-(16 + 8) - (x * 16)),
					-16 + (z * 16) - 8,
					camera, sprite);
		
		}
		
	}
	
	public void renderWall(double x, double z, Camera camera, Block block) {
		
		if (block.getWallTexture() != null) {
		
			renderWall(8 - (x * 16),
					-16 + (z * 16) - 8,
					(16 + 8) - (x * 16),
					-16 + (z * 16) - 8,
					camera, block.getWallTexture());
			
			renderWall((16 + 8) - (x * 16),
					-16 + (z * 16) - 8,
					(16 + 8) - (x * 16),
					-16 + (z * 16) - 24,
					camera, block.getWallTexture());
			
			renderWall((-8 - (x * 16)) + 32,
					-16 + (z * 16) - 24,
					(-(16 + 8) - (x * 16)) + 32,
					-16 + (z * 16) - 24,
					camera, block.getWallTexture());
			
			renderWall((-(16 + 8) - (x * 16)) + 32,
					-16 + (z * 16) - 24,
					(-(16 + 8) - (x * 16)) + 32,
					-16 + (z * 16) - 8,
					camera, block.getWallTexture());
		
		}
		
	}
	
	public void fpostProcess(Camera cam) {
		
		for (int i = 0; i < zBuffer.length; i++) {
			
			if (zBuffer[i] > (696.4 / 8) * ((int) brightness & 0xff)/255) {
			
				int yFade = 255 - (int) Math.min(MathUtil.clamp(zBuffer[i] - (696.4 / 8) * ((int) brightness & 0xff)/255, 0, 32) * 8, 255);
				
				int pixel = pixels[i];
				
				int r = (pixel >> 16) & 0xff;
				int g = (pixel >> 8) & 0xff;
				int b = (pixel) & 0xff;
				
				r = r * yFade / 255;
				g = g * yFade / 255;
				b = b * yFade / 255;
				
				pixels[i] = r << 16 | g << 8 | b;
		
			}
			
			pixels[i] = Art.shiftColorInInt(pixels[i], (int) brightness);
			
		}
		
	}
	
	public void cpostProcess(Camera cam) {
		
		for (int i = 0; i < zBuffer.length; i++) {
			
			if (zBuffer[i] > (696.4 / 4) * ((int) brightness & 0xff)/255)
				pixels[i] = 0;
			
			pixels[i] = Art.shiftColorInInt(pixels[i], (int) brightness);
			
		}
		
	}
	
	public double lerp (double a0, double a1, double w) {
		
		return a0 + (a1 - a0) * w;
		
	}
	
}
