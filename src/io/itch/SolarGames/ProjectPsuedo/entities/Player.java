package io.itch.SolarGames.ProjectPsuedo.entities;

import javax.swing.text.Position;

import io.itch.SolarGames.ProjectPsuedo.Config;
import io.itch.SolarGames.ProjectPsuedo.GameDisplay;
import io.itch.SolarGames.ProjectPsuedo.Settings;
import io.itch.SolarGames.ProjectPsuedo.gfx.Camera;
import io.itch.SolarGames.ProjectPsuedo.inv.*;
import io.itch.SolarGames.ProjectPsuedo.inv.armtype.*;
import io.itch.SolarGames.ProjectPsuedo.level.Level;
import io.itch.SolarGames.ProjectPsuedo.level.blocks.Block;
import me.sjplus.SJEngine.input.Mouse;
import me.sjplus.SJEngine.math.FVector2;
import me.sjplus.SJEngine.math.MathUtil;
import me.sjplus.SJEngine.math.Vector3;
import me.sjplus.SJEngine.renderer.Sprite;
import me.sjplus.SJEngine.util.Timer;

public class Player extends Entity implements Camera {

	public static final float fov = Settings.fov;
	
	public Item[] items;
	public float defense;
	public Boots boots;
	public Chestplate chestplate;
	public Helmet helmet;
	public double speed;
	public Item selectedItem;
	public int hotbar = 0;
	
	private Timer timer;
	
	public Player(Vector3 pos, FVector2 rot, Level level) {
	
		super(pos, rot, 8, level, null, 0xffffff);

		this.items = new Item[24];
		items[0] = Item.hand;
		
		addArmor(Armor.dragon_boots);
		addArmor(Armor.dragon_chestplate);
		addArmor(Armor.dragon_helmet);
		
		timer = new Timer();
		
		hotbar = 0;
		
	}
	
	public void update(Mouse mouse) {
		
		Block currentBlock = null;
		
		int tX = (int) (pos.x + 24) >> 4;
		int tY = (int) (pos.z + 24) >> 4;
		
		currentBlock = level.getBlock(tX, tY);
		currentBlock.onBlock(this);
		
		vel.multiply(currentBlock.getFriction(), 1, currentBlock.getFriction());
		
		pos.y = 4 + currentBlock.getBlockHeight();
		rot.y = 30.5f;
		
		hotbar -= mouse.wheelIncrement;
			
		hotbar %= 8;
		
		if (hotbar < 0)
			hotbar = 8;
			
		selectedItem = items[hotbar];
		
		mouse.wheelIncrement = 0;
		
		float bd = (boots != null? boots.getDefense() : 0);
		float cd = (chestplate != null? chestplate.getDefense() : 0);
		float hd = (helmet != null? helmet.getDefense() : 0);
		
		defense = bd + cd + hd;
		
		updateInventory();
		
		move(Settings.movement()[0], Settings.movement()[1], currentBlock.getWalkSpeed() * speed);
		
		if (mouse.lock) {
			
			float yawResult = -(GameDisplay.width/2 - mouse.lastMouseX - 8) * GameDisplay.sens;
			
			rot.add(yawResult, 0);
			
		}
		
	}
	
	public void updateInventory() {
		
		if (boots == Armor.dragon_boots)
			speed = 1.25;
		
	}
	
	public boolean hasItem(Item item, int stack) {
		
		for (int i = 0; i < 27; i++) {
			
			if (items[i] == item) {
				
				if (stack != 0)
					return item.stacking == stack;
				
				else
					return true;
				
			}
			
		}
		
		return false;
		
	}
	
	public void giveItem(Item item, int stack) {
		
		for (int i = 0; i < 27; i++) {
			
			if (items[i] == null) {
			
				items[i] = item;
				
				if (stack != 0 && items[i] != null)
					items[i].addToStack(stack-1);
				
				break;
				
			} else if (items[i] == item) {
				
				if (stack != 0)
					items[i].addToStack(stack);
				
				else
					items[i].addToStack(1);
				
				break;
				
			}
			
		}
		
	}
	
	public void removeItem(Item item, int stack) {
		
		for (int i = 0; i < 27; i++) {
			
			if (items[i] == item) {
				
				if (items[i].canStack) {
					
					if (stack != 0)
						items[i].removeFromStack(stack);

					else
						items[i].removeFromStack(1);

					if (items[i].isZero())
						items[i] = null;
					
				} else
					items[i] = null;
				
				break;
				
			}
				
			
		}
		
	}
	
	public void addArmor(Armor armor) {
		
		if (armor instanceof Boots || armor instanceof Chestplate || armor instanceof Helmet) {
			
			if (armor instanceof Boots)
				boots = (Boots) armor;
			
			if (armor instanceof Chestplate)
				chestplate = (Chestplate) armor;
			
			if (armor instanceof Helmet)
				helmet = (Helmet) armor;
			
		}
		
	}
	
	public void removeArmor(Armor armor) {
		
		if (armor instanceof Boots || armor instanceof Chestplate || armor instanceof Helmet) {
			
			if (((Boots) armor).equals(boots))
				boots = null;
			
			if (((Chestplate) armor).equals(chestplate))
				chestplate = null;
			
			if (((Helmet) armor).equals(helmet))
				helmet = null;
			
		}
		
	}

	public void changeLevel(int i) {

		level = Level.findLevel(i);
		
	}
	
	public Level getLevel() {
		
		return level;
		
	}

	@Override
	public void setPostion(double x, double y, double z) {
	
		pos.set(x, y, z);
	
	}

	@Override
	public void changePostion(double x, double y, double z) {
	
		pos.add(x, y, z);
	
	}

	@Override
	public void setRotation(float yaw, float pitch) {
		
		rot.set(yaw, pitch);
		
	}

	@Override
	public void changeRotation(float yaw, float pitch) {
		
		rot.add(yaw, pitch);
		
	}

	public Vector3 getPos() {
		
		return pos;
		
	}
	
	@Override
	public FVector2 getRot() {
		
		return rot;
		
	}
	
}
