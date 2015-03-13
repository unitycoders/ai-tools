package uk.me.webpigeon.piers;

import com.sun.org.apache.bcel.internal.generic.POP;
import uk.me.webpigeon.joseph.CowPopulationManager;
import uk.me.webpigeon.piers.neural.NeuralNet;
import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.DoubleWorld;
import uk.me.webpigeon.world.GrassEntity;

import javax.swing.*;
import javax.swing.text.html.parser.Entity;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by Piers on 09/03/2015.
 * <p>
 * A hunter evolver designed to provide the developer with a credible start point for the Hunters
 */
public final class OfflineHunterEvolver {

    private static final String HUNTER_DIRECTORY = "/hunters";
    public static final int GENERATIONS = 100;

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


    private static int POPULATION_SIZE = 100;
    private static final float CROSSOVER_CHANCE = 0.70f;
    private static final float MUTATION_CHANCE = 0.25f;

    // Maximum number of ticks to run the game for
    public static final int MAX_TICKS = 5_000;


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
        System.out.println("Number of Weights: " + numberOfWeights);

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
        if (bestInTotal == null || bestInTotal.getFitness() < bestThisGeneration.getFitness())
            bestInTotal = bestThisGeneration;


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

    private void showBestGenome() {
        JFrame frame = new JFrame("Best Genome");

        world = new DoubleWorld(800, 800);
        village = new HunterVillage(new Vector2D(400, 400));

        frame.setPreferredSize(new Dimension(800, 800));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        brain.setWeights(bestInTotal.weights);
        village.setHunterBrain(brain);

        CowPopulationManager pop = new CowPopulationManager(10);
        pop.addMoreCows(10, world);
        world.addComponent(pop);
        world.addEntity(village);

        for (int i = 0; i < 50; i++) {
            world.addEntity(new GrassEntity(Vector2D.getRandomCartesian(
                    800, 800, true)));
        }

        Thread thread = new Thread(world);

        frame.add(world);
        frame.pack();

        thread.start();

        frame.setVisible(true);
    }

    // Tournament selection - prevents need to calculate fitness for all candidates
    private HunterGenome tournament(int size) {
        HunterGenome best = getRandomFromPopulation();
        for (int i = 1; i < size; i++) {
            HunterGenome choice = getRandomFromPopulation();
            while (choice.getFitness() <= 50) {
                choice = getRandomFromPopulation();
            }
            if (choice.getFitness() > best.getFitness()) best = choice;
            if (worstThisGeneration == null || choice.getFitness() < worstThisGeneration.getFitness()) worstThisGeneration = choice;
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
        cows.addMoreCows(10, world);
        world.addComponent(cows);
        world.addEntity(village);

        // Implant our genome into the hunters
//        village.getHunterBrain().setWeights(candidate.weights);
        brain.setWeights(candidate.weights);
        village.setHunterBrain(brain);

        for (int i = 0; i < 50; i++) {
            world.addEntity(new GrassEntity(Vector2D.getRandomCartesian(
                    800, 800, true)));
        }
        double totalFitness = 0;
        for (int i = 0; i < 5; i++) {
            // Run the simulation
            for (int j = 0; j < OfflineHunterEvolver.MAX_TICKS; j++) {
                world.update(20);
                if (village.isDead()) break;
            }
            totalFitness += (double) village.getTotalFoodStocksEver();
        }

        // retrieve how well they did
        return totalFitness / 5;
    }

    private void saveBestGenome() throws IOException {
        Date date = new Date();
        String fileName = String.format("src/main/resources/hunters/%6.0f_%4d_%2d_%2d_%2d_%2d.ser",
                bestInTotal.getFitness(),
                date.getYear() + 1900,
                date.getMonth() + 1,
                date.getDate(),
                date.getHours(),
                date.getMinutes());

        FileOutputStream fileOut = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fileOut);
        oos.writeObject(bestInTotal);
        oos.close();
        fileOut.close();
        System.out.println("Saved to " + fileName);
    }

    public static void main(String[] args) {

        // Important to do this
        HunterAgent.initialiseBehaviour();
        HunterAgent.initialiseSensors();

        OfflineHunterEvolver evolver = new OfflineHunterEvolver();

        for (int i = 0; i < GENERATIONS; i++) {
            System.out.println("Generation: " + i);
            evolver.runSingleGeneration();
        }

        try {
            evolver.saveBestGenome();
        } catch (IOException e) {
            e.printStackTrace();
        }

        evolver.showBestGenome();

    }
}

class HunterGenome implements Serializable {
    // Stores essentially just the weights
    ArrayList<Double> weights;
    private static Random random = new Random();
    private static double INIDIVIDUAL_BIT_MUTATION_CHANCE = 0.10;

    private Double fitness = null;

    private transient OfflineHunterEvolver evolver;

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