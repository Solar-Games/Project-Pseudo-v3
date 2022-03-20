package io.itch.SolarGames.ProjectPsuedo.level.blocks;

public class StoneBlock extends Block {

	public StoneBlock(boolean wall) {
		
		this.wall = wall;
		this.blocksEntities = wall;
		
		this.floorTex = 3;
		this.wallTex = 0;
		this.ceilTex = 3;
		this.color = 0xBFBFBF;
		
	}
	
}
