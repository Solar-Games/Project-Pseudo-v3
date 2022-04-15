package io.itch.SolarGames.ProjectPsuedo.level.blocks;

import me.sjplus.SJEngine.renderer.Sprite;
import me.sjplus.SJEngine.util.*;

public class WaterBlock extends Block {

	public WaterBlock() {
		
		//super(3, 1/12f, true);
		
		this.color = 0x0045ef;
		this.floorTex = 1;
		
	}
	
	public double getBlockHeight() {
		
		return -10;
		
	}
	
	public double getWalkSpeed() {
		
		return 0.025;
		
	}
	
	public double getFriction() {
		
		return 0.925;
		
	}
	
}
