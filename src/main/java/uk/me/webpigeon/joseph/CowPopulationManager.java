package uk.me.webpigeon.joseph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.me.webpigeon.world.Entity;
import uk.me.webpigeon.world.EntityFactory;
import uk.me.webpigeon.world.World;
import uk.me.webpigeon.world.WorldComponent;

public class CowPopulationManager implements WorldComponent {
	private int cowPopulation;
	private List<Cow> cows;
	
	public CowPopulationManager(int cowPopulation) {
		this.cowPopulation = cowPopulation;
		this.cows = new ArrayList<Cow>();
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
		
		// find out now many new cows we need
		int replacementCowsNeeded = cowPopulation - cows.size();
		for (int i=0; i<replacementCowsNeeded; i++) {
			Cow newCow = EntityFactory.buildCow(800, 600);
			cows.add(newCow);
			world.addEntity(newCow);
		}
		
	}

}
