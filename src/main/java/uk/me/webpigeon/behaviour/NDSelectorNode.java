package uk.me.webpigeon.behaviour;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NDSelectorNode extends SelectorNode {

	public Collection<TreeNode> getChildren() {
		List<TreeNode> collection = new ArrayList<TreeNode>(super.getChildren());
		Collections.shuffle(collection);
		return collection;
	}

}
