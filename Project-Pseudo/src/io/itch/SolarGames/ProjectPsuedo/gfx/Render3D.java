package io.itch.SolarGames.ProjectPsuedo.gfx;

import java.util.*;

import io.itch.SolarGames.ProjectPsuedo.*;
import io.itch.SolarGames.ProjectPsuedo.entities.Entity;
import io.itch.SolarGames.ProjectPsuedo.level.*;
import io.itch.SolarGames.ProjectPsuedo.level.blocks.*;
import me.sjplus.SJEngine.math.*;
import me.sjplus.SJEngine.renderer.*;
import me.sjplus.SJEngine.util.*;

public class Render3D extends Render {

	public double[] zBuffer;
	public double[] zWallBuffer;
	public float brightness;
	
	public Render3D(int width, int height) {
		
		super(width, height);
		
		zBuffer = new double[width*height];
		zWallBuffer = new double[width];
		
	}
	
	public void render(Screen screen, Camera camera, Level level) {

		for (int i = 0; i < zWallBuffer.length; i++)
			zWallBuffer[i] = 0;
		
		for (int i = 0; i < zBuffer.length; i++)
			zBuffer[i] = 10000;
		
		screen.pixels = pixels;
		
		renderFloor(camera, level);

		for (Entity e : level.entities)
			renderSprite(level, camera, e);
		
		renderWall(0, -25, 25, -7,camera, GameDisplay.test[0]);
		
		if (!Settings.choppyFading)
			postProcess(camera);
		
		if (level.brightness != brightness)
			brightness = level.brightness;
		
	}

	public void renderFloor(Camera camera, Level level) {
		
 		double cos = Math.cos(Math.toRadians(camera.rotation.x));
		double sin = Math.sin(Math.toRadians(camera.rotation.x));
		
		for (int y = 0; y < height; y++) {
			
			double yD = (y - height / 2.0 + Math.sin(Math.toRadians(camera.rotation.y)) * 360) / camera.fov;
			
			boolean floor = true;
			double zD = (12 + camera.position.y) / yD;
			
			if (yD < 0) {
				
				floor = false;
				zD = (12 - camera.position.y) / -yD;
				
			}
			
			for (int x = 0; x < width; x++) {

				if (zBuffer[x + y * width] <= zD) continue;
				
				double xD = (x - width / 2.0) / camera.fov;
				xD *= zD;
				
				double xx = xD * cos + zD * sin + (24 + camera.position.x) * 1;
				double yy = zD * cos - xD * sin + (24 + camera.position.z) * 1;
				
				int xPix = (int) (xx);
				int yPix = (int) (yy);
				
				int tileX = xPix >> 4;
				int tileY = yPix >> 4;
	
				int w = level.getWidth();
				int h = level.getHeight();
				
				if (tileX >= w || tileY >= h || tileX < 0 || tileY < 0 || (!level.hasACeiling() && !floor) || zD > 696.5 / 4)
					continue;
				
				Block block = level.getBlock(tileX, tileY);

				if (floor) {
				
					zBuffer[x + y * width] = zD;
					pixels[x + y * width] = Art.shiftColorInInt(block.getFloorTexture().pixels[(xPix & 15) + (yPix & 15) * block.getFloorTexture().width], (int) (brightness));
				
				} else {
					
					zBuffer[x + y * width] = zD;
					pixels[x + y * width] = Art.shiftColorInInt(block.getCeilTexture().pixels[(xPix & 15) + (yPix & 15) * block.getCeilTexture().width], (int) (brightness));
					
				}
				
			}
			
		}
		
	}
	
	public void renderSprite(double x, double y, double z, Camera camera, Sprite texture) {
		 
		double cos = Math.cos(Math.toRadians(camera.rotation.x)),
				sin = Math.sin(Math.toRadians(camera.rotation.x));
		
		double pitchSin = Math.sin(Math.toRadians(camera.rotation.y));
		
		double xc = (x + camera.position.x) / 8 + sin / 8;
		double yc = (y + camera.position.y) / 8 + pitchSin * 1.25;
		double zc = (z - camera.position.z) / 8 - cos / 8;
		
		double xx = xc * cos + zc * sin;
		double yy = yc - pitchSin;
		double zz = zc * cos - xc * sin;
		
		if (zz < 0.1) return;
		
		double xPixel = (width / 2.0) - (xx / zz * camera.fov);
		double yPixel = (yy / zz * (camera.fov) + (height / 2.0 - pitchSin * 360));
		
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
		
		zz *= 4;
		
		for (int yp = yp0; yp < yp1; yp++) {
			
			double ypr = (yp - yPixel0) / (yPixel1 - yPixel0);
			int yt = (int) (ypr * texture.height);
			
			for (int xp = xp0; xp < xp1; xp++) {
				
				double xpr = (xp - xPixel0) / (xPixel1 - xPixel0);
				int xt = (int) (xpr * texture.width);
				
				if (zBuffer[xp + yp * width] > zz) {
					
					if (texture.alpha[xt + yt * texture.width] != 1 || zz / 4 > (696.5) / 32)
						continue;
					
					pixels[xp + yp * width] = Art.shiftColorInInt(texture.pixels[xt + yt * texture.width], (int) (brightness));
					zBuffer[xp + yp * width] = zz;
					
				}
				
			}
			
		}
		
	}
	
	public void renderSprite(Level level, Camera cam, Entity e) {
		
		renderSprite(((-e.getPos().x * 16)) + 8 / level.getWidth() * 2 + 16, e.getPos().y + 4, ((e.getPos().z * 16)) - 16, cam, e.getTexture());
		
	}
	
	public void renderWall(double x0, double z0, double x1, double z1, Camera cam, Sprite sprite) {
		
		double cos = Math.cos(Math.toRadians(cam.rotation.x)),
				sin = Math.sin(Math.toRadians(cam.rotation.x));
		
		double pitchSin = Math.sin(Math.toRadians(cam.rotation.y));
		
		double xc0 = ((x0) + cam.position.x) / 8 + sin * 0.016;
		double zc0 = ((z0) - cam.position.z) / 8 - cos * 0.016;
		
		double xx0 = xc0 * cos + zc0 * sin;
		double u0 = ((-0.5 * 33) + cam.position.y) / 8;
		double l0 = ((+0.5 * 32) + cam.position.y) / 8;
		double zz0 = zc0 * cos - xc0 * sin;
		
		double xc1 = ((x1) + cam.position.x) / 8 + sin * 0.016;
		double zc1 = ((z1) - cam.position.z) / 8 - cos * 0.016;
		
		double xx1 = xc1 * cos + zc1 * sin;
		double u1 = ((-0.5 * 24) + cam.position.y) / 8;
		double l1 = ((+0.5 * 24) + cam.position.y) / 8;
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
		
		double xPixel0 = (width / 2.0) - (xx0 / zz0 * cam.fov);
		double xPixel1 = (width / 2.0) - (xx1 / zz1 * cam.fov);
		
		if (xPixel0 >= xPixel1) return;
		
		int xp0 = (int) Math.ceil(xPixel0);
		int xp1 = (int) Math.ceil(xPixel1);
		
		xp0 = (int) Math.max(xp0, 0);
		xp1 = (int) Math.min(xp1, width);
		
		double yCent = height / 2.0 - pitchSin * 360;
		
		double yPixel00 = (u0 / zz0 * cam.fov + yCent);
		double yPixel01 = (l0 / zz0 * cam.fov + yCent);
		double yPixel10 = (u1 / zz1 * cam.fov + yCent);
		double yPixel11 = (l1 / zz1 * cam.fov + yCent);
		
		double iz0 = 1 / zz0;
		double iz1 = 1 / zz1;
		
		double iza = iz1 - iz0;
		double iw = 1 / (xPixel1 - xPixel0);
		
		double ixt0 = 0 * iz0;
		double ixta = (sprite.width * iz1) - ixt0;
		
		for (int x = xp0; x < xp1; x++) {
			
			double pr = (x - xPixel0) * iw;
			double iz = iz0 + iza * pr;
			
			if (zWallBuffer[x] > iz && iz < (696.5) / 4) return;
			zWallBuffer[x] = iz;
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
				
				if (sprite.alpha[xTex + yTex * sprite.width] != 0)
					pixels[x+y*width] = sprite.pixels[xTex + yTex * sprite.width];
				
				zBuffer[x+y*width] = 1 / iz * 4;
				
			}
			
		}
		
	}
	
	public void postProcess(Camera cam) {
		
		for (int i = 0; i < zBuffer.length; i++) {
			
			double xx = (i % width - width / 2.0) / width;
			double yy = (i / height - height / 2.0 + Math.sin(Math.toRadians(cam.rotation.y - 30)) * 360);
			
			int yFade = (int) MathUtil.clamp(0 + yy + xx, 0, 255);
			
			int pixel = pixels[i];
			
			int r = (pixel >> 16) & 0xff;
			int g = (pixel >> 8) & 0xff;
			int b = (pixel) & 0xff;
			
			r = r * yFade / 255;
			g = g * yFade / 255;
			b = b * yFade / 255;
			
			pixels[i] = r << 16 | g << 8 | b;
			
		}
		
	}
	
	public double lerp (double a0, double a1, double w) {
		
		return a0 + (a1 - a0) * w;
		
	}
	
}