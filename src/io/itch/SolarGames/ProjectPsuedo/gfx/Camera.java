package io.itch.SolarGames.ProjectPsuedo.gfx;

import me.sjplus.SJEngine.math.*;

public interface Camera {

	public static final float fov = 0;
	
	public void setPostion(double x, double y, double z);
	
	public void changePostion(double x, double y, double z);
		
	public void setRotation(float yaw, float pitch);
		
	public void changeRotation(float yaw, float pitch);
	
	public Vector3 getPos();
	
	public FVector2 getRot();
		
}
