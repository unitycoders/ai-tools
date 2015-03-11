package uk.me.webpigeon.joseph.utility;

import uk.me.webpigeon.joseph.utility.trees.TreeNode;
import uk.me.webpigeon.joseph.utility.trees.UtilCurve;

public class QuadraticCurve extends UtilCurve {
	private double m;
	private double k;
	
	public QuadraticCurve(TreeNode<Double> child, double m, double k) {
		super(child);
		this.m = m;
		this.k = k;
	}

	@Override
	public Double eval() {
		return Math.pow(child.eval()/m, k);
	}

}
