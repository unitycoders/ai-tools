package uk.me.webpigeon.piers;

import uk.me.webpigeon.behaviour.TreeNode;
import uk.me.webpigeon.world.Entity;

import java.awt.*;

/**
 * Created by Piers on 04/03/2015.
 * <p>
 * Agent that will use the offline evolved behavioural tree in controlling its
 * behaviour
 */
public class HunterAgent extends Entity {
    // How big we are
    int radius;

    // How healthy are we
    int health;

    // How hungry are we
    int hungerLevel;

    // How much energy do we have?
    int energyReserves;

    // How thirsty are we
    int thirstLevel;

    // The behaviour tree that governs how we behave
    private TreeNode behaviourTree;

    /**
     * Public constructor providing the tree to use to control this agent
     * @param behaviourTree
     */
    public HunterAgent(TreeNode behaviourTree) {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.RED);
        graphics.fillOval((int) getX(), (int) getY(), radius, radius);
    }
}
