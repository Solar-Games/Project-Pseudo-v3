package io.itch.SolarGames.ProjectPsuedo.gui;

import io.itch.SolarGames.ProjectPsuedo.GameDisplay;
import io.itch.SolarGames.ProjectPsuedo.Settings;
import io.itch.SolarGames.ProjectPsuedo.gui.comp.Button;
import io.itch.SolarGames.ProjectPsuedo.inv.Item;
import io.itch.SolarGames.ProjectPsuedo.level.Generation;
import io.itch.SolarGames.ProjectPsuedo.level.Level;
import me.sjplus.SJEngine.Game;
import me.sjplus.SJEngine.SystemFont;
import me.sjplus.SJEngine.input.Keyboard;
import me.sjplus.SJEngine.input.Mouse;
import me.sjplus.SJEngine.math.Vector2;
import me.sjplus.SJEngine.renderer.Screen;

public class MainMenuState extends Screen {

	public Button[] buttons;
	public String[] buttonNames;
	
	private GameState gs;
	
	public MainMenuState(int width, int height, GameState gs) {

		super(width, height);
	
		this.gs = gs;
		
		buttons = new Button[2];
		buttonNames = new String[] { "Start", "Options" };
		
		int xOff = (width / 8) - 96 / 8;
		int yOff = (height / 2) + (height / 8) - (int) (96 * 1.5);
		
		for (int i = 0; i < 2; i++)
			buttons[i] = new Button(new Vector2(xOff, yOff + (96 * 2) * i), 256 * 1.25, 96);
		
	}
	
	@Override
	public void update(Game arg0, Mouse arg1, Keyboard arg2) {

		for (int i = 0; i < 2; i++)
			buttons[i].update(arg1);
		
		if (buttons[0].pressed) {
		
			buttons[0].pressed = false;
			
			Generation.createTestLevel(0);
			Generation.generateCavernLevel(1, Level.findLevel(0), 64, 64, 150, 1800);
			
			gs.setupGameState(new Level[] { Level.findLevel(0), Level.findLevel(1) });
			
			GameDisplay.screenState = ScreenState.GAME_STATE;
			
		}
			
		if (buttons[1].pressed) {
			
			buttons[1].pressed = false;
			GameDisplay.screenState = ScreenState.OPTIONS_STATE;
			
		}
			
	}
	
	@Override
	public void render() {
		
		this.fill(0);
		
		if (Settings.mm_bg != null)
			this.draw(Settings.mm_bg.createRenderFromSprite(), (width - 1080)/2, (height - 720)/2);
		
		this.multiplyPixelColors(256/3, 256/3, 256/3);
		
		SystemFont.draw("Project Pseudo", this, 96 + 28, 96 + 28, 8);
		
		for (int i = 0; i < 2; i++) {
			
			Button button = buttons[i];
			
			for (int x = (int) button.pos.x; x < button.pos.x + button.width; x++) {
				
				for (int y = (int) button.pos.y; y < button.pos.y + button.height; y++) {
					
					int col = pixels[x+y*width];
					
					int r = (col >> 16) & 0xff;
					int g = (col >> 8) & 0xff;
					int b = (col) & 0xff;
					
					r = r * (64 * (int)(button.isHovered()? 4.5 : 1)) / 255;
					g = g * (64 * (int)(button.isHovered()? 4.5 : 1)) / 255;
					b = b * (64 * (int)(button.isHovered()? 3.25 : 1)) / 255;
					
					pixels[x+y*width] = r << 16 | g << 8 | b;
				}
				
			}
			
			SystemFont.draw(buttonNames[i], this, (int) button.pos.x + 32, (int) button.pos.y + 32, 4);
			
		}
		
		SystemFont.draw("" + Game.fps, this, 0, 0, 2);
		
	}

	@Override
	public boolean shouldUpdate() {
		return GameDisplay.screenState == ScreenState.MAIN_MENU_STATE;
	}
	
	@Override
	public boolean shouldRender() {
		return GameDisplay.screenState == ScreenState.MAIN_MENU_STATE;
	}

}
