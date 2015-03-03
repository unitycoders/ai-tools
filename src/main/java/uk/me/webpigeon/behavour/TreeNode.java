package uk.me.webpigeon.behavour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class TreeNode {
	private List<TreeNode> children;
	
	public TreeNode(TreeNode ... childrenC) {
		this.children = new ArrayList<TreeNode>(Arrays.asList(childrenC));
	}
	
	public TreeNode(int size) {
		this.children = new ArrayList<TreeNode>(size);
	}
	
	public Collection<TreeNode> getChildren() {
		return Collections.unmodifiableCollection(children);
	}
	
	protected void addChild(TreeNode node) {
		children.add(node);
	}
	
	public TreeNode getChild(int i) {
		return children.get(i);
	}
	
	public int getChildrenSize() {
		return children.size();
	}
	
	public boolean isLeaf() {
		return children.isEmpty();
	}

	public abstract Boolean eval();
	
}
