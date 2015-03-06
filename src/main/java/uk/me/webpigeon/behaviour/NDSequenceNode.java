package uk.me.webpigeon.behaviour;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Nondeterministic Sequence Node
 * 
 * Evaluate all nodes in order from 0 to random, if all nodes return true
 * then return true, else return false. If any node returns false then this node
 * returns false and evaluation stops immediately.
 */
public class NDSequenceNode extends SequenceNode {

	public Collection<TreeNode> getChildren() {
		List<TreeNode> collection = new ArrayList<TreeNode>(super.getChildren());
		Collections.shuffle(collection);
		return collection;
	}

}
