package uk.me.webpigeon.joseph.cow;

import uk.me.webpigeon.joseph.utility.Action;
import uk.me.webpigeon.joseph.utility.UtilTreeNode;
import uk.me.webpigeon.world.Entity;

public class Eat extends Action {
	
	public Eat(UtilTreeNode utilCalc) {
		super(utilCalc);
	}
	
	@Override
	public void executeStep(Entity entity) {
		// find the closest plant
		// head towards plant
		// eat the plant
		// be happy
	}

}
