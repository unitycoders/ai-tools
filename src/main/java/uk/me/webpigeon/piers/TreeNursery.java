package uk.me.webpigeon.piers;

import uk.me.webpigeon.behaviour.TreeNode;

/**
 * Created by Piers on 07/03/2015.
 * <p>
 * Grows behaviour trees for use in hunters
 */
public class TreeNursery {

    private int maxTreeDepth = 5;
    private int maxBranch = 5;

    public TreeNursery(int maxTreeDepth, int maxBranch) {
        this.maxTreeDepth = maxTreeDepth;
        this.maxBranch = maxBranch;
    }

    public TreeNode growNewTree() {
        return null;
    }

    public TreeNode[] growNewPopulation(int size) {
        TreeNode[] population = new TreeNode[size];
        for (int i = 0; i < size; i++) population[i] = growNewTree();
        return population;
    }

}
