package io.itch.SolarGames.ProjectPsuedo.level;

import java.util.Random;

import io.itch.SolarGames.ProjectPsuedo.level.blocks.TunnelBlock;

public class Generation {

	private static Random random = new Random();
	
	private static int seed = random.nextInt(1000000000);
	
	public static float generateNoise(int x, int z, float smoothness, float amp) {
		
		return getSmoothNoise(x, z, smoothness) * amp;
		
	}
	
	private static float getSmoothNoise(int x, int z, float smoothness) {
		
		float c = (getNoise(x + 1, z - 1) + getNoise(x + 1, z + 1) + getNoise(x + 1, z - 1) + getNoise(x - 1, z - 1)) / smoothness;
		float s = (getNoise(x + 1, z) + getNoise(x, z + 1) + getNoise(x - 1, z) + getNoise(x, z - 1)) / (smoothness / 2);
		float cntr = getNoise(x, z) / (smoothness / 4);
		
		return c + s + cntr;
		
	}
	
	private static float getNoise(int x, int z) {
		
		random.setSeed(x * 893894801 + z * 1293094013 + seed);
		
		return random.nextFloat() * 2f - 1f;
		
	}
	
	public static void generateOverworldLevel(int id, int width, int height, float smoothness, float amp) {
		
		int[] genData = new int[width * height];
		
		for (int x = 0; x < width; x++) {
			
			for (int y = 0; y < height; y++) {
				
				float gen = generateNoise(x, y, smoothness, amp);
				
				if (gen > -15) {
					
					genData[x+y*width] = 0x00ff00;
					
					if (gen > 35)
						genData[x+y*width] = 0x5D9F00;
					
					if (gen > 90)
						genData[x+y*width] = 0xBFF0BF;
					
					if (gen < -14.75)
						genData[x+y*width] = 0x00B200;
					
				}
				
				if (gen <= -15) {
					
					genData[x+y*width] = 0x0045ef;
					
				}
				
			}
			
		}

		Level.createLevel(id, genData, width, height, false, 0xffffff);
		
	}
	
	public static void generateCavernLevel(int id, Level overworld, int width, int height, float smoothness, float amp) {
		
		int[] genData = new int[width * height];
		
		for (int x = 0; x < width; x++) {
			
			for (int y = 0; y < height; y++) {
				
				float gen = generateNoise(x, y, smoothness, amp);
				
				if (gen > -75) {
					
					genData[x+y*width] = 0x909090;
					
					if (gen > 65)
						genData[x+y*width] = 0x353535;
					
					if (gen > 84)
						genData[x+y*width] = 0x878787;
					
					if (gen > 105)
						genData[x+y*width] = 0x555555;
					
					if (overworld.getBlock(x, y) instanceof TunnelBlock)
						genData[x+y*width] = 0x101010;
					
				}
				
				if (gen <= -75) {
					
					genData[x+y*width] = 0x385089;
					
				}
				
			}
			
		}

		Level.createLevel(id, genData, width, height, true, 0x909090);
		
	}
	
	public static void createTestLevel(int id) {
		
		int[] genData = new int[128*128];
		
		for (int i = 0; i < genData.length; i++) {
			
			genData[i] = 0x00ff00;
			
			if (i % 128 == 63 && i / 128 == 63)
				genData[i] = 0x00B200;
			
		}
		
		Level.createLevel(id, genData, 128, 128, false, 0xffffff);
		
	}
	
}
