package uk.me.webpigeon.behaviour;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Nondeterministic Selector Node
 * 
 * Evaluate all nodes in order from 0 to random, if any node returns true
 * stop evaluating and returns true. If all nodes return false then this node
 * returns false.
 */
public class NDSelectorNode extends SelectorNode {

	public Collection<TreeNode> getChildren() {
		List<TreeNode> collection = new ArrayList<TreeNode>(super.getChildren());
		Collections.shuffle(collection);
		return collection;
	}

}
