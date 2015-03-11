package uk.me.webpigeon.piers;

import uk.me.webpigeon.piers.neural.NeuralNet;
import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;
import uk.me.webpigeon.world.World;
import uk.me.webpigeon.world.WorldComponent;

import javax.xml.stream.events.EntityDeclaration;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Piers on 09/03/2015.
 * <p>
 * The object that represents the village that the hunters live in
 */
public class HunterVillage extends Entity {

    // How much food is available for the hunters to eat
    private int foodStocks = 51;

    // How many people do we have
    private int currentPopulation;
    private int maxPopulation = 10;

    private int hunterThreshold = 50;

    // The brian to use when creating hunters
    NeuralNet hunterBrain;

    private ArrayList<HunterAgent> hunters;


    @Override
    public void draw(Graphics2D graphics) {
//        super.draw(graphics);
        graphics.setColor(Color.cyan);
        graphics.fillRect((int) location.getX(), (int) location.getY(), 25, 25);
    }

    public HunterVillage(Vector2D location) {
        this.location = location;
        hunters = new ArrayList<>();
    }

    @Override
    public void update() {
        // Don't call super.update();
        // take out dead hunters
        Iterator<HunterAgent> iterator = hunters.iterator();
        while (iterator.hasNext()) {
            HunterAgent agent = iterator.next();
            if (agent.isDead()) {
                iterator.remove();
                System.out.println("Hunter died - All mourn the hunter!");
            }
        }

        currentPopulation = hunters.size();
        if (foodStocks > hunterThreshold && currentPopulation < maxPopulation) createNewHunter();
    }

    private void createNewHunter() {
        HunterAgent agent = new HunterAgent(this);
        foodStocks -= 50;
        world.addEntity(agent);
        hunters.add(agent);
    }
}
