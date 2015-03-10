package uk.me.webpigeon.world;

import java.util.Random;

import uk.me.webpigeon.joseph.GenomeCoding;
import uk.me.webpigeon.joseph.cow.Cow;
import uk.me.webpigeon.joseph.cow.CowFactory;
import uk.me.webpigeon.joseph.utility.UtilitySystem;
import uk.me.webpigeon.steering.SeekBehaviour;
import uk.me.webpigeon.steering.WanderingBehaviour;
import uk.me.webpigeon.steering.WeightedBehaviour;
import uk.me.webpigeon.util.Vector2D;

public class EntityFactory {
	private EntityFactory() {
	}

	public static Cow buildCow(int maxWidth, int maxHeight) {
		double[] genome = new double[]{GenomeCoding.MAX_SAT_ID, GenomeCoding.HUNGER_RATE};
		return buildGenomeCow(maxWidth, maxHeight, genome);
	}

	
	public static Cow buildGenomeCow(int maxWidth, int maxHeight, double[] genome) {
		Random r = new Random();

		int x = r.nextInt(maxWidth);
		int y = r.nextInt(maxHeight);

		UtilitySystem util = new UtilitySystem();
		CowFactory.applyCowActions(util);

		Cow cow = new Cow(x, y, util, genome);
		return cow;
	}
}
