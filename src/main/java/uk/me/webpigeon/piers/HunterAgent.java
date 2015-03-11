package uk.me.webpigeon.piers;

import uk.me.webpigeon.behaviour.TreeNode;
import uk.me.webpigeon.joseph.cow.Cow;
import uk.me.webpigeon.piers.neural.NeuralNet;
import uk.me.webpigeon.piers.neural.Sensor;
import uk.me.webpigeon.steering.SeekBehaviour;
import uk.me.webpigeon.steering.SteeringBehaviour;
import uk.me.webpigeon.steering.WanderingBehaviour;
import uk.me.webpigeon.steering.WeightedBehaviour;
import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Piers on 04/03/2015.
 * <p>
 * Agent that will use the offline evolved behavioural tree in controlling its
 * behaviour
 */
public class HunterAgent extends Entity {
    // How big we are
    int radius;

    // How healthy are we
    int health;

    // How hungry are we
    int hungerLevel;

    private static ArrayList<Sensor<HunterAgent>> sensors;
    private static WeightedBehaviour behaviour;

    private NeuralNet brain;

    private HunterVillage home;

    /**
     * Create HunterAgent with given home and a provided brain
     *
     * @param home
     * @param brain
     */
    public HunterAgent(HunterVillage home, NeuralNet brain) {
        this.home = home;
        this.brain = brain;
    }

    /**
     * Create HunterAgent with given home and new random brain
     *
     * @param home
     */
    public HunterAgent(HunterVillage home) {
        this.home = home;
        this.brain = new NeuralNet(sensors.size(), HunterBehaviours.values().length, 3, HunterBehaviours.values().length);
        this.brain.createNet();
    }

    public static void initialiseSensors() {
        sensors = new ArrayList<>();
        // health
        sensors.add(new Sensor<HunterAgent>() {
            @Override
            public void setValue() {
                value = new Double(entity.health);
            }
        });

        // hunger
        sensors.add(new Sensor<HunterAgent>() {
            @Override
            public void setValue() {
                value = (double) entity.hungerLevel;
            }
        });

        sensors.add(new Sensor<HunterAgent>() {
            @Override
            public void setValue() {
                value = entity.getDistanceToNearestCow();
            }
        });
    }

    public static void initialiseBehaviour() {
        behaviour = new HunterBehaviour();
    }

    @Override
    public void update() {
        // Set the input layer to use our sensors

    }

    public double getDistanceToNearestCow() {
        return world.getNearestEntityOfType(this, Cow.class).getLocation().dist(this.getLocation());
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.RED);
        graphics.fillOval((int) getX(), (int) getY(), radius, radius);
    }
}


class HunterBehaviour extends WeightedBehaviour {
    private HashMap<HunterBehaviours, SteeringBehaviour> hunterBehaviours;

    public HunterBehaviour() {
        hunterBehaviours.put(HunterBehaviours.RETURN_HOME, new SeekBehaviour(null));
        hunterBehaviours.put(HunterBehaviours.CHASE_COW, new SeekBehaviour(null));
        hunterBehaviours.put(HunterBehaviours.WANDER, new WanderingBehaviour());

        // For now weights of 1.0
        for (SteeringBehaviour behaviour : hunterBehaviours.values()) {
            addBehavour(behaviour, 1.0);
        }
    }


    @Override
    public void bind(Entity entity) {
        super.bind(entity);
    }
}

enum HunterBehaviours {
    RETURN_HOME,
    CHASE_COW,
    WANDER
}
