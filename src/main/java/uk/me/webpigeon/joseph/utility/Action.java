package uk.me.webpigeon.joseph.utility;

import uk.me.webpigeon.joseph.cow.Cow;
import uk.me.webpigeon.world.Entity;
import uk.me.webpigeon.world.World;

public class Action {
	private UtilTreeNode utilCalc;
	
	public Action(UtilTreeNode utilCalc) {
		this.utilCalc = utilCalc;
	}

	public void executeStep(Entity entity, World world) {
		
	}
	
	public boolean isComplete() {
		return true;
	}

	public double getScore(Cow cow) {
		return utilCalc.eval(cow);
	}

	public void notifyStarted(Entity cow) {
		
	}
	
}
