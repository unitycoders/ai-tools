package uk.me.webpigeon.joseph.utility;

import uk.me.webpigeon.joseph.utility.trees.AbstractTreeNode;
import uk.me.webpigeon.joseph.utility.trees.TreeNode;

public class MeanNode extends AbstractTreeNode {

	public MeanNode(TreeNode<Double> ... children) {
		for (TreeNode<Double> child : children) {
			addChild(child);
		}
	}

	@Override
	public Double eval() {
		double total = 0.0;
		for (TreeNode<Double> node : children) {
			total += node.eval();
		}
		
		return total / children.size();
	}

}
