package uk.me.webpigeon.joseph;

import java.awt.Color;
import java.awt.Graphics2D;

import uk.me.webpigeon.steering.SteeringBehaviour;
import uk.me.webpigeon.world.Entity;
import uk.me.webpigeon.world.SteeringEntity;

public class Cow extends SteeringEntity {
	private final static double MAX_SATURATION = 10;
	private final static double HUNGER_RATE = 0.01f;
	private double saturation;
	
	private Memory memory;
	
	public Cow(int x, int y, SteeringBehaviour behavour) {
		super(x, y, behavour);
		saturation = MAX_SATURATION;
		memory = new Memory();
	}

	@Override
	public void update() {
		processNeeds();
		
		super.update();
	}
	
	private void processNeeds() {
		if (saturation <= 0) {
			//cow starved
			health = 0;
		}
		saturation -= HUNGER_RATE;
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
		
		g.setColor(Color.BLUE);
		g.fillRect((int)location.x, (int)location.y, (int)saturation, 5);
	}

}
