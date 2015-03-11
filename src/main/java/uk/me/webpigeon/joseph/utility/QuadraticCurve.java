package uk.me.webpigeon.joseph.utility;

import uk.me.webpigeon.joseph.utility.trees.TreeNode;
import uk.me.webpigeon.joseph.utility.trees.UtilCurve;

public class QuadraticCurve extends UtilCurve {
	private float m;
	private float k;
	
	public QuadraticCurve(TreeNode<Double> child, float m, float k) {
		super(child);
		this.m = m;
		this.k = k;
	}

	@Override
	public Double eval() {
		return Math.pow(child.eval()/m, k);
	}

}
