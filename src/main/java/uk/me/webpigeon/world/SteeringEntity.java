package uk.me.webpigeon.world;

import java.awt.Color;
import java.awt.Graphics2D;

import uk.me.webpigeon.behavour.TreeNode;
import uk.me.webpigeon.steering.SteeringBehavour;
import uk.me.webpigeon.util.Vector2D;

public class SteeringEntity extends Entity {
	private static final Integer SIZE = 50;
	private SteeringBehavour behavour;
	
	public SteeringEntity(SteeringBehavour behavour) {
		this.behavour = behavour;
		behavour.bind(this);
	}

	@Override
	public void update(TreeNode behaviourRoot) {
		Vector2D targetForce = behavour.process();
		
		x += targetForce.getX();
		y += targetForce.getY();
	}

	@Override
	public void draw(Graphics2D graphics) {
		graphics.setColor(Color.WHITE);
		graphics.drawOval((int)(x), (int)(y), SIZE, SIZE);
	}

}
