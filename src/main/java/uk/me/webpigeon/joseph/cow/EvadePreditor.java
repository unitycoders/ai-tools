package uk.me.webpigeon.joseph.cow;

import java.awt.Graphics2D;

import uk.me.webpigeon.joseph.utility.Action;
import uk.me.webpigeon.joseph.utility.trees.AbstractTreeNode;
import uk.me.webpigeon.joseph.utility.trees.TreeNode;
import uk.me.webpigeon.steering.FleeBehaviour;
import uk.me.webpigeon.steering.SeekBehaviour;
import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;
import uk.me.webpigeon.world.Tag;
import uk.me.webpigeon.world.World;

public class EvadePreditor extends Action {
	private FleeBehaviour behaviour;
	private Entity preditor;
	private Vector2D dest;


	// find the closest preditor
	// choose a path away from the preditor
	// execute a move in the direction away from the preditor
	// be alive
	
	
	public EvadePreditor(TreeNode<Double> utilCalc) {
		super(utilCalc);
		this.behaviour = new FleeBehaviour(null);
	}

	@Override
	public void executeStep(Entity entity, World world) {
		if (preditor == null) {
			preditor = world.getNearestEntityOfType(entity, Tag.HUNTER);
			if (preditor == null) {
				entity.setVelocity(new Vector2D(0, 0));
				behaviour.setTarget(null);
				return;
			}
			dest = preditor.getLocation();
		}
		
		double safeDistance = entity.getValue(Property.SIGHT_RANGE, 100);
		if (dest.dist(entity.getLocation()) < safeDistance) {
			behaviour.setTarget(dest);
			Vector2D forceDir = behaviour.process();
			entity.setVelocity(forceDir);
		} else {
			preditor = null;
			behaviour.setTarget(null);
			entity.setVelocity(new Vector2D(0, 0));
		}
	}
	
	@Override
	public void notifyStarted(Entity entity) {
		behaviour.bind(entity);
	}
	
	public void debugDraw(Graphics2D g) {
		if (preditor != null) {
			behaviour.debugDraw(g);
		}
	}

}
