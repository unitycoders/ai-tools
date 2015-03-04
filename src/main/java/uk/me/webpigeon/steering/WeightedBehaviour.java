package uk.me.webpigeon.steering;

import java.awt.Graphics2D;
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
		
		total.normalise();
		
		return total;
	}

	public void bind(Entity entity) {
		for (SteeringBehaviour sb : behavours.keySet()) {
			sb.bind(entity);
		}
	}

	@Override
	public void debugDraw(Graphics2D g) {
		for (SteeringBehaviour sb : behavours.keySet()) {
			sb.debugDraw(g);
		}
	}
	

}
