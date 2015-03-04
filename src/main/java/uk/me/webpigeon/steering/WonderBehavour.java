package uk.me.webpigeon.steering;

import java.util.Random;

import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;

public class WonderBehavour implements SteeringBehavour {
	private double wanderRadius = 10;
	private double wanderDistance = 3;
	private double jitter = 1;
	private Entity entity;
	
	private Vector2D c;
	private Vector2D wanders;
	private SeekBehavour seek;
	private Random random;
	
	public WonderBehavour() {
		this.wanderDistance = 0.5;
		this.seek = new SeekBehavour(null);
		this.wanders = new Vector2D();
		this.random = new Random();
	}

	@Override
	public void bind(Entity entity) {
		this.entity = entity;
		seek.bind(entity);
	}

	@Override
	public Vector2D process() {
		Vector2D ourPosition = new Vector2D(entity.getX(), entity.getY());
		
		Vector2D v = new Vector2D(0.1, 0.1);
		v.normalise();
		
		Vector2D pw = Vector2D.add(ourPosition, Vector2D.multiply(v, wanderDistance));
		
		Vector2D a = new Vector2D(jitter * (random.nextDouble() * 2) - 1, jitter * (random.nextDouble() * 2) - 1);
		Vector2D wanders2 = Vector2D.add(wanders, a);
		Vector2D cNorm = new Vector2D(wanders2);
		cNorm.normalise();
		System.out.println("banana "+a + " " + cNorm.mag());
		wanders = Vector2D.multiply(cNorm, wanderRadius);
		
		//set the new target
		Vector2D seekTarget = Vector2D.add(Vector2D.add(ourPosition, Vector2D.multiply(v, wanderDistance)),wanders);
		System.out.println(ourPosition+" "+seekTarget);
		seek.setTarget(seekTarget);
		return seek.process();
	}

}
