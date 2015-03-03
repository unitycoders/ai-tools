package uk.me.webpigeon.steering;

import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;

public class FleeBehavour implements SteeringBehavour {
	private Vector2D currentVel = new Vector2D();
	private Vector2D targetPos;
	private Entity entity;
	
	public FleeBehavour(Vector2D targetPos) {
		this.targetPos = targetPos;
	}
	
	public void bind(Entity entity) {
		this.entity = entity;
	}
	
	/**
	 * 
	 * 
	 * @param q the target position
	 * @return
	 */
	public Vector2D process() {
		Vector2D currentPos = new Vector2D(entity.getX(), entity.getY());
		
		Vector2D targetDirection = Vector2D.subtract(currentPos, targetPos);
		targetDirection.normalise();
		
		return Vector2D.subtract(targetDirection, currentVel);
	}

}
