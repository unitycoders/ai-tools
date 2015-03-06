package uk.me.webpigeon.behaviour;

public class SequenceNode extends TreeNode {

	public SequenceNode(TreeNode... children) {
		super(children.length);
		for (TreeNode child : children) {
			addChild(child);
		}
	}

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
