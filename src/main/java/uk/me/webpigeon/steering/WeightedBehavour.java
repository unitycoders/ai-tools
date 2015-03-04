package uk.me.webpigeon.steering;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;

public class WeightedBehavour implements SteeringBehavour {
	private Map<SteeringBehavour, Double> behavours;
	
	public WeightedBehavour() {
		this.behavours = new HashMap<SteeringBehavour, Double>();
	}
	
	public void addBehavour(SteeringBehavour behavour, double weight) {
		behavours.put(behavour, weight);
	}

	public Vector2D process() {
		
		Vector2D total = new Vector2D();
		
		for (Map.Entry<SteeringBehavour, Double> entry : behavours.entrySet()) {
			SteeringBehavour behavour = entry.getKey();
			Double weight = entry.getValue();
			
			Vector2D result = Vector2D.multiply(behavour.process(), weight);
			total = Vector2D.add(total, result);
		}
		
		return total;
	}

	public void bind(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debugDraw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}
	

}
