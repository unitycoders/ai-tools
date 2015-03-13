package uk.me.webpigeon.joseph.cow;

import uk.me.webpigeon.joseph.utility.trees.AbstractTreeNode;
import uk.me.webpigeon.joseph.utility.trees.TreeNode;

public class InvertNode implements TreeNode<Double> {
	private TreeNode<Double> child;
	
	public InvertNode(TreeNode<Double> child) {
		this.child = child;
	}

	@Override
	public Double eval() {
		return 1 - child.eval();
	}

	@Override
	public void addChild(TreeNode<Double> child) {
		throw new RuntimeException();
	}

	@Override
	public int getChildCount() {
		return 1;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

}
