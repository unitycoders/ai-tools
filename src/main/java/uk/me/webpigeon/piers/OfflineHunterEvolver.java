package uk.me.webpigeon.piers;

import com.sun.org.apache.bcel.internal.generic.POP;
import uk.me.webpigeon.joseph.CowPopulationManager;
import uk.me.webpigeon.piers.neural.NeuralNet;
import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.DoubleWorld;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Piers on 09/03/2015.
 * <p>
 * A hunter evolver designed to provide the developer with a credible start point for the Hunters
 */
public final class OfflineHunterEvolver {

    private static final String HUNTER_DIRECTORY = "/hunters";

    // World to use for evaluation
    private DoubleWorld world;
    // village to use for evaluation
    private HunterVillage village;

    // population of genomes
    ArrayList<HunterGenome> population = new ArrayList<>();
    ArrayList<HunterGenome> next = new ArrayList<>();
    private static int POPULATION_SIZE = 50;

    // Maximum number of ticks to run the game for
    private int maxTicks = 1000;


    private NeuralNet brain;
    private int numberOfWeights;

    private Random random = new Random();

    /**
     * Forbid other parts of the program from using this class
     */
    private OfflineHunterEvolver() {


        brain = new HunterAgent(village).getBrain();
        brain.createNet();
        numberOfWeights = brain.getNumberOfWeights();

        createInitialPopulation();

    }

    private void createInitialPopulation() {
        population = new ArrayList<>(POPULATION_SIZE);
        next = new ArrayList<>(POPULATION_SIZE);
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(new HunterGenome(numberOfWeights));
        }
    }

    // Runs a generation of the GA - causing the population to shift to the new one
    public void runSingleGeneration() {

        next.clear();

        while (next.size() < POPULATION_SIZE) {
            // add a new Genome
        }

        population.clear();
        population.addAll(next);
    }

    private HunterGenome tournament(int size) {
        HunterGenome best = getRandomFromPopulation();

        for (int i = 1; i < size; i++) {
            HunterGenome choice = getRandomFromPopulation();

        }

        return best;
    }

    private HunterGenome getRandomFromPopulation() {
        return population.get(random.nextInt(population.size()));
    }

    public static void main(String[] args) {

        // Important to do this
        HunterAgent.initialiseBehaviour();
        HunterAgent.initialiseSensors();

        OfflineHunterEvolver evolver = new OfflineHunterEvolver();

    }
}

class HunterGenome {
    // Stores essentially just the weights
    ArrayList<Double> weights;
    private static Random random = new Random();
    private static double INIDIVIDUAL_BIT_MUTATION_CHANCE = 0.05;

    private Double fitness = null;

    private HunterGenome() {
        weights = new ArrayList<>();
    }

    // Random genome
    public HunterGenome(int numberOfWeights) {
        weights = new ArrayList<>(numberOfWeights);
        for (int i = 0; i < numberOfWeights; i++) {
            weights.add(rand());
        }
    }

    // Mutation operation
    public HunterGenome(HunterGenome source) {
        this.weights = new ArrayList<>(source.weights.size());
        for (int i = 0; i < source.weights.size(); i++) {
            if (random.nextDouble() < INIDIVIDUAL_BIT_MUTATION_CHANCE) {
                this.weights.add(rand());
            } else {
                this.weights.add(source.weights.get(i));
            }
        }
    }

    //CrossOver operation
    public HunterGenome(HunterGenome first, HunterGenome second) {
        if (first.weights.size() != second.weights.size())
            throw new IllegalArgumentException("Genomes of incompatible size");

        int size = first.weights.size();
        // Begin crossover - single point
        int crossoverPoint = random.nextInt(size);
        for (int i = 0; i < size; i++) {
            this.weights.add((i < crossoverPoint) ? first.weights.get(i) : second.weights.get(i));
        }
    }

    // Cloning
    public HunterGenome getClone() {
        HunterGenome clone = new HunterGenome();
        clone.weights.addAll(this.weights);
        return clone;
    }

    public double getFitness() {
        if (fitness == null) calculateFitness();
        return fitness;
    }

    private void calculateFitness() {
        // Do the heavy work calculating fitness

        DoubleWorld world = new DoubleWorld(800, 800);
        HunterVillage village = new HunterVillage(new Vector2D(400, 400));
        CowPopulationManager cows = new CowPopulationManager(10);
        world.addComponent(cows);
        world.addEntity(village);

    }

    private double rand() {
        return ((Math.random() * 2) - 1);
    }
}