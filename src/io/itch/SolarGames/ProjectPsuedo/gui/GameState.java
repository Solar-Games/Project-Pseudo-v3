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
import me.sjplus.SJEngine.math.MathUtil;
import me.sjplus.SJEngine.math.Vector3;
import me.sjplus.SJEngine.renderer.*;
import me.sjplus.SJEngine.util.*;

public class GameState extends Screen {

	public static GameRenderer renderer;
	public static Camera cam;
	
	private Level lvl;
	
	private Key esc;
	private Key inv;
	private int itemUseLength;
	
	private boolean escAlreadyPressed;
	private boolean invAlreadyPressed;
	private boolean useAlready;
	private boolean focus;
	
	private float strafe, forward;
	
	private Block lastBlock;
	private float aniMotion;
	private boolean commitAnimation;
	
	private int tX, tY;
	
	private Timer timer;
	private Entity lastEntity;
	
	private Player player;
	public boolean startInv = false;
	
	public GameState(int width, int height) {
	
		super(width, height);

		renderer = new Render3D(width, height);
		cam = new Camera(0, 0, 0, 0, 0, (float) (height * Math.tanh(Math.toRadians(135))));
		
		cam.setPostion(0, 12, 0);
		
		GameDisplay.player = new Player(cam, GameDisplay.level);
		
		player = GameDisplay.player;
		
		/*player.addArmor(Armor.iron_boots);
		player.addArmor(Armor.iron_chestplate);
		player.addArmor(Armor.iron_helmet);*/
		
		inv = new Key(new int[] { KeyEvent.VK_E }, "Inventory");
		esc = new Key(new int[] { KeyEvent.VK_ESCAPE }, "Escape");
		
		timer = new Timer();
		
	}

	@Override
	public void update(Game arg0, Mouse arg1, Keyboard arg2) {

		mouse(arg1);
		keyboard(arg2);
		
		tX = (int) (cam.position.x + 24) >> 4;
		tY = (int) (cam.position.z + 24) >> 4;
		
		Block block = null;
		
		if (tX < lvl.getWidth() || tY < lvl.getHeight() || tX > 0 || tY > 0)
			block = lvl.getBlock(tX, tY);
		
		double cos = Math.cos(Math.toRadians(cam.rotation.x)),
				sin = Math.sin(Math.toRadians(cam.rotation.x));
		
		cam.setPostion(cam.position.x, 2.25 + block.getBlockHeight(), cam.position.z);
		cam.setRotation(cam.rotation.x, (float) (30 - -block.getBlockHeight()));
		
		lvl.updateLevel(cam);
		
		if (GameDisplay.screenState != ScreenState.INVENTORY_STATE) {

			double speed = block.getWalkSpeed() + player.speed;
			
			double xAng = (strafe * speed * cos + forward * speed * sin);
			double zAng = (forward * speed * cos - strafe * speed * sin);
			
			double mx = Math.abs(xAng * 100) + 1;
			double mz = Math.abs(zAng * 100) + 1;
			
			cam.changePostion(xAng, 0, zAng);
			
			block.onBlock(player);
			
			if (player.getLevel() != GameDisplay.level) {
			
				lvl = GameDisplay.level = player.getLevel();
				
			}
			
		}
		
		cam.setPostion(MathUtil.clamp(cam.position.x, -23, (lvl.getWidth() * 16) - 28), cam.position.y, MathUtil.clamp(cam.position.z, -23, (lvl.getHeight() * 16) - 28));
		
		lastBlock = block;
		
	}
	
	public void mouse(Mouse mouse) {
		
		float yawResult = 0;
		
		if (focus) {
		
			yawResult = mouse.mouseX - width/2 + 8;
			
			cam.changeRotation(yawResult * GameDisplay.sens, 0);
			
		}
		
		if (itemUseLength > 10) {
			
			itemUseLength = 0;
			
		}
		
		if (!useAlready && mouse.mouseButtonDown[0] || itemUseLength != 0) {
		
			itemUseLength++;
			
			if (itemUseLength == 1)
				useEntity();
			
		}
		
		if (mouse.mouseButtonDown[0] && !useAlready)
			useAlready = true;

		else if (!mouse.mouseButtonDown[0] && useAlready)
			useAlready = false;
		
		Mouse.shouldLockMouse(focus);
		
		player.update(mouse);
		
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
		
		forward = (keyboard.up.pressed && !keyboard.down.pressed? 1.0f : (keyboard.down.pressed && !keyboard.up.pressed? -1.0f : 0.0f));
		strafe = (keyboard.right.pressed && !keyboard.left.pressed? 1.0f : (keyboard.left.pressed && !keyboard.right.pressed? -1.0f : 0.0f));
		
		if (renderer instanceof Render2D)
			strafe *= -1;
		
	}
	
	public void useEntity() {
		
		int x = (int) Math.round(Math.sin(Math.toRadians(cam.rotation.x)));
		int y = (int) Math.round(Math.cos(Math.toRadians(cam.rotation.x)));
	
		
		Entity e;
		
		if (lvl.getEntity(tX, tY) != null) {
			e = lvl.getEntity(tX, tY);
			e.inEntity(player);
		}
		
		else
			e = lvl.getEntity(tX + x, tY + y);
		
		lastEntity = e;
		
		if (e != null && player.selectedItem != null)
			e.use(player, player.selectedItem);
			
	}

	Sprite[] uiComp = new Sprite[] { Art.shiftColorsInSprite(GameDisplay.uicmpnts.copySpriteFromSheet(1, 1, 16, 16, 16), 0x909090),
									 Art.shiftColorsInSprite(GameDisplay.uicmpnts.copySpriteFromSheet(1, 1, 16, 16, 16), 0xFFFF90),
									 Art.shiftColorsInSprite(GameDisplay.uicmpnts.copySpriteFromSheet(2, 1, 16, 16, 16), 0x909090),
									 Art.shiftColorsInSprite(GameDisplay.uicmpnts.copySpriteFromSheet(2, 1, 16, 16, 16), 0xFFFF90) };
	
	@Override
	public void render() {

		this.fill(0);
		
		renderer.render(this, cam, lvl);
		
		SystemFont.draw("X: " + tX + ", Y: " + tY, this, 0, 0, 2);
		SystemFont.draw("FPS: " + Game.fps, this, 0, 14, 2);
		
		int x = (int) Math.round(Math.sin(Math.toRadians(cam.rotation.x)));
		int y = (int) Math.round(Math.cos(Math.toRadians(cam.rotation.x)));
		
		SystemFont.draw("RX: " + x + ", RY: " + y, this, 0, 28, 2);
		
		for (int i = 0; i < 3; i++) {
			
			for (int j = 0; j < 9; j++) {
				
				this.scaledDraw(uiComp[(int)((i/2) * 2) + (j != player.hotbar? 0 : 1)].createRenderFromSprite(), 0 + (int)(16 * j * 4f), height - (int) (27 * 4f) + 5, 4f);
				
			}
			
		}
		
		for (int i = 0; i < 9; i++) {
			
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
	
	public void setupGameState() {
		
		GameDisplay.level = Level.findLevel(0);
		lvl = GameDisplay.level;
		
		player.changeLevel(0);
		
	}
	
}
