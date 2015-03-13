package uk.me.webpigeon.steering;

import uk.me.webpigeon.util.Vector2D;

public interface TargetedBehavour extends SteeringBehaviour {

	public void setTarget(Vector2D target);
}
