package io.itch.SolarGames.ProjectPsuedo.gui;

import io.itch.SolarGames.ProjectPsuedo.GameDisplay;
import io.itch.SolarGames.ProjectPsuedo.Settings;
import io.itch.SolarGames.ProjectPsuedo.entities.Player;
import io.itch.SolarGames.ProjectPsuedo.gfx.Art;
import io.itch.SolarGames.ProjectPsuedo.gui.comp.Button;
import io.itch.SolarGames.ProjectPsuedo.gui.comp.HoverBox;
import io.itch.SolarGames.ProjectPsuedo.inv.Armor;
import io.itch.SolarGames.ProjectPsuedo.inv.Item;
import me.sjplus.SJEngine.Game;
import me.sjplus.SJEngine.SystemFont;
import me.sjplus.SJEngine.input.Keyboard;
import me.sjplus.SJEngine.input.Mouse;
import me.sjplus.SJEngine.math.MathUtil;
import me.sjplus.SJEngine.math.Vector2;
import me.sjplus.SJEngine.renderer.Screen;
import me.sjplus.SJEngine.renderer.Sprite;

public class InventoryState extends Screen {

	private Player player;
	private int yp = 0;
	private GameState gs;
	
	private HoverBox[] itemHB;
	private HoverBox[] armorHB;
	private boolean fin;
	private Button quit;
	
	public InventoryState(int width, int height, GameState gs) {
	
		super(width, height);
	
		player = GameDisplay.player;
		this.gs = gs;
		
		itemHB = new HoverBox[24];
		armorHB = new HoverBox[3];
		
		int xOff = (width - (64 * 12))/2;
		int yOff = (int) ((height/2 - (1 * height/2)) + (height - (64 * -3))/2);
		
		for (int i = 0; i < 24; i++) {
			
			itemHB[i] = new HoverBox(new Vector2(xOff + (i % 8) * 64, yOff + (i / 8) * 64), 64, 64);
			
		}
		
		for (int i = 0; i < 3; i++) {
			
			armorHB[i] = new HoverBox(new Vector2(xOff + (11 * 64), yOff + (i * 64)), 64, 64);
			
		}
		
		quit = new Button(new Vector2(xOff - 32, 48), 256 * 1.25/2, 96/2);
		
	}

	@Override
	public void update(Game arg0, Mouse arg1, Keyboard arg2) {

		if (yp < 89) {
		
			yp += 2;
			
			yp = (int) MathUtil.clamp(yp, 0, 90);
			
		}
		
		if (yp >= 90) {
			
			yp = 90;
			fin = true;

		}
			
		if (gs.startInv) {
			
			startAnimation();
			gs.startInv = false;
			
		}
		
		if (fin) {
			
			for (int i = 0; i < itemHB.length; i++)
				itemHB[i].update(arg1);
			
			for (int i = 0; i < armorHB.length; i++)
				armorHB[i].update(arg1);
		
			quit.update(arg1);
			
			if (quit.pressed) {
				
				quit.pressed = false;
				GameDisplay.screenState = ScreenState.MAIN_MENU_STATE;

			}
			
		}
		
	}

	Sprite[] hotbatComp = new Sprite[] { Art.shiftColorsInSprite(GameDisplay.uicmpnts.copySpriteFromSheet(1, 1, 16, 16, 16), 0x909090),
			 Art.shiftColorsInSprite(GameDisplay.uicmpnts.copySpriteFromSheet(1, 1, 16, 16, 16), 0xFFFF90),
			 Art.shiftColorsInSprite(GameDisplay.uicmpnts.copySpriteFromSheet(2, 1, 16, 16, 16), 0x909090),
			 Art.shiftColorsInSprite(GameDisplay.uicmpnts.copySpriteFromSheet(2, 1, 16, 16, 16), 0xFFFF90) };
	
	Sprite[] uiComp = new Sprite[] { Art.shiftColorsInSprite(GameDisplay.uicmpnts.copySpriteFromSheet(3, 1, 16, 16, 16), 0x909090),
			 Art.shiftColorsInSprite(GameDisplay.uicmpnts.copySpriteFromSheet(4, 1, 16, 16, 16), 0x909090),
			 Art.shiftColorsInSprite(GameDisplay.uicmpnts.copySpriteFromSheet(5, 1, 16, 16, 16), 0x909090),
			 Art.shiftColorsInSprite(GameDisplay.uicmpnts.copySpriteFromSheet(6, 1, 16, 16, 16), 0x909090) };
	
	@Override
	public void render() {

		this.fill(0);
		
		if (Settings.inv_bg != null)
			this.draw(Settings.inv_bg.createRenderFromSprite(), (width - 1080)/2, (height - 720)/2);
		else
			GameState.renderer.render(this, gs.player, GameDisplay.level);
		
		this.multiplyPixelColors(256/3, 256/3, 256/3);

		int xOff = (width - (64 * 12))/2;
		int yOff = (int) ((height/2 - (Math.sin(Math.toRadians(yp)) * height/2)) + (height - (64 * -3))/2);
		
		for (int x = 0; x < (128 * 6 + 64) / 64; x++) {
			
			for (int y = 0; y < (64 * 4) / 64; y++) {
				
				int aX = (xOff - 32) + (x * 64);
				int aY = (yOff - 32) + (y * 64);
				
				if (x != 0 && y != 0)
					this.scaledDraw(uiComp[3].createRenderFromSprite(), aX, aY, 4);
				
				if (x != 0 && y == 0)
					this.scaledDraw(uiComp[2].createRenderFromSprite(), aX, aY, 4);
					
				if (x == 0 && y != 0)
					this.scaledDraw(uiComp[1].createRenderFromSprite(), aX, aY, 4);
				
				if (x == 0 && y == 0)
					this.scaledDraw(uiComp[0].createRenderFromSprite(), aX, aY, 4);
				
			}
			
		}
		
		for (int x = (int) quit.pos.x; x < quit.pos.x + quit.width; x++) {
			
			for (int y = (int) quit.pos.y; y < quit.pos.y + quit.height; y++) {
				
				int col = pixels[x+y*width];
				
				int r = (col >> 16) & 0xff;
				int g = (col >> 8) & 0xff;
				int b = (col) & 0xff;
				
				r = r * (64 * (int)(quit.isHovered() && fin? 4.5 : 1)) / 255;
				g = g * (64 * (int)(quit.isHovered() && fin? 4.5 : 1)) / 255;
				b = b * (64 * (int)(quit.isHovered() && fin? 3.25 : 1)) / 255;
				
				pixels[x+y*width] = r << 16 | g << 8 | b;
				
			}
			
		}
		
		SystemFont.draw("Quit", this, (int) quit.pos.x + 16, (int) quit.pos.y + 16, 2);
		
		for (int p = 0; p < 2; p++) {
		
			for (int i = 0; i < player.items.length; i++) {
				
				this.scaledDraw(hotbatComp[p*2+(itemHB[i].isHovered() && fin? 1 : 0)].createRenderFromSprite(), xOff + (i % 8) * 64, yOff + (i / 8) * 64, 4);
				
			}
		
		}
		
		for (int i = 0; i < player.items.length; i++) {
			
			if (player.items[i] != null) {
				
				this.scaledDraw(player.items[i].getTexture(Item.INV_TEXTURE).createRenderFromSprite(), xOff + 8 + (i % 9) * 64, yOff + 8 + (i / 9) * 64, 3);
		
				if (player.items[i].stacking > 1)
					SystemFont.draw(String.valueOf(player.items[i].stacking), this, xOff + 10 + (i % 9) * 64, yOff + 40 + (i / 9) * 64, 2);
						
			}
				
		
		}
		
		for (int p = 0; p < 2; p++) {
			
			for (int i = 0; i < 3; i++) {
				
				this.scaledDraw(hotbatComp[p*2+(armorHB[i].isHovered()? 1 : 0)].createRenderFromSprite(), xOff + (11 * 64), yOff + (i * 64), 4);
				
			}
			
		}
		
		Armor[] armorPieces = new Armor[] { player.helmet, player.chestplate, player.boots };
		
		for (int i = 0; i < 3; i++) {
			
			if (armorPieces[i] != null)		
				this.scaledDraw(armorPieces[i].getTexture().createRenderFromSprite(), xOff + (15 * 48) - (24 - 48/3), yOff + (i * 64) - (8 - 48/3), 3);
			
		}

		SystemFont.draw("" + Game.fps, this, 0, 0, 2);
		
	}

	@Override
	public boolean shouldUpdate() {
	
		return GameDisplay.screenState == ScreenState.INVENTORY_STATE;
	
	}
	
	@Override
	public boolean shouldRender() {
	
		return GameDisplay.screenState == ScreenState.INVENTORY_STATE;
	
	}

	public void startAnimation() {
		
		yp = 0;
		fin = false;
		
	}
	
}
