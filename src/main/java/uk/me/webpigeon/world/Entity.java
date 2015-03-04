package uk.me.webpigeon.world;

import uk.me.webpigeon.behavour.TreeNode;
import uk.me.webpigeon.util.Vector2D;

import java.awt.*;

/**
 * Base abstract class for all entity objects in the world
 *
 * Created by Piers on 03/03/2015.
 */
public abstract class Entity {

    // The GridWorld in all its glory
    protected World world;

    // X Co-Ordinate
    @Deprecated
    protected double x;
    // Y Co-Ordinate
    @Deprecated
    protected double y;
    // Location of the entity
    protected Vector2D location;

    // Health
    protected int health;


    public abstract void update();

    public abstract void draw(Graphics2D graphics);
    
    /**
     * Bind an entity to a world.
     * 
     * This method is called by the world when the entity gets added to the world.
     * 
     * @param world the world the entity now belongs to
     */
    void bind(World world) {
    	this.world = world;
    }

	public double getX() {
		return x;
	}

	
	public double getY() {
		return y;
	}
}
