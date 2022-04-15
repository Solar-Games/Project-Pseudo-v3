package io.itch.SolarGames.ProjectPsuedo.level.blocks;

public class GrassBlock extends Block {

	public GrassBlock() {
		
		this.color = 0x00ff00;
		this.floorTex = 0;
		
	}
	
	public double getWalkSpeed() {
		
		return 1/3.334f;
		
	}
	
	public double getFriction() {

		return 0.628;
		
	}
	
}
