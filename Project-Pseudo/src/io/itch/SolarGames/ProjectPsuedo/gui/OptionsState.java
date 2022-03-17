package io.itch.SolarGames.ProjectPsuedo.gui;

import io.itch.SolarGames.ProjectPsuedo.GameDisplay;
import io.itch.SolarGames.ProjectPsuedo.Settings;
import io.itch.SolarGames.ProjectPsuedo.gui.comp.Button;
import me.sjplus.SJEngine.Game;
import me.sjplus.SJEngine.SystemFont;
import me.sjplus.SJEngine.input.Keyboard;
import me.sjplus.SJEngine.input.Mouse;
import me.sjplus.SJEngine.math.Vector2;
import me.sjplus.SJEngine.renderer.Screen;

public class OptionsState extends Screen {

	private Button quit;

	public OptionsState(int width, int height) {
	
		super(width, height);
	
		quit = new Button(new Vector2(48, 48), 256 * 1.25, 96);
		
	}

	@Override
	public void update(Game arg0, Mouse arg1, Keyboard arg2) {
		
		quit.update(arg1);
		
		if (quit.pressed) {
			
			GameDisplay.screenState = ScreenState.MAIN_MENU_STATE;
			quit.pressed = false;
			
		}
		
	}
	
	public void render() {
		
		if (Settings.opt_bg != null)
			this.draw(Settings.opt_bg.createRenderFromSprite(), (width - 1080)/2, (height - 720)/2);
		
		this.multiplyPixelColors(256/3, 256/3, 256/3);
		
		for (int x = (int) quit.pos.x; x < quit.pos.x + quit.width; x++) {
			
			for (int y = (int) quit.pos.y; y < quit.pos.y + quit.height; y++) {
				
				int col = pixels[x+y*width];
				
				int r = (col >> 16) & 0xff;
				int g = (col >> 8) & 0xff;
				int b = (col) & 0xff;
				
				r = r * (64 * (int)(quit.isHovered()? 4.5 : 1)) / 255;
				g = g * (64 * (int)(quit.isHovered()? 4.5 : 1)) / 255;
				b = b * (64 * (int)(quit.isHovered()? 3.25 : 1)) / 255;
				
				pixels[x+y*width] = r << 16 | g << 8 | b;
				
			}
			
		}
		
		SystemFont.draw("Quit", this, (int) quit.pos.x + 32, (int) quit.pos.y + 32, 4);
		
	}

	@Override
	public boolean shouldUpdate() {
		return GameDisplay.screenState == ScreenState.OPTIONS_STATE;
	}
	
	@Override
	public boolean shouldRender() {
		return GameDisplay.screenState == ScreenState.OPTIONS_STATE;
	}

}
