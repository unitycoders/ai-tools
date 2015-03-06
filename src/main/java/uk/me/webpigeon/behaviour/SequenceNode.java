package uk.me.webpigeon.behaviour;

/**
 * Sequence Node
 * 
 * Evaluate all nodes in order from 0 to N, if all nodes return true
 * then return true, else return false. If any node returns false then this node
 * returns false and evaluation stops immediately.
 */
public class SequenceNode extends TreeNode {

	public SequenceNode(TreeNode... children) {
		super(children.length);
		for (TreeNode child : children) {
			addChild(child);
		}
	}

	/**
	 * Evaluate all child nodes in order from 0 ... size.
	 * 
	 * If any child fails to evaluate then evaluation of subsequent
	 * children stop and this method returns false.
	 * 
	 * @return true if all nodes evaluate successfully
	 */
	@Override
	public Boolean eval() {

		Boolean result;
		for (TreeNode child : getChildren()) {
			result = child.eval();
			if (!result) {
				return false;
			}
		}

		return true;
	}

}
