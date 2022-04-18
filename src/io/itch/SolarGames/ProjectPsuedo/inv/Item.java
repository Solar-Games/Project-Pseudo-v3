package io.itch.SolarGames.ProjectPsuedo.inv;

import io.itch.SolarGames.ProjectPsuedo.GameDisplay;
import io.itch.SolarGames.ProjectPsuedo.entities.Player;
import io.itch.SolarGames.ProjectPsuedo.gfx.*;
import io.itch.SolarGames.ProjectPsuedo.inv.crafts.CraftPick;
import me.sjplus.SJEngine.renderer.*;
import me.sjplus.SJEngine.util.Logger;

public class Item {

	

	// resources
	public static final Item wood = new Item("Wood", 1, 0xE1804E, true);
	public static final Item rock = new Item("Rock", 3, 0xBFBFBF, true);
	public static final Item iron_ore = new Item("Iron Ore", 3, 0xFFD1B2, true);
	public static final Item steel_ore = new Item("Steel Ore", 3, 0x788F9F, true);
	public static final Item iron_bar = new Item("Iron Bar", 2, 0xFFECDD, true);
	public static final Item steel_bar = new Item("Steel Bar", 2, 0xA7C1CC, true);
	public static final Item dragon_hide = new Item("Dragon Hide", 5, 0x52008C, true);

	// tools
	public static final Item hand = new Item("Hand", 0, 0xD98010, false);
	public static final Item wood_pick = new Item("Wood Pick", 4, 0xE1804E, false, CraftPick.wood_pick_craft);
	public static final Item stone_pick = new Item("Stone Pick", 4, 0xBFBFBF, false, CraftPick.stone_pick_craft);
	public static final Item wood_axe = new Item("Wood Axe", 5, 0xE1804E, false);
	public static final Item stone_axe = new Item("Stone Axe", 5, 0xBFBFBF, false);
	
	public static final int INV_TEXTURE = 0;
	public static final int STATIC_TEXTURE = 1;
	public static final int SWING_TEXTURE = 2;
	
	protected Sprite[] texture;
	protected int id;
	public String name;
	
	private Craft craft;
	
	public int stacking = 1;
	public final boolean canStack;
	
	public Item(String name, int id, int col, boolean canStack) {
		
		this.name = name;
		this.texture = new Sprite[] { Art.shiftColorsInSprite(GameDisplay.items.copySpriteFromSheet(1 + id % 8, 1 + (id / 8), 16, 16, 16), col),
				Art.shiftColorsInSprite(GameDisplay.items.copySpriteFromSheet(1 + id % 8, 3 + (id / 8), 16, 16, 16), col),
						Art.shiftColorsInSprite(GameDisplay.items.copySpriteFromSheet(1 + id % 8, 5 + (id / 8), 16, 16, 16), col) };
		this.id = id;
		this.canStack = canStack;
		
	}
	
	public Item(String name, int id, int col, boolean canStack, Craft craft) {
		
		this.name = name;
		this.texture = new Sprite[] { Art.shiftColorsInSprite(GameDisplay.items.copySpriteFromSheet(1 + id % 8, 1 + (id / 8), 16, 16, 16), col),
				Art.shiftColorsInSprite(GameDisplay.items.copySpriteFromSheet(1 + id % 8, 3 + (id / 8), 16, 16, 16), col),
						Art.shiftColorsInSprite(GameDisplay.items.copySpriteFromSheet(1 + id % 8, 5 + (id / 8), 16, 16, 16), col) };
		this.id = id;
		this.canStack = canStack;
		this.craft = craft;
		
	}
	
	public Sprite getTexture(int text) {
		
		return texture[text];
		
	}
	
	public void addToStack(int amount) {
		
		if (!canStack) return;
		
		stacking += amount;
		
	}
	
	public void removeFromStack(int amount) {
		
		if (!canStack) return;
		
		stacking += amount;
		
	}
	
	public boolean isZero() {
		
		return stacking == 0;
		
	}
	
	public boolean canCraft(Player player) {
		
		if (craft == null)
			return false;
		
		return craft.hasMaterials(player);
		
	}
	
	public void craft(Player player) {
		
		if (craft != null)
			craft.craft(player);
		
	}
	
}
