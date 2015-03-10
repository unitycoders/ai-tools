package uk.me.webpigeon.joseph.utility;

import uk.me.webpigeon.joseph.cow.Cow;
import uk.me.webpigeon.world.Entity;

public class Action {
	private UtilTreeNode utilCalc;
	
	public Action(UtilTreeNode utilCalc) {
		this.utilCalc = utilCalc;
	}

	public void executeStep(Entity entity) {
		
	}
	
	public boolean isComplete() {
		return true;
	}

	public double getScore(Cow cow) {
		return utilCalc.eval(cow);
	}
	
}
