package uk.me.webpigeon.steering;

import java.awt.Graphics2D;

import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;

/**
 * This interface defines steering behaviours.
 */
public interface SteeringBehaviour {

	/**
	 * Bind a behaviour to a given entity.
	 * 
	 * @param entity the entity this behavour is bound to
	 */
	public void bind(Entity entity);

	/**
	 * process this behaviour and return it's resultant vector.
	 * 
	 * The resultant vector should be a normalised position vector indicating the
	 * direction in which this behaviour would like the entity to move.
	 * 
	 * (be aware that this might need to change in the future as more complex behavours
	 * are implemented).
	 * 
	 * @return a normalised position vector.
	 */
	public Vector2D process();

	/**
	 * Draw debug visualisations for this behaviour.
	 * 
	 * Draw debug visualisations over the top of the world for this behaviour.
	 * The graphics's origin is the world origin and the units are the world units.
	 * 
	 * @param g the graphics objects to use to draw debug graphics
	 */
	public void debugDraw(Graphics2D g);

}
