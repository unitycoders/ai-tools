package uk.me.webpigeon.steering;

import java.awt.Graphics2D;
import java.util.Random;

import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;

public class WonderBehaviour implements SteeringBehaviour {
	private double wanderRadius = 10;
	private double wanderDistance = 3;
	private double jitter = 1;
	private Entity entity;
	
	private Vector2D c;
	private Vector2D wanders;
	private SeekBehaviour seek;
	private Random random;
	
	public WonderBehaviour() {
		this.wanderDistance = 0.5;
		this.seek = new SeekBehaviour(null);
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
		
		Vector2D v = entity.getVelocity();
		//v.normalise();
		
		Vector2D pw = Vector2D.add(ourPosition, Vector2D.multiply(v, wanderDistance));
		
		double x = (random.nextDouble() * 2) - 1;
		double y = (random.nextDouble() * 2) - 1;
		
		Vector2D a = new Vector2D(jitter * x, jitter * y);
		Vector2D wanders2 = Vector2D.add(wanders, a);
		Vector2D cNorm = new Vector2D(wanders2, true);
		cNorm.normalise();
		wanders = Vector2D.multiply(cNorm, wanderRadius);
		
		//set the new target
		Vector2D seekTarget = Vector2D.add(Vector2D.add(ourPosition, Vector2D.multiply(v, wanderDistance)),wanders);
		seek.setTarget(seekTarget);
		return seek.process();
	}

	@Override
	public void debugDraw(Graphics2D g) {
		// TODO Auto-generated method stub
		
		g.drawOval((int)wanders.x, (int)wanders.y, (int)wanderRadius*2, (int)wanderRadius*2);
		
		seek.debugDraw(g);
	}

}
