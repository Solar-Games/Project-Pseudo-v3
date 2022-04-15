package io.itch.SolarGames.ProjectPsuedo.level;

import java.util.*;

import io.itch.SolarGames.ProjectPsuedo.entities.*;
import io.itch.SolarGames.ProjectPsuedo.gfx.Camera;
import io.itch.SolarGames.ProjectPsuedo.level.blocks.*;
import me.sjplus.SJEngine.math.MathUtil;
import me.sjplus.SJEngine.math.Vector3;
import me.sjplus.SJEngine.renderer.*;
import me.sjplus.SJEngine.util.Logger;

public class Level {

	public Block[] blocks;
	public int[] data;
	
	private int id;
	
	private boolean hasCeiling = false;
	
	private int w;
	private int h;
	
	public float brightness;
	
	public List<Entity> entities = new ArrayList<>();
	
	public List<Entity> loadedEntities = new ArrayList<>(),
						unloadedEntities = new ArrayList<>();
	
	public static Map<Integer, Level> levels = new HashMap<>();
	
	public void create(int id, boolean hasCeiling, float brightness, int w, int h, int[] data) {

		this.hasCeiling = hasCeiling;
		
		this.w = w;
		this.h = h;
		
		this.id = id;
		
		this.brightness = brightness;
		
		blocks = new Block[w * h];
		this.data = data;
		
		for (int i = 0; i < w * h; i++) {
			
			blocks[i] = createBlock(i % w, i / h, data[i]);
			createEntity((i % w), (i / h), data[i]);
			
		}
		
	}
	
	public int getWidth() {
		
		return w;
		
	}
	
	public int getHeight() {
		
		return h;
		
	}
	
	public boolean hasACeiling() {
		
		return hasCeiling;
		
	}
 	
	public void addEntity(Entity e) {
		
		entities.add(e);
		
	}
	
	public void removeEntity(Entity e) {
		
		entities.remove(e);
		
	}
	
	public void updateLevel(Camera camera) {
		
		for (Entity e : entities) {
			
			e.update();
			
		}
		
		for (Block b : blocks) {
			
			b.update();
			
		}
		
	}
	
	public Entity getEntity(int x, int y) {
		
		for (Entity e : entities) {
			
			if ((int) e.getPos().x == x && (int) e.getPos().z == y)
				return e;
			
		}
		
		return null;
		
	}
	
	protected void createEntity(int x, int y, int col) {
		
		if (col == 0x5D9F00) addEntity(new EntityTree(new Vector3(x, 0, y), this));
		if (col == 0xBFF0BF) addEntity(new EntityStoneRock(new Vector3(x, 0, y), this));
		if (col == 0x353535) addEntity(new EntityStoneRock(new Vector3(x, 0, y), this));
		if (col == 0x555555) addEntity(new EntityIronRock(new Vector3(x, 0, y), this));
		
	}
	
	protected Block createBlock(int x, int y, int col) {
		
		if (col == 0x00ff00) return new GrassBlock();
		if (col == 0x5D9F00) return new GrassBlock();
		if (col == 0xBFF0BF) return new GrassBlock();
		if (col == 0x00B200) return new TunnelBlock(false);
		if (col == 0x353535) return new StoneBlock(false);
		if (col == 0x101010) return new TunnelBlock(true);
		if (col == 0x0045ef) return new WaterBlock();
		if (col == 0x909090) return new StoneBlock(false);
		if (col == 0x878787) return new StoneBlock(true);
		if (col == 0x555555) return new StoneBlock(false);
		if (col == 0x385089) return new UGWaterBlock();
		
		return new Block();
		
	}
	
	public Block getBlock(int x, int y) {
		
		return blocks[x + y * w];
		
	}
	
	public static Level findLevel(int i) {
		
		return levels.get(i);
		
	}
	
	public static void createLevel(int id, int[] data, int w, int h, boolean hasCeiling, float brightness) {
		
		Level level = new Level();
		
		level.create(id, hasCeiling, brightness, w, h, data);
		
		levels.put(id, level);
		
	}
	
}
