package uk.me.webpigeon.steering;

import java.util.HashMap;
import java.util.Map;

import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;

public class WeightedBehaviour implements SteeringBehaviour {
	private Map<SteeringBehaviour, Double> behavours;
	
	public WeightedBehaviour() {
		this.behavours = new HashMap<SteeringBehaviour, Double>();
	}
	
	public void addBehavour(SteeringBehaviour behavour, double weight) {
		behavours.put(behavour, weight);
	}

	public Vector2D process() {
		
		Vector2D total = new Vector2D();
		
		for (Map.Entry<SteeringBehaviour, Double> entry : behavours.entrySet()) {
			SteeringBehaviour behavour = entry.getKey();
			Double weight = entry.getValue();
			
			Vector2D result = Vector2D.multiply(behavour.process(), weight);
			total = Vector2D.add(total, result);
		}
		
		return total;
	}

	public void bind(Entity entity) {
		// TODO Auto-generated method stub
		
	}
	

}
