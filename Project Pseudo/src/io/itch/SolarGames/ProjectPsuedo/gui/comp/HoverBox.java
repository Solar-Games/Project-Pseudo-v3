package io.itch.SolarGames.ProjectPsuedo.gui.comp;

import me.sjplus.SJEngine.input.Mouse;
import me.sjplus.SJEngine.math.Vector2;
import me.sjplus.SJEngine.util.Logger;

public class HoverBox {

	public Vector2 pos;
	public double width, height;
	protected boolean hovering = false;
	
	public HoverBox(Vector2 pos, double width, double height) {
		
		this.pos = pos;
		this.height = height;
		this.width = width;
		
	}
	
	public void update(Mouse mouse) {
		
		hovering = mouse.mouseX > pos.x &&
				mouse.mouseY > pos.y &&
				mouse.mouseX < pos.x + width &&
				mouse.mouseY < pos.y + height;
				
	}
	
	public boolean isHovered() {
		
		return hovering;
		
	}
	
}
