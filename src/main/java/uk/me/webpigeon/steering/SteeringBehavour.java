package uk.me.webpigeon.steering;

import java.awt.Graphics2D;

import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;

public interface SteeringBehavour {
	
	public void bind(Entity entity);
	public Vector2D process();
	public void debugDraw(Graphics2D g);

}
