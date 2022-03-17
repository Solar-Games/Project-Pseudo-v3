package io.itch.SolarGames.ProjectPsuedo;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import me.sjplus.SJEngine.renderer.Sprite;

public class Config {

	public static Config instance = new Config();
	public static File config;
	
	private Config() {

		try {
			config = new File(getClass().getResource("/game.sjm").toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	
	public static String getValueFromVar(String string) throws IOException {
		
		Scanner scanner = new Scanner(config);
		
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
	
	public static String[] getArrayFromVar(String string) throws IOException {
		
		Scanner scanner = new Scanner(config);
		
		while (scanner.hasNext()) {
			
			String str = scanner.nextLine();
			
			if (str.substring(0, str.indexOf("=")).equals(string)) {
			
				Scanner arrSCN = new Scanner(str.substring(str.indexOf("=")+2, str.length() - 1));
				List<String> array = new ArrayList<>();
				
				while (arrSCN.hasNext()) {
					
					String data = arrSCN.next();
					
					array.add(data);
					
				}
				
				scanner.close();
				arrSCN.close();
				
				String[] strArr = new String[array.size()];
					
				return array.toArray(strArr);
				
			}
			
		}
		
		scanner.close();
		
		return null;
		
	}
	
	public static void setUpGame() {
		
		try {
			
			GameDisplay.scale = Float.valueOf(getValueFromVar("d_scale"));
			
			GameDisplay.width = Integer.valueOf(getValueFromVar("d_width"));
			GameDisplay.height = Integer.valueOf(getValueFromVar("d_height"));
			
			GameDisplay.width *= GameDisplay.scale;
			GameDisplay.height *= GameDisplay.scale;
			
			GameDisplay.sens = Float.valueOf(getValueFromVar("g_sens"));
			GameDisplay.name = getValueFromVar("g_playername");
			Settings.choppyFading = Boolean.valueOf(getValueFromVar("g_choppyfading"));
			Settings.debug_test_texture_path = getArrayFromVar("gdebug_test_texture_path");
			
			if (Config.class.getResource("/" + getValueFromVar("g_inventory_background")) != null)
				Settings.inv_bg = new Sprite(getValueFromVar("g_inventory_background"));
			
			if (Config.class.getResource("/" + getValueFromVar("g_main_menu_background")) != null)
				Settings.mm_bg = new Sprite(getValueFromVar("g_main_menu_background"));
			
			if (Config.class.getResource("/" + getValueFromVar("g_options_background")) != null)
				Settings.opt_bg = new Sprite(getValueFromVar("g_options_background"));
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
}
