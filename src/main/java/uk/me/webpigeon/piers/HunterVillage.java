package uk.me.webpigeon.piers;

import uk.me.webpigeon.world.World;
import uk.me.webpigeon.world.WorldComponent;

import java.util.ArrayList;

/**
 * Created by Piers on 09/03/2015.
 * <p>
 * The object that represents the village that the hunters live in
 */
public class HunterVillage implements WorldComponent {

    // How much food is available for the hunters to eat
    private int foodStocks;

    // How many people do we have
    private int currentPopulation;

    private ArrayList<HunterAgent> hunters;
    

    @Override
    public void update(World world, int delta) {

    }
}
