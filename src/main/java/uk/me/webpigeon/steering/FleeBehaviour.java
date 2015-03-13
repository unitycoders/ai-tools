package uk.me.webpigeon.steering;

import java.awt.Color;
import java.awt.Graphics2D;

import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;

public class FleeBehaviour implements TargetedBehavour {
	private Vector2D targetPos;
	private Entity entity;

	public FleeBehaviour(Vector2D targetPos) {
		this.targetPos = targetPos;
	}

	public void bind(Entity entity) {
		this.entity = entity;
	}

	/**
	 * Return the position vector which corresponds to the opposite direction to the object.
	 * 
	 * @return the normalised direction vector
	 */
	public Vector2D process() {
		Vector2D currentPos = entity.getLocation();

		// Flee(q) = smax.unit(p − q) − v

		Vector2D targetDirection = Vector2D.subtract(currentPos, targetPos);
		targetDirection.normalise();

		Vector2D currentVel = targetDirection;
		return currentVel;
	}

	@Override
	public void debugDraw(Graphics2D g) {
		if (entity == null) {
			return;
		}
		
		g.setColor(Color.RED);
		Vector2D entPos = entity.getLocation();
		g.drawLine((int) entPos.x, (int) entPos.y, (int) targetPos.x,
				(int) targetPos.y);
	}

	@Override
	public void setTarget(Vector2D target) {
		this.targetPos = target;
	}

}
