package uk.me.webpigeon.joseph.cow;

import uk.me.webpigeon.joseph.CowPopulationManager;
import uk.me.webpigeon.joseph.utility.Action;
import uk.me.webpigeon.joseph.utility.MathNode;
import uk.me.webpigeon.joseph.utility.MeanNode;
import uk.me.webpigeon.joseph.utility.UtilitySystem;
import uk.me.webpigeon.joseph.utility.trees.AbstractTreeNode;
import uk.me.webpigeon.steering.SteeringBehaviour;
import uk.me.webpigeon.steering.WanderingBehaviour;

public class CowFactory {

	public static Action buildEatAction(Cow cow, UtilitySystem util) {
		AbstractTreeNode hunger = new InvertedCowProperty(cow, Property.SATURATION);
		return new Eat(hunger);
	}
	
	public static Action buildWanderingBehavour(Cow cow, UtilitySystem util) {
		AbstractTreeNode hunger = new CowProperty(cow, Property.SATURATION);
		SteeringBehaviour behaviour = new WanderingBehaviour();
		return new SteeringAction(hunger, behaviour);
	}
	
	public static Action buildReproduceBehavour(CowPopulationManager pop, UtilitySystem util) {
		AbstractTreeNode currPop = new CowPopulationStats("current", pop);
		AbstractTreeNode maxPop = new CowPopulationStats("max", pop);
		MathNode div = new MathNode('/', currPop, maxPop);
		return new MakeBabbyCow(div, pop);
	}
	
	public static void applyCowActions(Cow cow, CowPopulationManager pop, UtilitySystem util) {
		util.addAction(buildReproduceBehavour(pop, util));
		util.addAction(buildEatAction(cow, util));
		util.addAction(buildWanderingBehavour(cow, util));
	}
	
}