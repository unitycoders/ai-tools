package uk.me.webpigeon.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import uk.me.webpigeon.steering.SteeringBehaviour;
import uk.me.webpigeon.util.Vector2D;

public class SteeringEntity extends Entity {
	private static final Double VECTOR_SMOOTHING = 0.2;
	private static final Integer SIZE = 30;
	private SteeringBehaviour behaviour;
	private ArrayList<Vector2D> previousLocations = new ArrayList<>();

	public SteeringEntity(int x, int y, SteeringBehaviour behaviour) {
		this.location = new Vector2D(x, y, true);
		this.velocity = new Vector2D(0, 0, true);
		setBehavour(behaviour);
	}
	
	public final void setBehavour(SteeringBehaviour behaviour) {
		this.behaviour = behaviour;
		if (behaviour != null) {
			behaviour.bind(this);
		}
	}

	@Override
	public void update() {
		if (behaviour != null) {
			Vector2D targetForce = behaviour.process();
			setVelocity(targetForce);
		}
		super.update();
	}

	@Override
	public void drawLocal(Graphics2D graphics) {
		graphics.setColor(Color.WHITE);
		graphics.fillRect(-SIZE, -(int) (SIZE / 2.0), SIZE * 2, SIZE);
	}

	public void debugDraw(Graphics2D graphics) {
		graphics.setColor(Color.DARK_GRAY);
		for (int i = 0; i < previousLocations.size() - 1; i++) {
			graphics.drawOval((int) previousLocations.get(i).getX() - SIZE / 6,
					(int) previousLocations.get(i).getY() - SIZE / 6, SIZE / 3,
					SIZE / 3);
		}

		behaviour.debugDraw(graphics);
	}
	
	public void setVelocity(Vector2D velocity) {
		Vector2D newVelocity = Vector2D.add(velocity, velocity, VECTOR_SMOOTHING);
		super.setVelocity(newVelocity);
	}


}
