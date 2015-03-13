package uk.me.webpigeon.world;

import java.util.Random;

import uk.me.webpigeon.joseph.BaseStats;
import uk.me.webpigeon.joseph.CowPopulationManager;
import uk.me.webpigeon.joseph.GenomeCoding;
import uk.me.webpigeon.joseph.cow.Cow;
import uk.me.webpigeon.joseph.cow.CowFactory;
import uk.me.webpigeon.joseph.cow.Property;
import uk.me.webpigeon.joseph.utility.UtilitySystem;
import uk.me.webpigeon.steering.SeekBehaviour;
import uk.me.webpigeon.steering.WanderingBehaviour;
import uk.me.webpigeon.steering.WeightedBehaviour;
import uk.me.webpigeon.util.Vector2D;

public class EntityFactory {
	private EntityFactory() {
	}

	public static double[] buildDefaultGenome() {
		return new double[]{BaseStats.BASE_SAT, BaseStats.BASE_HUNGER, BaseStats.BASE_SIGHT};
	}
	
	public static Cow buildCow(World world, int maxWidth, int maxHeight, CowPopulationManager pop) {
		return buildGenomeCow(maxWidth, maxHeight, world, pop, buildDefaultGenome());
	}

	
	public static Cow buildGenomeCow(int maxWidth, int maxHeight, World world, CowPopulationManager pop, double[] genome) {
		Random r = new Random();

		int x = r.nextInt(maxWidth);
		int y = r.nextInt(maxHeight);
		
		UtilitySystem util = new UtilitySystem();
		Cow cow = new Cow(x, y, util, genome);
		
		setGenome(cow, genome);
		
		CowFactory.applyCowActions(cow, world, pop, util);
		return cow;
	}
	
	public static void setGenome(Entity entity, double[] genome) {
		entity.setValue(Property.SATURATION, genome[GenomeCoding.MAX_SAT_ID]);
		entity.setLimit(Property.SATURATION, genome[GenomeCoding.MAX_SAT_ID]);
		entity.setValue(Property.METABOLISM, Math.abs(genome[GenomeCoding.HUNGER_RATE]));
		entity.setLimit(Property.METABOLISM, Math.abs(genome[GenomeCoding.HUNGER_RATE]));
		entity.setValue(Property.SIGHT_RANGE, Math.abs(genome[GenomeCoding.SIGHT_RANGE]));
	}
}
