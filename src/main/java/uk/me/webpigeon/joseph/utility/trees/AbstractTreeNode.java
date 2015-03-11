package uk.me.webpigeon.joseph.utility.trees;

import java.util.ArrayList;
import java.util.List;

import uk.me.webpigeon.joseph.cow.Cow;

public abstract class AbstractTreeNode implements TreeNode<Double> {
	protected List<TreeNode<Double>> children;
	
	public AbstractTreeNode() {
		this.children = new ArrayList<>();
	}
	
	public void addChild(TreeNode<Double> node) {
		assert node != null;
		children.add(node);
	}
	
	public int getChildCount() {
		return children.size();
	}
	
	public boolean isLeaf() {
		return children.isEmpty();
	}

}
