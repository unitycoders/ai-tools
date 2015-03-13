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

public class EvadePreditor extends TargetingAction {
	
	public EvadePreditor(TreeNode<Double> utilCalc, Tag targetType) {
		super(utilCalc, new FleeBehaviour(null), targetType);
	}

	@Override
	protected boolean isCorrectDistance(Entity us, Entity target) {
		double safeDistance = us.getValue(Property.SIGHT_RANGE, 100);
		Vector2D targetLocation = target.getLocation();
		return (targetLocation.dist(us.getLocation()) > safeDistance);
	}

}
