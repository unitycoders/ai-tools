package uk.me.webpigeon.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import uk.me.webpigeon.util.Vector2D;

/**
 * Base abstract class for all entity objects in the world
 *
 * Created by Piers on 03/03/2015.
 */
public abstract class Entity {

	// The GridWorld in all its glory
	protected World world;
	protected List<Vector2D> previousLocations;

	// Location of the entity
	protected Vector2D location;
	protected Vector2D velocity;
	protected double rotation;

	// Health
	protected int health;

	public Entity() {
		this.location = new Vector2D();
		this.health = 100;
		this.previousLocations = new ArrayList<>();
	}

	public void update() {
		location.add(velocity);
		rotation = velocity.angleBetween(new Vector2D(1, 0));
		previousLocations.add(new Vector2D(location));
		location.wrap(world.width, world.height);
	}

	public void draw(Graphics2D graphics) {
		Graphics2D sandbox = (Graphics2D) graphics.create();
		sandbox.translate(location.getX(), location.getY());

		// TODO piers - can you look at this, i'm assuming theta will be the
		// rotation, but it looks wrong
		// Vector2D polarVel = Vector2D.toPolar(velocity);
		double theta = velocity.getTheta();
		sandbox.rotate(Math.toRadians(rotation));
		drawLocal(sandbox);
		sandbox.rotate(Math.toRadians(-rotation));
		sandbox.translate(-location.getX(), -location.getY());
	}
	
	public void drawLocal(Graphics2D graphics) {}

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

	public boolean isDead() {
		return health <= 0;
	}

	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}

	public int getHealth() {
		return health;
	}

}
