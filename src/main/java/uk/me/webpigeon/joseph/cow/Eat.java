package uk.me.webpigeon.joseph.cow;

import java.awt.Graphics2D;

import uk.me.webpigeon.joseph.utility.Action;
import uk.me.webpigeon.joseph.utility.trees.AbstractTreeNode;
import uk.me.webpigeon.joseph.utility.trees.TreeNode;
import uk.me.webpigeon.steering.SeekBehaviour;
import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;
import uk.me.webpigeon.world.GrassEntity;
import uk.me.webpigeon.world.Tag;
import uk.me.webpigeon.world.World;

public class Eat extends Action {
	private static final Double EAT_RANGE = 2.0;
	SeekBehaviour behaviour;
	Entity plant;
	Vector2D dest;
	
	public Eat(TreeNode<Double> utilFunction) {
		super(utilFunction);
		behaviour = new SeekBehaviour(null);
	}
	
	@Override
	public void executeStep(Entity entity, World world) {
		if (plant == null) {
			plant = world.getNearestEntityOfType(entity, Tag.GRASS);
			if (plant == null) {
				behaviour.setTarget(null);
				return;
			}
			dest = plant.getLocation();
		}
		
		if (dest.dist(entity.getLocation()) > EAT_RANGE) {
			behaviour.setTarget(dest);
			Vector2D forceDir = behaviour.process();
			entity.setVelocity(forceDir);
		} else {
			//we can eat the plant now ^^
			double currSat = entity.getValue(Property.SATURATION, 0);
			currSat += plant.getHealth();
			entity.setValue(Property.SATURATION, currSat);
			world.removeEntity(plant);
			
			plant = null;
			dest = null;
		}
		
		if (plant != null && plant.isDead()) {
			plant = null;
		}
		
	}
	
	@Override
	public void notifyStarted(Entity entity) {
		behaviour.bind(entity);
	}

	@Override
	public void debugDraw(Graphics2D g) {
		behaviour.debugDraw(g);
	}
}
