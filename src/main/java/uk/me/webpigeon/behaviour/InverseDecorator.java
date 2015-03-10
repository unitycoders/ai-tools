package uk.me.webpigeon.behaviour;

/**
 * Created by Piers on 09/03/2015.
 */
public class InverseDecorator extends Decorator{

    public InverseDecorator(TreeNode child) {
        super(child);
    }

    @Override
    public Boolean eval() {
        return !child.eval();
    }
}
