package uk.me.webpigeon.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import uk.me.webpigeon.steering.SteeringBehaviour;
import uk.me.webpigeon.util.Vector2D;

public class SteeringEntity extends Entity {
    private static final Double VECTOR_SMOOTHING = 0.2;
    private static final Integer SIZE = 30;
    private SteeringBehaviour behaviour;
    private ArrayList<Vector2D> previousLocations = new ArrayList<>();

    private double rotationAmount;

    public SteeringEntity(int x, int y, SteeringBehaviour behaviour) {
        this.behaviour = behaviour;
        this.location = new Vector2D(x, y, true);
        this.velocity = new Vector2D(0, 0, true);
        behaviour.bind(this);
    }

    @Override
    public void update() {
        Vector2D targetForce = behaviour.process();

        velocity = Vector2D.add(targetForce, velocity, VECTOR_SMOOTHING);
        location.add(velocity);
        rotationAmount = velocity.angleBetween(new Vector2D(1, 0));
        previousLocations.add(new Vector2D(location));
        location.wrap(world.width, world.height);
    }

    @Override
    public void draw(Graphics2D graphics) {
        Graphics2D sandbox = (Graphics2D) graphics.create();
        sandbox.translate(location.getX(), location.getY());

        //TODO piers - can you look at this, i'm assuming theta will be the rotation, but it looks wrong
//        Vector2D polarVel = Vector2D.toPolar(velocity);
        double theta = velocity.getTheta();
        sandbox.rotate(Math.toRadians(rotationAmount));

        sandbox.setColor(Color.WHITE);
        sandbox.fillRect(-SIZE, -(int) (SIZE / 2.0), SIZE * 2, SIZE);
        sandbox.rotate(Math.toRadians(-rotationAmount));
        sandbox.translate(-location.getX(), -location.getY());
    }

    public void debugDraw(Graphics2D graphics) {
        graphics.setColor(Color.DARK_GRAY);
        for (int i = 0; i < previousLocations.size() - 1; i++) {
            graphics.drawOval((int) previousLocations.get(i).getX() - SIZE / 6, (int) previousLocations.get(i).getY() - SIZE / 6, SIZE / 3, SIZE / 3);
        }

        behaviour.debugDraw(graphics);
    }

}
