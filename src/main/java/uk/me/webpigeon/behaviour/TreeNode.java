package uk.me.webpigeon.behaviour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Base Behaviour Tree Node.
 * 
 * This node is used as the basis for all leaf and non-leaf nodes in our
 * behaviour tree implementation.
 */
public abstract class TreeNode {
	private List<TreeNode> children;

	/**
	 * Create a new TreeNode.
	 * 
	 * @param childrenC the list of child nodes
	 */
	public TreeNode(TreeNode... childrenC) {
		this.children = new ArrayList<TreeNode>(Arrays.asList(childrenC));
	}

	/**
	 * Create a new TreeNode.
	 * 
	 * @param size the initial size of the backing arraylist for children
	 */
	public TreeNode(int size) {
		this.children = new ArrayList<TreeNode>(size);
	}

	/**
	 * Get all children of this node.
	 * 
	 * This returns an empty collection in the event that this node is a
	 * leaf node. The collection returned is not modifiable.
	 * 
	 * @return the child nodes of this object
	 */
	public Collection<TreeNode> getChildren() {
		return Collections.unmodifiableCollection(children);
	}

	/**
	 * Add a child node to this Node.
	 * 
	 * The child will be added to the end of the list of current nodes.
	 * 
	 * @param node the node to add
	 */
	protected void addChild(TreeNode node) {
		assert node != null;
		children.add(node);
	}

	/**
	 * Get the nth child from this node.
	 * 
	 * This method assumes the child exists and is non-zero. Passing it a
	 * non-existent child ID will result in undefined behaviour.
	 * 
	 * @param n the number of the child to look for
	 * @return the child node
	 */
	public TreeNode getChild(int n) {
		assert n >= children.size();
		assert n < 0;
		return children.get(n);
	}

	/**
	 * Return the number of children this node has.
	 * 
	 * If this node is a leaf node, then this method will return 0.
	 * 
	 * @return the number of children this node has
	 */
	public int getChildrenSize() {
		return children.size();
	}

	/**
	 * If this node has no children, this returns True.
	 * 
	 * @return true if this node has no children, false otherwise
	 */
	public boolean isLeaf() {
		return children.isEmpty();
	}

	/**
	 * Evaluate this node and return true if it was successfully evaluated.
	 * 
	 * It's up to nodes to define what they consider to be "successfully evaluated".
	 * You should check the Javadoc for the node type.
	 * 
	 * @return true if successful, false otherwise.
	 */
	public abstract Boolean eval();

}
