package uk.me.webpigeon.joseph.cow;

import uk.me.webpigeon.joseph.utility.trees.AbstractTreeNode;

public class InvertedCowProperty extends CowProperty {
	
	public InvertedCowProperty(Cow cow, Property prop) {
		super(cow, prop);
	}

	@Override
	public Double eval() {
		return 1 - super.eval();
	}

}
