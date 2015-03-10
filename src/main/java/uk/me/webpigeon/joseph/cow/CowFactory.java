package uk.me.webpigeon.joseph.cow;

import uk.me.webpigeon.joseph.CowPopulationManager;
import uk.me.webpigeon.joseph.utility.Action;
import uk.me.webpigeon.joseph.utility.MeanNode;
import uk.me.webpigeon.joseph.utility.UtilTreeNode;
import uk.me.webpigeon.joseph.utility.UtilitySystem;
import uk.me.webpigeon.steering.SteeringBehaviour;
import uk.me.webpigeon.steering.WanderingBehaviour;

public class CowFactory {

	public static Action buildEatAction(UtilitySystem util) {
		UtilTreeNode hunger = new InvertedCowProperty(Property.SATURATION);
		return new Eat(hunger);
	}
	
	public static Action buildWanderingBehavour(UtilitySystem util) {
		UtilTreeNode hunger = new CowProperty(Property.SATURATION);
		SteeringBehaviour behaviour = new WanderingBehaviour();
		return new SteeringAction(hunger, behaviour);
	}
	
	public static Action buildReproduceBehavour(CowPopulationManager pop, UtilitySystem util) {
		UtilTreeNode hunger = new CowProperty(Property.SATURATION);
		return new MakeBabbyCow(hunger, pop);
	}
	
	public static void applyCowActions(CowPopulationManager pop, UtilitySystem util) {
		//util.addAction(buildReproduceBehavour(pop, util));
		util.addAction(buildEatAction(util));
		util.addAction(buildWanderingBehavour(util));
	}
	
}
