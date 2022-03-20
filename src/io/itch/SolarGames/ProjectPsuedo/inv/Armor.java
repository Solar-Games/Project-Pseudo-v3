package io.itch.SolarGames.ProjectPsuedo.inv;

import io.itch.SolarGames.ProjectPsuedo.GameDisplay;
import io.itch.SolarGames.ProjectPsuedo.gfx.Art;
import io.itch.SolarGames.ProjectPsuedo.inv.armtype.*;
import me.sjplus.SJEngine.renderer.Sprite;

public class Armor {
	
	public static final Boots iron_boots = new Boots("Iron Boots", 0xD6B0A2, 0, 3.5f);
	public static final Boots dragon_boots = new Boots("Dragon Boots", 0x52008C, 1, 7.5f);
	
	public static final Chestplate iron_chestplate = new Chestplate("Iron Chestplate", 0xD6B0A2, 0, 4.75f);
	public static final Chestplate dragon_chestplate = new Chestplate("Dragon Chestplate", 0x52008C, 1, 10.5f);
	
	public static final Helmet iron_helmet = new Helmet("Iron Helmet", 0xD6B0A2, 0, 3.5f);
	public static final Helmet dragon_helmet = new Helmet("Dragon Helmet", 0x52008C, 1, 6.25f);
	
	private final float defense;
	private Sprite texture;
	public final int id;
	public final String name;
	
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
