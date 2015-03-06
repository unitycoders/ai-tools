package uk.me.webpigeon.world;

import java.awt.Graphics2D;

import uk.me.webpigeon.util.Vector2D;

/**
 * Base abstract class for all entity objects in the world
 *
 * Created by Piers on 03/03/2015.
 */
public abstract class Entity {

	// The GridWorld in all its glory
	protected World world;

	// Location of the entity
	protected Vector2D location;
	protected Vector2D velocity;

	// Health
	protected int health;

	public Entity() {
		this.location = new Vector2D();
	}

	public abstract void update();

	public abstract void draw(Graphics2D graphics);

	public void debugDraw(Graphics2D g) {
	}

	/**
	 * Bind an entity to a world.
	 * 
	 * This method is called by the world when the entity gets added to the
	 * world.
	 * 
	 * @param world
	 *            the world the entity now belongs to
	 */
	void bind(World world) {
		this.world = world;
	}

	public double getX() {
		return location.getX();
	}

	public double getY() {
		return location.getY();
	}

	public Vector2D getLocation() {
		return location;
	}

	public Vector2D getVelocity() {
		return velocity;
	}

}
