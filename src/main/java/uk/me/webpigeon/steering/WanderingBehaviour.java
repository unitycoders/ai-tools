package uk.me.webpigeon.steering;

import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;

import java.util.Random;

/**
 * Created by Piers on 04/03/2015.
 */
public class WanderingBehaviour implements SteeringBehavour {

    private double wanderRadius = 50;
    private double wanderDistance = 50;
    private double angleJitter = 5;
    private Entity entity;

    // random vector - TODO May need to convert 360 to radians
    private Vector2D c = Vector2D.getRandomPolar(Math.toRadians(360), 1, 1, true);
    private SeekBehavour seek = new SeekBehavour(null);
    private Random random = new Random();

    @Override
    public Vector2D process() {
        // Obtain information about the entity
        Vector2D ourPosition = new Vector2D(entity.getX(), entity.getY());
        Vector2D ourVelocity = new Vector2D(entity.getVelocity(), true);

        // Modify ourVelocity to reflect the difference between Position and center of circle
        ourVelocity.normalise();
        ourVelocity.multiply(wanderDistance);
        // Use that information to calculate the center of the circle
        Vector2D centerOfCircle = Vector2D.add(ourPosition, ourVelocity);
        // Vary c by jittering it a little
        c.rotate(Math.toRadians((random.nextDouble() * 2 - 1) * angleJitter));
        // c is a unit vector in its own co-ordinate system - bring it back
        Vector2D seekPosition =
                Vector2D.add(
                        centerOfCircle,
                        Vector2D.multiply(Vector2D.toCartesian(c), wanderRadius)
                );

        // Set the seeking behaviour to the target
        seek.setTarget(seekPosition);
        return seek.process();
    }

    @Override
    public void bind(Entity entity) {
        this.entity = entity;
        seek.bind(entity);
    }
}
