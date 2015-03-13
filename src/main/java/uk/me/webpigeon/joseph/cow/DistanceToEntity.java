package uk.me.webpigeon.joseph.cow;

import uk.me.webpigeon.joseph.utility.trees.TreeNode;
import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;
import uk.me.webpigeon.world.Tag;
import uk.me.webpigeon.world.World;

public class DistanceToEntity implements TreeNode<Double> {
	private Entity us;
	private World world;
	private Tag type;

	public DistanceToEntity(Entity us, World world, Tag type) {
		this.us = us;
		this.world = world;
		this.type = type;
	}
	
	@Override
	public void addChild(TreeNode<Double> child) {
		throw new IllegalArgumentException("Cannot add children to leaf nodes");
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public Double eval() {
		Entity other = world.getNearestEntityOfType(us, type);
		if(other == null) {
			return 1.0;
		}
		
		double maxSight = us.getLimit(Property.SIGHT_RANGE, 100);
		double distance = us.getLocation().dist(other.getLocation());
		if (distance > maxSight) {
			return 1.0; //out of range
		}
		
		return distance/maxSight;
	}

}
