package uk.me.webpigeon.world;

import uk.me.webpigeon.behavour.TreeNode;

import java.awt.*;

/**
 * Base abstract class for all entity objects in the world
 *
 * Created by Piers on 03/03/2015.
 */
public abstract class Entity {

    // The World in all its glory
    protected World world;

    // X Co-Ordinate
    protected int x;
    // Y Co-Ordinate
    protected int y;

    // Health
    protected int health;


    public abstract void update(TreeNode behaviourRoot);

    public abstract void draw(Graphics2D graphics);

}
