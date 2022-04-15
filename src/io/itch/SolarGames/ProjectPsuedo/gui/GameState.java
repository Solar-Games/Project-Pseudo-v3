package io.itch.SolarGames.ProjectPsuedo.gui;

import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import io.itch.SolarGames.ProjectPsuedo.*;
import io.itch.SolarGames.ProjectPsuedo.entities.Entity;
import io.itch.SolarGames.ProjectPsuedo.entities.EntityTree;
import io.itch.SolarGames.ProjectPsuedo.entities.Player;
import io.itch.SolarGames.ProjectPsuedo.gfx.*;
import io.itch.SolarGames.ProjectPsuedo.inv.Armor;
import io.itch.SolarGames.ProjectPsuedo.inv.Item;
import io.itch.SolarGames.ProjectPsuedo.level.*;
import io.itch.SolarGames.ProjectPsuedo.level.blocks.*;
import me.sjplus.SJEngine.*;
import me.sjplus.SJEngine.input.*;
import me.sjplus.SJEngine.math.FVector2;
import me.sjplus.SJEngine.math.MathUtil;
import me.sjplus.SJEngine.math.Vector3;
import me.sjplus.SJEngine.renderer.*;
import me.sjplus.SJEngine.util.*;

public class GameState extends Screen {

	public static GameRenderer renderer;
	
	private Key esc;
	private Key inv;
	private int itemUseLength;
	
	private boolean escAlreadyPressed;
	private boolean invAlreadyPressed;
	private boolean focus;
	
	private Timer timer;
	private Entity lastEntity;
	
	public Player player;
	public boolean startInv = false;
	
	public GameState(int width, int height) {
	
		super(width, height);

		renderer = new Render3D(width, height);
		player = new Player(new Vector3(0, 0, 0), new FVector2(0, 0), GameDisplay.level);
		
		player.setPostion(0, 12, 0);
		
		GameDisplay.player = player;
		
		inv = new Key(new int[] { KeyEvent.VK_E }, "Inventory");
		esc = new Key(new int[] { KeyEvent.VK_ESCAPE }, "Escape");
		
		timer = new Timer();
		
	}

	@Override
	public void update(Game arg0, Mouse arg1, Keyboard arg2) {

		player.getLevel().updateLevel(player);
		player.update(arg1);
		keyboard(arg2);
		Mouse.shouldLockMouse(focus);
		
	}
	
	public void keyboard(Keyboard keyboard) {
		
		if (GameDisplay.screenState != ScreenState.INVENTORY_STATE) {
			
			if (esc.pressed && !escAlreadyPressed) {
			
				escAlreadyPressed = true;
				focus = !focus;
			
			}
			
			if (!esc.pressed && escAlreadyPressed)
				escAlreadyPressed = false;
		
		} else
			focus = false;
		
		if (inv.pressed && !invAlreadyPressed) {
			
			invAlreadyPressed = true;

			if (GameDisplay.screenState == ScreenState.INVENTORY_STATE) {
			
				GameDisplay.screenState = ScreenState.GAME_STATE;
				startInv = false;
				
			}
			
			else if (GameDisplay.screenState == ScreenState.GAME_STATE) {
			
				GameDisplay.screenState = ScreenState.INVENTORY_STATE;
				startInv = true;
				
			}
			
		}
		
		if (!inv.pressed && invAlreadyPressed)
			invAlreadyPressed = false;
		
	}

	Sprite[] uiComp = new Sprite[] { Art.shiftColorsInSprite(GameDisplay.uicmpnts.copySpriteFromSheet(1, 1, 16, 16, 16), 0x909090),
									 Art.shiftColorsInSprite(GameDisplay.uicmpnts.copySpriteFromSheet(1, 1, 16, 16, 16), 0xFFFF90),
									 Art.shiftColorsInSprite(GameDisplay.uicmpnts.copySpriteFromSheet(2, 1, 16, 16, 16), 0x909090),
									 Art.shiftColorsInSprite(GameDisplay.uicmpnts.copySpriteFromSheet(2, 1, 16, 16, 16), 0xFFFF90) };
	
	@Override
	public void render() {

		this.fill(0);
		
		if (player.getLevel() != null)
			renderer.render(this, player, player.getLevel());
		
		SystemFont.draw("FPS: " + Game.fps, this, 0, 14, 2);
		
		int x = (int) Math.round(Math.sin(Math.toRadians(player.pos.x)));
		int y = (int) Math.round(Math.cos(Math.toRadians(player.pos.x)));
		
		SystemFont.draw("RX: " + x + ", RY: " + y, this, 0, 28, 2);
		
		for (int i = 0; i < 3; i++) {
			
			for (int j = 0; j < 8; j++) {
				
				this.scaledDraw(uiComp[(int)((i/2) * 2) + (j != player.hotbar? 0 : 1)].createRenderFromSprite(), 0 + (int)(16 * j * 4f), height - (int) (27 * 4f) + 5, 4f);
				
			}
			
		}
		
		for (int i = 0; i < 8; i++) {
			
			if (player.items[i] != null) {
				
				this.scaledDraw(player.items[i].getTexture(Item.INV_TEXTURE).createRenderFromSprite(), 8 + (int)(16 * i * 4f), height - (int) ((27 * 3.5f) + 1), 3);

				if (player.items[i].stacking > 1)
					SystemFont.draw(String.valueOf(player.items[i].stacking), this, (4) + (int)(16 * i * 4f) + 7, height - (int)((27 * 3.5f) - 30), 2);
			
			}
			
		}
		
		if (player.selectedItem != null)
			this.scaledDraw(player.selectedItem.getTexture(Item.STATIC_TEXTURE + (itemUseLength > 0? 1 : 0)).createRenderFromSprite(), width - 16*(17 + (itemUseLength > 0? 2 : 0)), height - (int)(16*21) + 42, 16);
		
		SystemFont.draw("Speed: " + (player.speed + 1), this, 1, height - (17 * 8), 2);
		SystemFont.draw("Defense: " + player.defense, this, 1, height - (17 * 7), 2);
		
	}

	@Override
	public boolean shouldUpdate() {
		
		return GameDisplay.screenState == ScreenState.GAME_STATE || GameDisplay.screenState == ScreenState.INVENTORY_STATE;
	
	}
	
	@Override
	public boolean shouldRender() {
	
		return GameDisplay.screenState == ScreenState.GAME_STATE;
	
	}
	
	public void setupGameState(Level[] levels) {
		
		for (Level level : levels) {
			
			level.addEntity(player);
			
		}
		
		player.changeLevel(0);
		
	}
	
}
