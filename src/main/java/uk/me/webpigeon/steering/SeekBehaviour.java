package uk.me.webpigeon.steering;

import java.awt.Color;
import java.awt.Graphics2D;

import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;

public class SeekBehaviour implements SteeringBehaviour {
	private Vector2D targetPos;
	private Entity entity;
	
	public SeekBehaviour(Vector2D targetPos) {
		this.targetPos = targetPos;
	}
	

	public Vector2D process() {
		Vector2D currentPos = entity.getLocation();

		Vector2D targetDirection = Vector2D.subtract(targetPos, currentPos);
		targetDirection.normalise();
		
		Vector2D currentVel = targetDirection;
		return currentVel;
	}

	public void bind(Entity entity) {
		this.entity = entity;
	}

	public void setTarget(Vector2D seekTarget) {
		targetPos = seekTarget;
	}

	@Override
	public void debugDraw(Graphics2D g) {
		g.setColor(Color.GREEN);
		Vector2D entPos = entity.getLocation();
		g.drawLine((int)entPos.x, (int)entPos.y, (int)targetPos.x, (int)targetPos.y);	
	}

}
