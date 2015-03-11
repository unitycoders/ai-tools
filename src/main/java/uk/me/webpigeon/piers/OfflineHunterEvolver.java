package uk.me.webpigeon.piers;

import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.DoubleWorld;

import java.util.ArrayList;

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

    // Maximum number of ticks to run the game for
    private int maxTicks = 1000;
    /**
     * Forbid other parts of the program from using this class
     */
    private OfflineHunterEvolver() {
        world = new DoubleWorld(800, 800);
        village = new HunterVillage(new Vector2D(400, 400));


    }

    public static void main(String[] args) {

    }
}

class HunterGenome {
    // Stores essentially just the weights
    ArrayList<Double> weights;
}