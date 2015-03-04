package uk.me.webpigeon.steering;

import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

/**
 * Created by Piers on 04/03/2015.
 */
public class WanderingBehaviour implements SteeringBehaviour {

    private double wanderRadius = 50;
    private double wanderDistance = 50;
    private double angleJitter = 5;
    private Entity entity;

    // random vector
    private Vector2D c = Vector2D.toCartesian(Vector2D.getRandomPolar(Math.toRadians(360), 1, 1, true));
    private SeekBehaviour seek = new SeekBehaviour(null);
    private Random random = new Random();
    private Vector2D centerOfCircle;
    private Vector2D seekPosition;

    @Override
    public Vector2D process() {
        // Obtain information about the entity
        Vector2D ourPosition = new Vector2D(entity.getLocation(), true);
        Vector2D ourVelocity = new Vector2D(entity.getVelocity(), true);

        // Modify ourVelocity to reflect the difference between Position and center of circle
        ourVelocity.normalise();
        ourVelocity.multiply(wanderDistance);

        // Use that information to calculate the center of the circle
        centerOfCircle = Vector2D.add(ourPosition, ourVelocity);
        // Vary c by jittering it a little

        c = Vector2D.toPolar(c);
        c.rotate(Math.toRadians((random.nextDouble() * 2 - 1) * angleJitter));
        c = Vector2D.toCartesian(c);

        // c is a unit vector in its own co-ordinate system - bring it back
        seekPosition = Vector2D.add(centerOfCircle, c);

        // Set the seeking behaviour to the target
        seek.setTarget(seekPosition);
        return seek.process();
    }

    @Override
    public void bind(Entity entity) {
        this.entity = entity;
        seek.bind(entity);
    }

    @Override
    public void debugDraw(Graphics2D g) {
        // TODO Auto-generated method stub
        g.drawOval((int) (centerOfCircle.x - wanderRadius), (int) (centerOfCircle.y - wanderRadius), (int) wanderRadius * 2, (int) wanderRadius * 2);
        g.setColor(Color.GREEN);
        g.drawOval((int) seekPosition.x - 2, (int) seekPosition.y - 2, 4, 4);
        seek.debugDraw(g);
    }
}
