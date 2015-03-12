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
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Piers on 04/03/2015.
 * <p>
 * Agent that will use the offline evolved behavioural tree in controlling its
 * behaviour
 */
public class HunterAgent extends Entity {
    public static final int TICKS_PER_HUNGER_DROP = 10;
    // How big we are
    int radius = 10;

    // How hungry are we
    // When this is below STARVING_THRESHOLD then we start to lose health
    int hungerLevel = 100;
    private static int STARVING_THRESHOLD = 10;

    // Maximum amount of life for a hunter
    private static int MAX_TICKS = 1_000;
    private int currentTicks;

    private int foodCarrying;

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
        initialise(home, brain);
    }

    /**
     * Create HunterAgent with given home and new random brain
     *
     * @param home
     */
    public HunterAgent(HunterVillage home) {
        NeuralNet brain = new NeuralNet(sensors.size(), HunterBehaviourNames.values().length, 3, HunterBehaviourNames.values().length);
        brain.createNet();
        initialise(home, brain);
    }

    private void initialise(HunterVillage home, NeuralNet brain) {
        this.home = home;
        this.brain = brain;
        this.location = new Vector2D(home.getLocation(), true);
    }

    @Override
    public void update() {
        if (velocity == null) velocity = new Vector2D(1, 0, true);
        currentTicks++;

        if (currentTicks % TICKS_PER_HUNGER_DROP == 0) hungerLevel--;

        // Hunt cow if we are near it
        Cow cow = (Cow) world.getNearestEntityOfType(this, Cow.class);
        if (cow != null) {
            if (cow.getLocation().dist(this.getLocation()) <= radius) {
                // kill cow
                foodCarrying += 50;
                //TODO Kill the cow
//                System.out.println("Cow killed");
            }
        }

        //Add food carrying if near home
        if (this.getLocation().dist(home.getLocation()) <= 25) {
            // Put food into home
            if (foodCarrying > 0) {
                home.addFood(foodCarrying);
                foodCarrying = 0;
//                System.out.println("Food returned");
            }

            int toEat = 100 - hungerLevel;
            hungerLevel += home.getFood(toEat);
        }


        // think about hunger and health
        if (hungerLevel < STARVING_THRESHOLD) health--;


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
        Entity cow = world.getNearestEntityOfType(this, Cow.class);
        if (cow != null) return cow.getLocation().dist(this.getLocation());
        return Double.MAX_VALUE; // No cows
    }

    public Vector2D getNearestCowLocation() {
        Entity cow = world.getNearestEntityOfType(this, Cow.class);
        if (cow != null) return cow.getLocation();
        // This is causing problems
        return null;
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.RED);
        graphics.fillOval((int) getX(), (int) getY(), radius, radius);
    }

    @Override
    public boolean isDead() {
        return (currentTicks > MAX_TICKS || super.isDead());
    }

    public NeuralNet getBrain() {
        return brain;
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

        sensors.add(new Sensor<HunterAgent>() {
            @Override
            public void setValue() {
                value = (double) entity.foodCarrying;
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
