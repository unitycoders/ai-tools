package uk.me.webpigeon.joseph.utility.trees;

public abstract class ImmutableTreeNode implements TreeNode<Double> {

	@Override
	public final void addChild(TreeNode<Double> child) {
		throw new IllegalArgumentException("Cannot add children - immutable");	
	}

	@Override
	public boolean isLeaf() {
		return getChildCount() == 0;
	}

}
