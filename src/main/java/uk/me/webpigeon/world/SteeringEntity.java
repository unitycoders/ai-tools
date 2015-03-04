package uk.me.webpigeon.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import uk.me.webpigeon.steering.SteeringBehaviour;
import uk.me.webpigeon.util.Vector2D;

public class SteeringEntity extends Entity {
    private static final Integer SIZE = 50;
    private SteeringBehaviour behavour;
    private ArrayList<Vector2D> previousLocations = new ArrayList<>();

    public SteeringEntity(SteeringBehaviour behavour) {
        this.behavour = behavour;
        this.location = new Vector2D(50, 50, true);
        this.velocity = new Vector2D(0, 0, true);
        behavour.bind(this);
    }

    @Override
    public void update() {
        Vector2D targetForce = behavour.process();

        x += targetForce.getX();
        y += targetForce.getY();

        location.add(targetForce);
        previousLocations.add(new Vector2D(location));
        location.wrap(500, 500);
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.WHITE);
        graphics.drawOval((int) (location.getX()), (int) (location.getY()), SIZE, SIZE);

        graphics.setColor(Color.GREEN);
        for(int i = 0; i < previousLocations.size() - 1; i++){
            graphics.drawOval((int)previousLocations.get(i).getX(), (int) previousLocations.get(i).getY(), SIZE / 3, SIZE / 3);
        }
    }

}
