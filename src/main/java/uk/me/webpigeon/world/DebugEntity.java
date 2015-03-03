package uk.me.webpigeon.world;

import java.awt.Color;
import java.awt.Graphics2D;

import uk.me.webpigeon.behavour.TreeNode;

public class DebugEntity extends Entity {
	private final Integer SIZE = 50; 

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(Color.red);
		g2.fillRect(x * SIZE, y * SIZE, SIZE, SIZE);
	}

}
