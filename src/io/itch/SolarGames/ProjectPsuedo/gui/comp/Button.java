package io.itch.SolarGames.ProjectPsuedo.gui.comp;

import me.sjplus.SJEngine.input.Mouse;
import me.sjplus.SJEngine.math.Vector2;

public class Button extends HoverBox {

	public boolean pressed;
	private boolean alreadyPressed;
	
	public Button(Vector2 pos, double width, double height) {
	
		super(pos, width, height);

	}

	public void update(Mouse mouse) {
		
		super.update(mouse);
		
		if (hovering) {
		
			pressed = mouse.mouseButtonDown[0] && !alreadyPressed;
			alreadyPressed = mouse.mouseButtonDown[0];
		
		}
		
	}
	
}
