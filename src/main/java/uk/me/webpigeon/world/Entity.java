package uk.me.webpigeon.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.me.webpigeon.joseph.cow.Property;
import uk.me.webpigeon.util.Vector2D;

/**
 * Base abstract class for all entity objects in the world
 * <p>
 * Created by Piers on 03/03/2015.
 */
public abstract class Entity {
	
	// The GridWorld in all its glory
	protected World world;
	protected List<Vector2D> previousLocations;
	protected Map<Property, Double> properties;
	protected Map<Property, Double> propertyLimits;
	protected Set<Tag> tags;

	// Location of the entity
	protected Vector2D location;
	protected Vector2D velocity;
	protected double rotation;

	// Health
	protected int health;

	public Entity(double x, double y, EnumSet<Tag> tags) {
		this.location = new Vector2D(x, y, true);
		this.velocity = new Vector2D(0, 0, true);
		this.health = 100;
		this.previousLocations = new ArrayList<>();
		
		this.tags = tags;
		this.properties = new EnumMap<>(Property.class);
		this.propertyLimits = new EnumMap<>(Property.class);
	}
	
	public Entity(double x, double y) {
		this(x, y, EnumSet.noneOf(Tag.class));
	}
	
	public Entity() {
		this(0, 0);
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
	
	public boolean hasTag(Tag tag) {
		return tags.contains(tag);
	}
	
	public boolean hasAllTags(Tag ... extras) {
		List<Tag> tags = Arrays.asList(extras);
		return tags.containsAll(tags);
	}
	
	public int getZIndex() {
		return 0;
	}
	
	public Double setValue(Property prop, Double newValue) {
		double maxVal = getLimit(prop, Double.MAX_VALUE);
		double storeVal = Math.min(maxVal, newValue);
		return properties.put(prop, storeVal);
	}
	
	public Double getValue(Property prop) {
		return properties.get(prop);
	}
	
	public double getValue(Property prop, double defaultValue) {
		Double val = getValue(prop);
		if (val == null) {
			assert getLimit(prop) == null || getLimit(prop) > defaultValue;
			return defaultValue;
		}
		return val;
	}
	
	public Double setLimit(Property prop, Double newValue) {
		return propertyLimits.put(prop, newValue);
	}
	
	public Double getLimit(Property prop) {
		return propertyLimits.get(prop);
	}
	
	public double getLimit(Property prop, double defaultVal) {
		Double limit = getLimit(prop);
		if (limit == null) {
			return defaultVal;
		}
		return limit;
	}

	public double getNormProp(Property prop) {
		double maxVal = getLimit(prop, Double.MAX_VALUE);
		double val = getValue(prop, Double.MAX_VALUE);
		
		if (maxVal == 0) {
			return 0.0;
		}
		
		return val / maxVal;
	}

	public void setHealth(int newHealth) {
		health = newHealth;
	}
	
}
