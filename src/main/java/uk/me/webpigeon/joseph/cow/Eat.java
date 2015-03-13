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

public class Eat extends TargetingAction {
	private static final Double EAT_RANGE = 2.0;
	
	public Eat(TreeNode<Double> utilFunction) {
		super(utilFunction, new SeekBehaviour(null), Tag.GRASS);
	}
	
	@Override
	protected void targetingComplete(World world, Entity target) {
		if (target == null || target.isDead()) {
			return;
		}
		
		double currSat = us.getValue(Property.SATURATION, 0);
		currSat += target.getHealth();
		us.setValue(Property.SATURATION, currSat);
		world.removeEntity(target);
	}
	
	@Override
	protected boolean isCorrectDistance(Entity us, Entity target) {
		Vector2D dest = target.getLocation();
		return dest.dist(us.getLocation()) <= EAT_RANGE;
	}
}
