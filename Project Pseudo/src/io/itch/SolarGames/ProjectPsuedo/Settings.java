package io.itch.SolarGames.ProjectPsuedo;

import java.io.FileWriter;
import java.io.IOException;

import me.sjplus.SJEngine.renderer.Sprite;

public class Settings {

	public static boolean choppyFading;
	public static String[] debug_test_texture_path;
	public static Sprite inv_bg;
	public static Sprite mm_bg;
	public static Sprite opt_bg;
	
	public static void setChoppyFading(boolean val) {
		
		choppyFading = val;
		
	}
	
}
