package io.itch.SolarGames.ProjectPsuedo.level.blocks;

public class StoneBlock extends Block {

	public StoneBlock(boolean wall) {
		
		this.wall = wall;
		this.blocksEntities = wall;
		
		this.floorTex = 5;
		this.wallTex = 0;
		this.ceilTex = 5;
		this.color = 0xBFBFBF;
		
	}
	
}
