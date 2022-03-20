package io.itch.SolarGames.ProjectPsuedo.level.blocks;

import io.itch.SolarGames.ProjectPsuedo.entities.*;

public class TunnelBlock extends Block {

	public final boolean up;
	
	public TunnelBlock(boolean up) {
		
		if (!up) {
			
			this.floorTex = 2;
			this.ceilTex = -1;
			
		} else {
		
			this.floorTex = 5;
			this.ceilTex = 4;

		}
			
		this.color = (!up? 0x00B200 : 0xBFBFBF);
		
		this.up = up;
		
	}
	
	public double getBlockHeight() {
		
		return -5 + (up? 5 : 0);
		
	}
	
	public void onBlock(Entity e) {
		
		if (e instanceof Player && !up) {
			
			Player player = (Player) e;
			
			player.changeLevel(1);
			
		}
		
	}
	
}
