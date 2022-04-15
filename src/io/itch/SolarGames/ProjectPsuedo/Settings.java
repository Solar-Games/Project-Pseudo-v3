package io.itch.SolarGames.ProjectPsuedo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import me.sjplus.SJEngine.Display;
import me.sjplus.SJEngine.input.Key;
import me.sjplus.SJEngine.renderer.Sprite;

public class Settings {

	public static boolean choppyFading;
	public static String[] debug_test_texture_path;
	public static Sprite inv_bg;
	public static Sprite mm_bg;
	public static Sprite opt_bg;
	public static float fov = GameDisplay.height / (135/100);
	public static final Map<Integer, Key> keysID = new HashMap<>();
	
	public static Key getKey(int id) {
	
		return keysID.get(id);
	
	}
	
	public static float[] movement() {

		float forward = (getKey(0).pressed && !getKey(2).pressed? 1.0f : (!getKey(0).pressed && getKey(2).pressed? -1.0f : 0.0f));
		float strafe = -(getKey(1).pressed && !getKey(3).pressed? 1.0f : (!getKey(1).pressed && getKey(3).pressed? -1.0f : 0.0f));
		
		return new float[] { forward, strafe };
		
	}
	
	public static void setChoppyFading(boolean val) {
		
		choppyFading = val;
		
	}
	
}
