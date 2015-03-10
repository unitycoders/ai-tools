package uk.me.webpigeon.joseph.cow;

import uk.me.webpigeon.joseph.utility.Action;
import uk.me.webpigeon.joseph.utility.UtilTreeNode;
import uk.me.webpigeon.steering.SteeringBehaviour;
import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;

public class SteeringAction extends Action {
	private static final Double VECTOR_SMOOTHING = 0.2;
	final SteeringBehaviour behaviour;

	public SteeringAction(UtilTreeNode utilCalc, SteeringBehaviour behaviour) {
		super(utilCalc);
		this.behaviour = behaviour;
	}
	
	public void executeStep(Entity entity) {		
		behaviour.bind(entity);
		Vector2D targetForce = behaviour.process();
		Vector2D velocity = Vector2D.add(targetForce, entity.getVelocity(), VECTOR_SMOOTHING);
		entity.setVelocity(velocity);
		behaviour.bind(null);
	}
}
