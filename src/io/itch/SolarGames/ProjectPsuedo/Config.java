package io.itch.SolarGames.ProjectPsuedo;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import me.sjplus.SJEngine.input.Key;
import me.sjplus.SJEngine.renderer.Sprite;

public class Config {

	public static Config instance = new Config();
	public static File game;
	public static File keybinds;
	
	private Config() {

		try {
			game = new File(getClass().getResource("/game.sjm").toURI());
			keybinds = new File(getClass().getResource("/keybinds.sjm").toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	
	public static String getValueFromVar(String string, File file) throws IOException {
		
		Scanner scanner = new Scanner(file);
		
		while (scanner.hasNext()) {
			
			String str = scanner.nextLine();
			
			if (str.substring(0, str.indexOf("=")).equals(string)) {
			
				scanner.close();
				
				return str.substring(str.indexOf("=")+1);
			
			}
			
		}
		
		scanner.close();
		
		return null;
		
	}
	
	public static String[] getArrayFromVar(String string, File file) throws IOException {
		
		Scanner scanner = new Scanner(file);
		
		while (scanner.hasNext()) {
			
			String str = scanner.nextLine();
			
			if (str.substring(0, str.indexOf("=")).equals(string)) {
			
				return (str.substring(str.indexOf("=")+2, str.length() - 1)).split(",");
				
			}
			
		}
		
		scanner.close();
		
		return null;
		
	}
	
	public static void setUpGame() {
		
		try {
			
			GameDisplay.scale = Float.valueOf(getValueFromVar("d_scale", game));
			
			GameDisplay.width = Integer.valueOf(getValueFromVar("d_width", game));
			GameDisplay.height = Integer.valueOf(getValueFromVar("d_height", game));
			
			GameDisplay.width *= GameDisplay.scale;
			GameDisplay.height *= GameDisplay.scale;
			
			GameDisplay.sens = Float.valueOf(getValueFromVar("g_sens", game));
			GameDisplay.name = getValueFromVar("g_playername", game);
			Settings.choppyFading = Boolean.valueOf(getValueFromVar("g_choppyfading", game));
			Settings.debug_test_texture_path = getArrayFromVar("gdebug_test_texture_path", game);
			
			if (Config.class.getResource("/" + getValueFromVar("g_inventory_background", game)) != null)
				Settings.inv_bg = new Sprite(getValueFromVar("g_inventory_background", game));
			
			if (Config.class.getResource("/" + getValueFromVar("g_main_menu_background", game)) != null)
				Settings.mm_bg = new Sprite(getValueFromVar("g_main_menu_background", game));
			
			if (Config.class.getResource("/" + getValueFromVar("g_options_background", game)) != null)
				Settings.opt_bg = new Sprite(getValueFromVar("g_options_background", game));
			
			String[] forward = getArrayFromVar("forward", keybinds);
			Settings.keysID.put(Integer.valueOf(forward[0]), new Key(new int[] { Integer.valueOf(forward[1]) }, "Forward"));
			String[] lstrafe = getArrayFromVar("left_strafe", keybinds);
			Settings.keysID.put(Integer.valueOf(lstrafe[0]), new Key(new int[] { Integer.valueOf(lstrafe[1]) }, "Left Strafe"));
			String[] backward = getArrayFromVar("backwards", keybinds);
			Settings.keysID.put(Integer.valueOf(backward[0]), new Key(new int[] { Integer.valueOf(backward[1]) }, "Backwards"));
			String[] rstrafe = getArrayFromVar("right_strafe", keybinds);
			Settings.keysID.put(Integer.valueOf(rstrafe[0]), new Key(new int[] { Integer.valueOf(rstrafe[1]) }, "Right Strafe"));
			String[] inventory = getArrayFromVar("inventory", keybinds);
			Settings.keysID.put(Integer.valueOf(inventory[0]), new Key(new int[] { Integer.valueOf(inventory[1]) }, "Inventory"));
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
}
