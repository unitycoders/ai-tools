package uk.me.webpigeon.strawberry;

import uk.me.webpigeon.util.Vector2D;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Piers on 05/03/2015.
 */
public class Strawberry {

    int x;
    int y;

    Vector2D location;

    int timeLived = 0;
    int growThreshold = 25;
    int lifeThreshold = 250;

    static int maxRunnerDistance = 75;


    static int manyRunners = 5;

    ArrayList<Vector2D> targetLocations;
    ArrayList<Vector2D> currentRunnerLocations;
    boolean[] runnerCompleted;
    boolean running = false;

    boolean dead = false;

    public Strawberry(int x, int y) {
        this.x = x;
        this.y = y;

        location = new Vector2D(x, y, true);

        targetLocations = new ArrayList<>();
        currentRunnerLocations = new ArrayList<>();
    }

    public Strawberry(Vector2D location) {
        this((int) location.x, (int) location.y);
    }

    public void update(StrawberryWorld world) {
        if (!dead) {
            timeLived++;
            if (!running) {
                if (growThreshold < timeLived) {
                    // Send out the runners
                    int numberOfRunners = (int) (world.map[x][y] * manyRunners);
                    int distance = maxRunnerDistance - (int) (world.map[x][y] * maxRunnerDistance);
                    for (int i = 0; i < numberOfRunners; i++) {
//                        Vector2D runnerTarget = new Vector2D(Math.toRadians(Math.random() * 360), 1, true);
                        Vector2D runnerTarget = Vector2D.getRandomPolar(2 * Math.PI, 1, 1, true);
//                        System.out.println(runnerTarget);
                        runnerTarget = Vector2D.toCartesian(runnerTarget);
//                        System.out.println(runnerTarget);
                        runnerTarget.normalise();
                        runnerTarget.multiply(distance);
                        runnerTarget.add(location);
                        if (runnerTarget.x < 0 || runnerTarget.x > world.width || runnerTarget.y < 0 || runnerTarget.y > world.height) {
                            i--;
                            continue;
                        }

                        targetLocations.add(runnerTarget);
                        currentRunnerLocations.add(new Vector2D(location, true));
                    }
                    running = true;
                    runnerCompleted = new boolean[targetLocations.size()];
                }
            } else {

                // run the runners
                for (int i = 0; i < targetLocations.size(); i++) {
                    if (!runnerCompleted[i]) {
//                        System.out.println(currentRunnerLocations.get(i).dist(targetLocations.get(i)));
                        Vector2D difference = Vector2D.subtract(targetLocations.get(i), currentRunnerLocations.get(i));
                        if (difference.mag() > 6) {
//                            System.out.println(difference + " " + difference.mag());
                            currentRunnerLocations.get(i).add(Vector2D.subtract(targetLocations.get(i), location), 3 / targetLocations.get(i).mag());
                        } else {
//                            System.out.println("Strawberry lived for" + timeLived);
                            world.addStrawberry(new Strawberry(targetLocations.get(i)));
                            runnerCompleted[i] = true;
                        }
                    }
                }
            }

            if (timeLived > lifeThreshold) {
                // time to die
                dead = true;
            }
        }

    }

    public void draw(Graphics2D graphics2D) {
        // 139,69,19
        graphics2D.setColor((dead) ? new Color(139, 69, 19) : Color.GREEN);
        graphics2D.fillRect(x, y, 5, 5);

        if (!dead) {
            for (int i = 0; i < currentRunnerLocations.size(); i++) {
                graphics2D.setColor(Color.GREEN.darker().darker());
                Vector2D target = currentRunnerLocations.get(i);
                int targetX = (int) target.x;
                int targetY = (int) target.y;
                graphics2D.drawLine(x, y, targetX, targetY);
//                graphics2D.setColor(Color.CYAN);
//                graphics2D.fillRect((int) targetLocations.get(i).x, (int) targetLocations.get(i).y, 5, 5);

            }
        }
    }
}
