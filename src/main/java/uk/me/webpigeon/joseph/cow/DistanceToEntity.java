package uk.me.webpigeon.joseph.cow;

import uk.me.webpigeon.joseph.utility.trees.TreeNode;
import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;
import uk.me.webpigeon.world.World;

public class DistanceToEntity implements TreeNode<Double> {
	private Entity us;
	private World world;
	private Class clazz;

	public DistanceToEntity(Entity us, World world, Class clazz) {
		this.us = us;
		this.world = world;
		this.clazz = clazz;
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
		return 0.0;
		/*
		//TODO do something better with this
		double maxDist = 800;
		
		Entity closestOfType = world.getNearestEntityOfType(us, clazz);
		if (closestOfType == null) {
			return 1.0;
		}
		
		Vector2D myLocation = us.getLocation();
		Vector2D theirLocation = closestOfType.getLocation();
		return myLocation.dist(theirLocation) / maxDist;*/
	}

}
