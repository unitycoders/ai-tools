package uk.me.webpigeon.joseph;

import uk.me.webpigeon.behaviour.SequenceNode;
import uk.me.webpigeon.behaviour.TreeNode;
import uk.me.webpigeon.steering.SeekBehaviour;
import uk.me.webpigeon.steering.SteeringBehaviour;
import uk.me.webpigeon.util.Vector2D;

public class BehavourFactory {
	
	public static SteeringBehaviour buildEatBehavour() {
		SteeringBehaviour findPlant = new SeekBehaviour(new Vector2D(10,10));
		return findPlant;
	}

}
