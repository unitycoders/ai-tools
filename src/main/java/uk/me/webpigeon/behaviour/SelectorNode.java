package uk.me.webpigeon.behaviour;

/**
 * Selector Node 
 * 
 * Evaluate all nodes in order from 0 to N, if any node returns true
 * stop evaluating and returns true. If all nodes return false then this node
 * returns false.
 */
public class SelectorNode extends TreeNode {

	public SelectorNode(TreeNode... children) {
		super(children.length);
		for (TreeNode child : children) {
			addChild(child);
		}
	}

	/**
	 * Evaluate all child nodes in order from 0 ... size.
	 * 
	 * When any child node evaluates successfully, this method immediately
	 * returns True. 
	 * 
	 * @return true if any node evaluates successfully
	 */
	@Override
	public Boolean eval() {

		Boolean result = false;
		for (TreeNode child : getChildren()) {
			result = child.eval();
			if (result) {
				return true;
			}
		}

		return false;
	}

}
