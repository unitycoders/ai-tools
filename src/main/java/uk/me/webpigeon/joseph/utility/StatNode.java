package uk.me.webpigeon.joseph.utility;

import uk.me.webpigeon.joseph.cow.Cow;
import uk.me.webpigeon.joseph.utility.trees.AbstractTreeNode;
import uk.me.webpigeon.joseph.utility.trees.TreeNode;

public class StatNode implements TreeNode<Double> {
	private String opr;
	private TreeNode<Double> left;
	private TreeNode<Double> right;
	
	public StatNode(String opr, TreeNode<Double> sub, TreeNode<Double> right){
		this.opr = opr;
		this.left = sub;
		this.right = right;	
	}

	@Override
	public Double eval() {
		switch (opr) {
			case "max":
				return Math.max(left.eval(), right.eval());
				
			case "min":
				return Math.min(left.eval(), right.eval());
				
			default:
				System.err.println("unknown oper: "+opr);
				return null;
		}
	}

	@Override
	public void addChild(TreeNode<Double> child) {
		throw new IllegalArgumentException("Cannot modify child nodes");
	}

	@Override
	public int getChildCount() {
		return 2;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

}
