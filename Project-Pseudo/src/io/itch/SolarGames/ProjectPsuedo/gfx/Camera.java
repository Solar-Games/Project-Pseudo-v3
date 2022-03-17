package io.itch.SolarGames.ProjectPsuedo.gfx;

import me.sjplus.SJEngine.math.*;

public class Camera {

	public Vector3 position;
	public FVector2 rotation;
	public float fov;
	
	public Camera(double x, double y, double z, float yaw, float pitch, float fov) {
		
		position = new Vector3(x, y, z);
		rotation = new FVector2(yaw, pitch);
		this.fov = fov;
		
	}
	
	public void setPostion(double x, double y, double z) {
		
		position.set(x, y, z);
		
	}
	
	public void changePostion(double x, double y, double z) {
		
		position.add(x, y, z);
		
	}
	
	public void setRotation(float yaw, float pitch) {
		
		rotation.set(yaw, pitch);
		
	}
	
	public void changeRotation(float yaw, float pitch) {
		
		rotation.add(yaw, pitch);
		
	}
	
}
