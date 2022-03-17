package io.itch.SolarGames.ProjectPsuedo.inv;

import io.itch.SolarGames.ProjectPsuedo.GameDisplay;
import io.itch.SolarGames.ProjectPsuedo.gfx.Art;
import io.itch.SolarGames.ProjectPsuedo.inv.armtype.*;
import me.sjplus.SJEngine.renderer.Sprite;

public class Armor {
	
	public static final Boots iron_boots = new Boots("Iron Boots", 0xD6B0A2, 0, 3.5f);
	
	public static final Chestplate iron_chestplate = new Chestplate("Iron Chestplate", 0xD6B0A2, 0, 6);
	
	public static final Helmet iron_helmet = new Helmet("Iron Helmet", 0xD6B0A2, 0, 3.5f);
	
	private final float defense;
	public Sprite texture;
	public int id;
	public String name;
	
	public Armor(String name, int col, int id, float defense) {
	
		this.name = name;
		
		this.texture = Art.shiftColorsInSprite(GameDisplay.armor.copySpriteFromSheet(1 + id % 8, 1 + id / 8, 16, 16, 16), col);
		
		this.id = id;
		
		this.defense = defense;
		
	}
	
	public float getDefense() {
		
		return defense;
		
	}
	
	public Sprite getTexture() {
		
		return texture;
		
	}

}
