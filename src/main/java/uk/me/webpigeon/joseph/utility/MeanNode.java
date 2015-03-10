package uk.me.webpigeon.joseph.utility;

import uk.me.webpigeon.behaviour.TreeNode;
import uk.me.webpigeon.joseph.cow.Cow;

public class MeanNode extends UtilTreeNode {

	@Override
	public Double eval(Cow cow) {
		double total = 0.0;
		for (UtilTreeNode node : children) {
			total += node.eval(cow);
		}
		
		return total / children.size();
	}

}
