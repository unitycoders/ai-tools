package uk.me.webpigeon.joseph.utility;

import uk.me.webpigeon.joseph.cow.Cow;
import uk.me.webpigeon.joseph.utility.trees.AbstractTreeNode;
import uk.me.webpigeon.joseph.utility.trees.TreeNode;

public class MathNode implements TreeNode<Double> {
	private char opr;
	private TreeNode<Double> left;
	private TreeNode<Double> right;
	
	public MathNode(char opr, TreeNode<Double> sub, TreeNode<Double> right){
		this.opr = opr;
		this.left = sub;
		this.right = right;	
	}

	@Override
	public Double eval() {
		switch (opr) {
			case '+':
				return left.eval() + right.eval();
				
			case '-':
				return left.eval() - right.eval();
				
			case '/':
				return left.eval() / right.eval();
				
			case '*':
				return left.eval() * right.eval();
				
			default:
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
