package uk.me.webpigeon.behaviour;

/**
 * Created by Piers on 09/03/2015.
 */
public class RepeatDecorator extends Decorator {
    private int repetitions;
    private int ticksSoFar = 0;

    public RepeatDecorator(TreeNode child, int repetitions) {
        super(child);
        this.repetitions = repetitions;
    }

    @Override
    public Boolean eval() {
        if (ticksSoFar < repetitions) {
            ticksSoFar++;
        }
        return child.eval();
    }
}
