package uk.me.webpigeon.steering;

import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;

public class SeekBehavour implements SteeringBehavour {
	private Vector2D currentVel = new Vector2D();
	private Vector2D targetPos;
	private Entity entity;
	
	public SeekBehavour(Vector2D targetPos) {
		this.targetPos = targetPos;
	}
	
	/**
	 * 
	 * 
	 * @param q the target position
	 * @return
	 */
	public Vector2D process() {
		Vector2D currentPos = new Vector2D(entity.getX(), entity.getY());
		System.out.println(currentPos);
		
		Vector2D targetDirection = Vector2D.subtract(targetPos, currentPos);
		targetDirection.normalise();
		
		currentVel = Vector2D.subtract(targetDirection, currentVel);
		return currentVel;
	}

	public void bind(Entity entity) {
		this.entity = entity;
	}

}
