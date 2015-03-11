package uk.me.webpigeon.piers;

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
    int radius = 10;

    // How healthy are we
    int health;

    // How hungry are we
    int hungerLevel;

    private static ArrayList<Sensor<HunterAgent>> sensors;
    private static HunterBehaviour behaviour;

    private NeuralNet brain;

    protected HunterVillage home;

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
        this.brain = new NeuralNet(sensors.size(), HunterBehaviourNames.values().length, 3, HunterBehaviourNames.values().length);
        this.brain.createNet();
        this.location = new Vector2D(home.getLocation(), true);
    }

    @Override
    public void update() {
        if (velocity == null) velocity = new Vector2D(1, 0, true);

        // Set the input layer to use our sensors
        ArrayList<Double> inputs = new ArrayList<>();
        for (Sensor sensor : sensors) {
            sensor.bind(this);
            inputs.add(sensor.getValue());
        }

        ArrayList<Double> outputs = brain.getOutputs(inputs);

        behaviour.bind(this);
        behaviour.setWeights(outputs);
        this.velocity = behaviour.process();

        super.update();
    }

    public double getDistanceToNearestCow() {
        return world.getNearestEntityOfType(this, Cow.class).getLocation().dist(this.getLocation());
    }

    public Vector2D getNearestCowLocation() {
        return world.getNearestEntityOfType(this, Cow.class).getLocation();
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.RED);
        graphics.fillOval((int) getX(), (int) getY(), radius, radius);
    }

    public static void initialiseBehaviour() {
        behaviour = new HunterBehaviour();
    }

    public static void initialiseSensors() {
        sensors = new ArrayList<>();
        // health
        sensors.add(new Sensor<HunterAgent>() {
            @Override
            public void setValue() {
                value = (double) entity.health;
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
}


class HunterBehaviour extends WeightedBehaviour {
    private HashMap<HunterBehaviourNames, SteeringBehaviour> hunterBehaviours = new HashMap<>();

    public HunterBehaviour() {
        hunterBehaviours.put(HunterBehaviourNames.RETURN_HOME, new SeekBehaviour(null));
        hunterBehaviours.put(HunterBehaviourNames.CHASE_COW, new SeekBehaviour(null));
        hunterBehaviours.put(HunterBehaviourNames.WANDER, new WanderingBehaviour());

        // For now weights of 1.0
        for (SteeringBehaviour behaviour : hunterBehaviours.values()) {
            addBehavour(behaviour, 1.0);
        }
    }

    public void setWeights(ArrayList<Double> weights) {
        if (weights.size() != hunterBehaviours.size())
            throw new IllegalArgumentException("Weights size was not correct(" + weights.size() + ") should have been (" + hunterBehaviours.size() + ")");
        int i = 0;
        for (HunterBehaviourNames behaviour : HunterBehaviourNames.values()) {
            behaviours.put(hunterBehaviours.get(behaviour), weights.get(i));
        }
    }


    @Override
    public void bind(Entity entity) {
        if (!(entity instanceof HunterAgent))
            throw new IllegalArgumentException("Entity must be of type: " + HunterAgent.class + " not of type: " + entity.getClass());
        super.bind(entity);

        ((SeekBehaviour) hunterBehaviours.get(HunterBehaviourNames.RETURN_HOME)).setTarget(((HunterAgent) entity).home.getLocation());
        ((SeekBehaviour) hunterBehaviours.get(HunterBehaviourNames.CHASE_COW)).setTarget(((HunterAgent) entity).getNearestCowLocation());

    }
}

enum HunterBehaviourNames {
    RETURN_HOME,
    CHASE_COW,
    WANDER
}
