package uk.me.webpigeon.joseph.cow;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import uk.me.webpigeon.joseph.GenomeCoding;
import uk.me.webpigeon.joseph.Memory;
import uk.me.webpigeon.joseph.utility.Action;
import uk.me.webpigeon.joseph.utility.Need;
import uk.me.webpigeon.joseph.utility.UtilitySystem;
import uk.me.webpigeon.steering.SteeringBehaviour;
import uk.me.webpigeon.world.Entity;
import uk.me.webpigeon.world.SteeringEntity;

public class Cow extends SteeringEntity {
	private final static double MAX_SATURATION = 10;
	private final static double HUNGER_RATE = 0.01f;
	
	private List<Action> actions;
	private UtilitySystem util;
	private Action action;
	
	private double saturation;
	private double[] genome;
	private Memory memory;
	private Color colour;
	private int age;
	
	public Cow(int x, int y, SteeringBehaviour behavour) {
		this(x, y, behavour, new double[]{MAX_SATURATION, HUNGER_RATE});
	}
	
	public Cow(int x, int y, SteeringBehaviour bh, double[] genome) {
		super(x, y, bh);
		this.genome = genome;
		this.saturation = genome[GenomeCoding.MAX_SAT_ID];
		this.util = new UtilitySystem();
		this.actions = new ArrayList<Action>();
		memory = new Memory();
	}

	@Override
	public void update() {
		processLife();
		processNeeds();
		super.update();
	}
	
	/**
	 * Update this cow's game state
	 */
	private void processLife() {
		age += 1;
		if (saturation <= 0) {
			//cow starved
			health = 0;
		}
		
		//stop cows from cheating by evolving negative hunger rates
		saturation -= Math.abs(genome[GenomeCoding.HUNGER_RATE]);
	}
	
	private void processNeeds() {
		if (action != null && action.isComplete()) {
			action = null;
		}
		
		//TODO hook up utility system here
		action = util.process(actions);
		if (action == null) {
			return;
		}
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
