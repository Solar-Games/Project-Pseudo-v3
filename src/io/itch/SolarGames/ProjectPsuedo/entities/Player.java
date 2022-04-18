package io.itch.SolarGames.ProjectPsuedo.entities;

import io.itch.SolarGames.ProjectPsuedo.*;
import io.itch.SolarGames.ProjectPsuedo.gfx.*;
import io.itch.SolarGames.ProjectPsuedo.gui.GameState;
import io.itch.SolarGames.ProjectPsuedo.inv.*;
import io.itch.SolarGames.ProjectPsuedo.inv.armtype.*;
import io.itch.SolarGames.ProjectPsuedo.level.*;
import io.itch.SolarGames.ProjectPsuedo.level.blocks.*;
import me.sjplus.SJEngine.*;
import me.sjplus.SJEngine.input.*;
import me.sjplus.SJEngine.math.*;
import me.sjplus.SJEngine.renderer.*;
import me.sjplus.SJEngine.util.*;

public class Player extends Entity implements Camera {

	public static final float fov = Settings.fov;
	
	public Item[] items;
	public float defense;
	public Boots boots;
	public Chestplate chestplate;
	public Helmet helmet;
	public double speed, friction;
	public Item selectedItem;
	public int hotbar = 0;
	
	private Timer timer;
	private int useTime;
	
	private int tileX, tileY;
	
	public boolean used = false;
	
	private boolean alreadyPressed = false;
	
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
		
		tileX = tX;
		tileY = tY;
		
		currentBlock = level.getBlock(tX, tY);
		currentBlock.onBlock(this);
		
		vel.multiply(currentBlock.getFriction() + friction, 1, currentBlock.getFriction() + friction);
		
		pos.y = 2 + currentBlock.getBlockHeight();
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
		
		if (useTime == 0) {
		
			if (mouse.mouseButtonDown[0] && !alreadyPressed) {
				
				useTime = 10;
				
				int cos = (int) Math.round(Math.cos(Math.toRadians(rot.x))),
					sin = (int) Math.round(Math.sin(Math.toRadians(rot.x)));
				
				Entity e = null;
				
				if (level.getEntity(tX - 1, tY - 1) == null) {
				
					e = level.getEntity(tX - 1 + sin, tY - 1 + cos);
				
				}
				
				if (items[hotbar] != null && e != null) {
					
					e.use(this, items[hotbar]);
					
				}
				
			}
		
		} else {
			
			useTime--;
			
		}
		
		alreadyPressed = mouse.mouseButtonDown[0];
		used = useTime > 0;
		
	}
	
	public void renderDebugGUI(Screen screen) {
		
		double cos = Math.round(Math.cos(Math.toRadians(rot.x))),
				sin = Math.round(Math.sin(Math.toRadians(rot.x)));
		
		//SystemFont.draw("SX: " + (tileX + sin) + "SY: " + (tileY + cos), screen, 0, 0, 2.0f);
		
	}
	
	public void updateInventory() {
		
		if (boots == Armor.dragon_boots) {
			
			speed = 1.75;
			
		} else {
			
			speed = 1;
			
		}
			
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

	@Override
	public void addRendering(Render3D r3d) {

	}
	
}
