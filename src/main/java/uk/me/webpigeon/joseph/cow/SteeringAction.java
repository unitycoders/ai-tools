package uk.me.webpigeon.joseph.cow;

import uk.me.webpigeon.joseph.utility.Action;
import uk.me.webpigeon.joseph.utility.trees.AbstractTreeNode;
import uk.me.webpigeon.joseph.utility.trees.TreeNode;
import uk.me.webpigeon.steering.SteeringBehaviour;
import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;
import uk.me.webpigeon.world.World;

public class SteeringAction extends Action {
	final SteeringBehaviour behaviour;

	public SteeringAction(TreeNode<Double> utilCalc, SteeringBehaviour behaviour) {
		super(utilCalc);
		this.behaviour = behaviour;
	}
	
	@Override
	public void executeStep(Entity entity, World world) {	
		Vector2D targetForce = behaviour.process();
		entity.setVelocity(targetForce);
	}
	
	@Override
	public void notifyStarted(Entity entity) {
		behaviour.bind(entity);
	}
	
	public String toString() {
		return "Action["+behaviour+"]";
	}
}
