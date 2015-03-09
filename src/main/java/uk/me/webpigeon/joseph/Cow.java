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
	
	private double[] genome;
	private Memory memory;
	private Color colour;
	private int age;
	
	public Cow(int x, int y, SteeringBehaviour behavour) {
		super(x, y, behavour);
		saturation = MAX_SATURATION;
		memory = new Memory();
	}
	
	public Cow(int x, int y, SteeringBehaviour bh, double[] genome) {
		super(x, y, bh);
		this.genome = genome;
		this.saturation = genome[GenomeCoding.MAX_SAT_ID];
		memory = new Memory();
	}

	@Override
	public void update() {
		processNeeds();
		age += 1;
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

	public int getAge() {
		return age;
	}

	public double[] getGenome() {
		return genome;
	}

}
