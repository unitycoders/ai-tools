package uk.me.webpigeon.joseph.utility;

import uk.me.webpigeon.joseph.cow.Cow;

public class MathNode extends UtilTreeNode {
	private char opr;
	private UtilTreeNode left;
	private UtilTreeNode right;
	
	public MathNode(char opr, UtilTreeNode left, UtilTreeNode right){
		this.opr = opr;
		this.left = left;
		this.right = right;	
	}

	@Override
	public Double eval(Cow cow) {
		switch (opr) {
			case '+':
				return left.eval(cow) + right.eval(cow);
				
			case '-':
				return left.eval(cow) - right.eval(cow);
				
			case '/':
				return left.eval(cow) / right.eval(cow);
				
			case '*':
				return left.eval(cow) * right.eval(cow);
				
			default:
				return null;
		}
	}

}
