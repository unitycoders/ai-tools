package uk.me.webpigeon.joseph.utility.trees;

public abstract class UtilCurve extends ImmutableTreeNode {
	protected TreeNode<Double> child;
	
	public UtilCurve(TreeNode<Double> child) {
		assert child != null;
		this.child = child;
	}

	@Override
	public int getChildCount() {
		return 1;
	}

}
