package uk.me.webpigeon.joseph.utility;

import java.util.ArrayList;
import java.util.List;

import uk.me.webpigeon.joseph.cow.Cow;

public abstract class UtilTreeNode {
	protected List<UtilTreeNode> children;
	
	public UtilTreeNode() {
		this.children = new ArrayList<>();
	}
	
	public abstract Double eval(Cow cow);
	
	public void addChild(UtilTreeNode node) {
		assert node != null;
		children.add(node);
	}
	
	public int getChildCount() {
		return children.size();
	}

}
