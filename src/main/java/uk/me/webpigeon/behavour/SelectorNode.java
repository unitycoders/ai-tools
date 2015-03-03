package uk.me.webpigeon.behavour;

public class SelectorNode extends TreeNode {

	public SelectorNode(TreeNode ... children) {
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
			if (result) {
				return true;
			}
		}
		
		return false;
	}
	

}
