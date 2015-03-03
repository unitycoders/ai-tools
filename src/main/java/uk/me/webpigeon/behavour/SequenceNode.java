package uk.me.webpigeon.behavour;

public class SequenceNode extends TreeNode {

	public SequenceNode(TreeNode ... children) {
		super(children.length);
		for (TreeNode child : children) {
			addChild(child);
		}
	}

	@Override
	public Boolean eval() {
		
		Boolean result = false;
		for (TreeNode child : getChildren()) {
			result = child.eval();
			if (!result) {
				return false;
			}
		}
		
		return true;
	}
	
	

}
