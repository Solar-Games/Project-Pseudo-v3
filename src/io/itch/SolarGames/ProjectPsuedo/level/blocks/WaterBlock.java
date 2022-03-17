package io.itch.SolarGames.ProjectPsuedo.level.blocks;

import me.sjplus.SJEngine.renderer.Sprite;
import me.sjplus.SJEngine.util.*;

public class WaterBlock extends Block {

	public WaterBlock() {
		
		this.color = 0x0045ef;
		this.floorTex = 1;
		
	}
	
	public double getBlockHeight() {
		
		return -10;
		
	}
	
	public double getWalkSpeed() {
		
		return 0.5;
		
	}
	
}
