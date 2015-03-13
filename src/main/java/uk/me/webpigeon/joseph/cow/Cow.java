package uk.me.webpigeon.joseph.cow;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import uk.me.webpigeon.joseph.BaseStats;
import uk.me.webpigeon.joseph.GenomeCoding;
import uk.me.webpigeon.joseph.utility.Action;
import uk.me.webpigeon.joseph.utility.UtilitySystem;
import uk.me.webpigeon.steering.SteeringBehaviour;
import uk.me.webpigeon.world.Entity;
import uk.me.webpigeon.world.SteeringEntity;

public class Cow extends SteeringEntity {
	private final static double HUNGER_RATE = 0.01f;
	
	private UtilitySystem util;
	private Action action;
	
	//private double saturation;
	private double[] genome;
	private Color colour;
	private int age;
	
	public Cow(int x, int y, UtilitySystem util, double[] genome) {
		super(x, y, null);
		this.genome = genome;
		this.util = util;
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
		double currentSat = getValue(Property.SATURATION, 0);
		if (currentSat <= 0) {
			//cow starved
			health = 0;
		}
		
		//stop cows from cheating by evolving negative hunger rates
		currentSat -= getValue(Property.METABOLISM, BaseStats.BASE_HUNGER);
		setValue(Property.SATURATION, currentSat);
	}
	
	private void processNeeds() {
		Action newAction = util.process(this);		
		if (newAction != null && !newAction.equals(action)){
			action = newAction;
			action.notifyStarted(this);
		}
		
		if (action != null) {			
			action.executeStep(this, world);
		}
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
		
		double satVal = getNormProp(Property.SATURATION);
		
		g.setColor(Color.BLUE);
		g.fillRect((int)location.x-20, (int)location.y+10, (int)(satVal*50), 5);
	}
	
	public void debugDraw(Graphics2D g) {
		if (action != null) {
			action.debugDraw(g);
		}
		
		int range = (int)getValue(Property.SIGHT_RANGE, 100);
		g.setColor(Color.WHITE);
		g.drawOval((int)location.x - range, (int)location.y - range, range*2, range*2);
	}

	public int getAge() {
		return age;
	}

	public double[] getGenome() {
		return genome;
	}

	/**public void addSaturation(int health) {
		double maxSat = getPropertyMax(Property.SATURATION);
		saturation = Math.min(maxSat, saturation+health);
	}*/
	
	
	public String toString() {
		return "cow["+action+"]";
	}
}
