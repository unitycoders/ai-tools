package uk.me.webpigeon.joseph.cow;

import uk.me.webpigeon.joseph.utility.UtilTreeNode;

public class InvertedCowProperty extends CowProperty {
	
	public InvertedCowProperty(Property prop) {
		super(prop);
	}

	@Override
	public Double eval(Cow cow) {
		return 1 - super.eval(cow);
	}

}
