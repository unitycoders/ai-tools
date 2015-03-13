package uk.me.webpigeon.joseph.cow;

import java.awt.Graphics2D;

import uk.me.webpigeon.joseph.utility.Action;
import uk.me.webpigeon.joseph.utility.trees.TreeNode;
import uk.me.webpigeon.steering.FleeBehaviour;
import uk.me.webpigeon.steering.TargetedBehavour;
import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;
import uk.me.webpigeon.world.Tag;
import uk.me.webpigeon.world.World;

public abstract class TargetingAction extends Action {
	private TargetedBehavour targeting;
	protected Entity us;
	private Entity target;
	private Tag targetType;

	public TargetingAction(TreeNode<Double> utilCalc, TargetedBehavour targeting, Tag type) {
		super(utilCalc);
		this.targeting = targeting;
		this.targetType = type;
	}

	@Override
	public void executeStep(Entity entity, World world) {
		if (target == null) {
			target = getTarget(world);
		}
		
		if (target == null || target.isDead() || isCorrectDistance(us, target)) {
			targetingComplete(world, target);
			target = null;
			targeting.setTarget(null);
			entity.setVelocity(new Vector2D(0,0));
		} else {
			targeting.setTarget(target.getLocation());
			Vector2D force = targeting.process();
			entity.setVelocity(force);
		}
	}
	
	protected void targetingComplete(World world, Entity target) {}
	protected abstract boolean isCorrectDistance(Entity us, Entity target);

	protected Entity getTarget(World world) {
		Entity newTarget = world.getNearestEntityOfType(us, targetType);
		if (newTarget == null) {
			return null;
		}
		
		double maxVisibleDistance = us.getValue(Property.SIGHT_RANGE, 100);
		double distanceToTarget = newTarget.getLocation().dist(us.getLocation());
		if (distanceToTarget > maxVisibleDistance) {
			return null;
		}
		return newTarget;
	}
	
	@Override
	public void notifyStarted(Entity entity) {
		this.us = entity;
		targeting.bind(entity);
	}
	
	public void debugDraw(Graphics2D g) {
		if (target != null) {
			targeting.debugDraw(g);
		}
	}
}
