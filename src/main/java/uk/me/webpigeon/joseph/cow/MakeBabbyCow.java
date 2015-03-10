package uk.me.webpigeon.joseph.cow;

import uk.me.webpigeon.joseph.CowPopulationManager;
import uk.me.webpigeon.joseph.utility.Action;
import uk.me.webpigeon.joseph.utility.UtilTreeNode;
import uk.me.webpigeon.world.Entity;
import uk.me.webpigeon.world.World;

public class MakeBabbyCow extends Action {
	private CowPopulationManager population;
	private boolean cowAdded;

	public MakeBabbyCow(UtilTreeNode utilCalc, CowPopulationManager population) {
		super(utilCalc);
		this.population = population;
	}

	@Override
	public void executeStep(Entity entity, World world) {
		if (!cowAdded) {
			Cow cow = (Cow)entity;
			population.addCow(cow.getGenome());
		}
	}

	@Override
	public void notifyStarted(Entity cow) {
		cowAdded = false;
	}
	
}
