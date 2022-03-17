package io.itch.SolarGames.ProjectPsuedo;

import io.itch.SolarGames.ProjectPsuedo.entities.Player;
import io.itch.SolarGames.ProjectPsuedo.gui.*;
import io.itch.SolarGames.ProjectPsuedo.inv.Item;
import io.itch.SolarGames.ProjectPsuedo.level.Level;
import me.sjplus.SJEngine.Display;
import me.sjplus.SJEngine.Game;
import me.sjplus.SJEngine.Game.LibraryIntro;
import me.sjplus.SJEngine.ScreenHandler;
import me.sjplus.SJEngine.renderer.*;
import me.sjplus.SJEngine.util.Logger;

public class GameDisplay {

	public static float scale;
	public static int width, height;
	
	public static float sens;
	
	public static String name;
	
	public static SpriteSheet armor = new SpriteSheet("armor.png");
	public static SpriteSheet tiles = new SpriteSheet("tiles.png");
	public static SpriteSheet entities = new SpriteSheet("entities.png");
	public static SpriteSheet items = new SpriteSheet("items.png");
	public static SpriteSheet uicmpnts = new SpriteSheet("uicomp.png");
	public static final FontRenderer smooth_font = new FontRenderer(new SpriteSheet("smooth-font.png"), 64, 64, -28, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789$,.?");
	public static Sprite[] test = new Sprite[8];
	
	public static ScreenState screenState;
	
	public static Player player;
	public static Level level;

	public static void main(String[] args) {

		Config.setUpGame();
		
		if (Settings.debug_test_texture_path != null)
			for (int i = 0; i < 8; i++)
				if (Settings.debug_test_texture_path.length > 0)
					if (GameDisplay.class.getResource("/" + Settings.debug_test_texture_path[0] + (i+1) + ".png") != null)
						test[i] = new Sprite(Settings.debug_test_texture_path[0] + (i+1) + ".png");
		
		Display d = new Display("Project Pseudo", width, height);
		d.setClientRates(60, 144);
		
		screenState = ScreenState.MAIN_MENU_STATE;
		
		GameState gs = new GameState(width, height);
		InventoryState is = new InventoryState(width, height, gs);
		OptionsState os = new OptionsState(width, height);
		MainMenuState mms = new MainMenuState(width, height, gs);
		
		player.giveItem(Item.wood_pick, 0);
		
		d.start();

		ScreenHandler.addScreen(gs);
		ScreenHandler.addScreen(is);
		ScreenHandler.addScreen(os);
		ScreenHandler.addScreen(mms);
		
	}
	
}
