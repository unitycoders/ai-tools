package uk.me.webpigeon.joseph.cow;

import uk.me.webpigeon.joseph.CowPopulationManager;
import uk.me.webpigeon.joseph.utility.trees.AbstractTreeNode;

public class CowProperty extends AbstractTreeNode {
	private Cow cow;
	private Property prop;
	
	public CowProperty(Cow cow, Property prop) {
		this.cow = cow;
		this.prop = prop;
	}

	@Override
	public Double eval() {
		return cow.getProperty(prop) / cow.getPropertyMax(prop);
	}

}
