package uk.me.webpigeon.joseph.utility.trees;

public interface TreeNode <T> {
	
	public void addChild(TreeNode<T> child);
	public int getChildCount();
	
	public boolean isLeaf();
	public T eval();

}
