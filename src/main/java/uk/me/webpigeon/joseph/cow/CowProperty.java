package uk.me.webpigeon.joseph.cow;

import uk.me.webpigeon.joseph.utility.UtilTreeNode;

public class CowProperty extends UtilTreeNode {
	private Property prop;
	
	public CowProperty(Property prop) {
		this.prop = prop;
	}

	@Override
	public Double eval(Cow cow) {
		return cow.getProperty(prop) / cow.getPropertyMax(prop);
	}

}
