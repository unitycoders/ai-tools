package uk.me.webpigeon.joseph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import uk.me.webpigeon.joseph.cow.Cow;
import uk.me.webpigeon.world.Entity;
import uk.me.webpigeon.world.EntityFactory;
import uk.me.webpigeon.world.World;
import uk.me.webpigeon.world.WorldComponent;

public class CowPopulationManager implements WorldComponent {
	private double epsilon = 0.02;
	private int cowPopulation;
	private List<Cow> cows;
	private List<Cow> newCows;
	
	public CowPopulationManager(int cowPopulation) {
		this.cowPopulation = cowPopulation;
		this.cows = new ArrayList<Cow>();
		this.newCows = new ArrayList<Cow>();
	}
	
	public void update(World world, int delta) {
		
		Iterator<Cow> cowItr = cows.iterator();
		while (cowItr.hasNext()) {
			Cow cow = cowItr.next();
			
			//remove dead cows from our population
			if(cow.isDead()) {
				cowItr.remove();
			}
		}
		
		// add any outstanding cows
		addWaitingCows(world);
	}
	
	private void addWaitingCows(World world) {
		Iterator<Cow> cowItr = newCows.iterator();
		while (cowItr.hasNext()) {
			Cow cow = cowItr.next();
			cows.add(cow);
			world.addEntity(cow);
			cowItr.remove();
		}
	}
	
	public void addMoreCows(int cowCount, World world) {
		// find out now many new cows we need
		//int replacementCowsNeeded = cowPopulation - cows.size();
		for (int i=0; i<cowCount; i++) {
			//get the best cow and mutate it's genome
			Cow parent = selectParent();
			double[] genome = parent==null?EntityFactory.buildDefaultGenome():parent.getGenome();
			//genome = mutateGenome(genome);
			
			//build a new cow with our new genome
			Cow newCow = EntityFactory.buildGenomeCow(800, 600, world, this, genome);
			newCows.add(newCow);
		}
	}
	
	private Cow selectParent() {
		Cow oldestCow = null;
		for (Cow cow : cows) {
			if (oldestCow == null) {
				//the first cow we see is the oldest cow
				oldestCow = cow;
			} else if (oldestCow.getAge() < cow.getAge()) {
				// if this cow is older than the oldest cow so far
				oldestCow = cow;
			}
		}
		return oldestCow;
	}
	
	private double[] mutateGenome(double[] genome) {
		Random r = new Random();
		
		double[] newGenome = new double[genome.length];
		for (int i=0; i<genome.length; i++) {
			newGenome[i] = genome[i] + r.nextGaussian() * epsilon;
		}
		
		return newGenome;
	}

	public void addCow(World world, double[] genome) {
		Cow newCow = EntityFactory.buildGenomeCow(800, 600, world, this, genome);
		newCows.add(newCow);
	}

	public int getPopMax() {
		return cowPopulation;
	}

	public int getPop() {
		return cows.size() + newCows.size();
	}
}
