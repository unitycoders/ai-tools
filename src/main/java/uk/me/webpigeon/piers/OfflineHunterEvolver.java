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

    // Best genome seen this generation
    HunterGenome bestThisGeneration;
    // Worst genome seen this generation
    HunterGenome worstThisGeneration;
    // Best genome seen in total
    HunterGenome bestInTotal;


    private static int POPULATION_SIZE = 500;
    private static final float CROSSOVER_CHANCE = 0.66f;
    private static final float MUTATION_CHANCE = 0.1f;

    // Maximum number of ticks to run the game for
    public static final int MAX_TICKS = 1000;


    private NeuralNet brain;
    private int numberOfWeights;

    private Random random = new Random();

    /**
     * Forbid other parts of the program from using this class
     */
    private OfflineHunterEvolver() {
        village = new HunterVillage(new Vector2D(400, 400));

        brain = new HunterAgent(village).getBrain();
        brain.createNet();
        numberOfWeights = brain.getNumberOfWeights();

        createInitialPopulation();

    }

    private void createInitialPopulation() {
        population = new ArrayList<>(POPULATION_SIZE);
        next = new ArrayList<>(POPULATION_SIZE);
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(new HunterGenome(this, numberOfWeights));
        }
    }

    // Runs a generation of the GA - causing the population to shift to the new one
    public void runSingleGeneration() {

        next.clear();
        bestThisGeneration = null;
        worstThisGeneration = null;

        while (next.size() < POPULATION_SIZE) {
            // add a new Genome
            float rand = random.nextFloat();
            if (rand < CROSSOVER_CHANCE) {
                next.add(new HunterGenome(tournament(5), tournament(5)));
                continue;
            }
            if (rand < CROSSOVER_CHANCE + MUTATION_CHANCE) {
                next.add(new HunterGenome(tournament(5)));
                continue;
            }
            next.add(tournament(5).getClone());
//            System.out.println(next.size());
        }

        population.clear();
        population.addAll(next);
        if (bestInTotal == null || bestInTotal.getFitness() < bestThisGeneration.getFitness()) bestInTotal = bestThisGeneration;


        System.out.println("Best Overall: " + bestInTotal.getFitness() +
                " Best This Generation:" + bestThisGeneration.getFitness()
                + " Worst this Generation: " + worstThisGeneration.getFitness());
    }

    // Potentially quite expensive operation
    private HunterGenome getBestFromPopulation() {
        HunterGenome best = population.get(0);
        for (int i = 1; i < population.size(); i++) {
            if (population.get(i).getFitness() > best.getFitness()) best = population.get(i);
        }
        return best;
    }

    // Tournament selection - prevents need to calculate fitness for all candidates
    private HunterGenome tournament(int size) {
        HunterGenome best = getRandomFromPopulation();
        if (worstThisGeneration == null) worstThisGeneration = best;
        for (int i = 1; i < size; i++) {
            HunterGenome choice = getRandomFromPopulation();
            if (choice.getFitness() > best.getFitness()) best = choice;
            if (choice.getFitness() < worstThisGeneration.getFitness()) worstThisGeneration = choice;
        }
        // Set the best seen so far
        if (bestThisGeneration == null || bestThisGeneration.getFitness() < best.getFitness())
            bestThisGeneration = best;

        return best;
    }

    private HunterGenome getRandomFromPopulation() {
        return population.get(random.nextInt(population.size()));
    }

    // Only call this from a genome
    public double calculateFitnessFromGenome(HunterGenome candidate) {
        DoubleWorld world = new DoubleWorld(800, 800);
        HunterVillage village = new HunterVillage(new Vector2D(400, 400));
        CowPopulationManager cows = new CowPopulationManager(10);
        cows.addMoreCows(10);
        world.addComponent(cows);
        world.addEntity(village);

        // Implant our genome into the hunters
//        village.getHunterBrain().setWeights(candidate.weights);
        brain.setWeights(candidate.weights);
        village.setHunterBrain(brain);

        // Run the simulation
        for (int i = 0; i < OfflineHunterEvolver.MAX_TICKS; i++) {
            world.update(20);
        }

        // retrieve how well they did
        return (double) village.getTotalFoodStocksEver();
    }

    public static void main(String[] args) {

        // Important to do this
        HunterAgent.initialiseBehaviour();
        HunterAgent.initialiseSensors();

        OfflineHunterEvolver evolver = new OfflineHunterEvolver();

        for (int i = 0; i < 10; i++) {
            evolver.runSingleGeneration();
        }

    }
}

class HunterGenome {
    // Stores essentially just the weights
    ArrayList<Double> weights;
    private static Random random = new Random();
    private static double INIDIVIDUAL_BIT_MUTATION_CHANCE = 0.05;

    private Double fitness = null;

    private OfflineHunterEvolver evolver;

    private HunterGenome() {
        weights = new ArrayList<>();
    }

    // Random genome
    public HunterGenome(OfflineHunterEvolver evolver, int numberOfWeights) {
        weights = new ArrayList<>(numberOfWeights);
        for (int i = 0; i < numberOfWeights; i++) {
            weights.add(rand());
        }

        this.evolver = evolver;
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
        this.evolver = source.evolver;
    }

    //CrossOver operation
    public HunterGenome(HunterGenome first, HunterGenome second) {
        if (first.weights.size() != second.weights.size())
            throw new IllegalArgumentException("Genomes of incompatible size");

        int size = first.weights.size();
        this.weights = new ArrayList<>(size);
        // Begin crossover - single point
        int crossoverPoint = random.nextInt(size);
        for (int i = 0; i < size; i++) {
            this.weights.add((i < crossoverPoint) ? first.weights.get(i) : second.weights.get(i));
        }

        this.evolver = first.evolver;
    }

    // Cloning
    public HunterGenome getClone() {
        HunterGenome clone = new HunterGenome();
        clone.weights.addAll(this.weights);
        // This is reasonable - will cut down on calculations
        clone.fitness = this.fitness;
        clone.evolver = this.evolver;
        return clone;
    }

    public double getFitness() {
        if (fitness == null) calculateFitness();
        return fitness;
    }

    private void calculateFitness() {
        // Do the heavy work calculating fitness
        fitness = evolver.calculateFitnessFromGenome(this);
    }

    private double rand() {
        return ((Math.random() * 2) - 1);
    }

    @Override
    public String toString() {
        return "HunterGenome{" +
                "fitness=" + fitness +
                '}';
    }
}