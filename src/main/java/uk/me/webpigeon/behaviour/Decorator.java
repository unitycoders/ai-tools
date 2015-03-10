package uk.me.webpigeon.behaviour;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Piers on 09/03/2015.
 */
public abstract class Decorator extends TreeNode {

    // Use this link to the single child not the children list
    TreeNode child;

    public Decorator(TreeNode child) {
        super(0);
        this.child = child;
        // Call the super - this.addChild will except
        super.addChild(child);
    }

    /**
     * Cannot add children to a Decorator
     * @param node the node to add
     */
    @Override
    protected void addChild(TreeNode node) {
        throw new IllegalStateException("Decorators can't have new children");
    }

    /**
     * Always returns the child of the decorator
     * @param n A number that will promptly be ignored by this function
     * @return The child of the decorator - regardless of the value of n
     */
    @Override
    public TreeNode getChild(int n) {
        return this.child;
    }

    /**
     * Returns the number of children (1)
     * @return (1) its always 1
     */
    @Override
    public int getChildrenSize() {
        return 1;
    }
}
