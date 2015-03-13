package uk.me.webpigeon.joseph.cow;

import uk.me.webpigeon.joseph.CowPopulationManager;
import uk.me.webpigeon.joseph.utility.Action;
import uk.me.webpigeon.joseph.utility.LogisticCurve;
import uk.me.webpigeon.joseph.utility.MathNode;
import uk.me.webpigeon.joseph.utility.StatNode;
import uk.me.webpigeon.joseph.utility.MeanNode;
import uk.me.webpigeon.joseph.utility.QuadraticCurve;
import uk.me.webpigeon.joseph.utility.UtilitySystem;
import uk.me.webpigeon.joseph.utility.trees.AbstractTreeNode;
import uk.me.webpigeon.joseph.utility.trees.TreeNode;
import uk.me.webpigeon.steering.FleeBehaviour;
import uk.me.webpigeon.steering.SteeringBehaviour;
import uk.me.webpigeon.steering.WanderingBehaviour;
import uk.me.webpigeon.world.GrassEntity;
import uk.me.webpigeon.world.Tag;
import uk.me.webpigeon.world.World;

public class CowFactory {

	public static Action buildEatAction(Cow cow, UtilitySystem util) {
		TreeNode<Double> hunger = new InvertNode(new CowProperty(cow, Property.SATURATION));
		TreeNode<Double> quadtraic = new QuadraticCurve(hunger, 1, 0.2);
		return new Eat(quadtraic);
	}
	
	public static Action buildNearFood(Cow cow, World world, UtilitySystem util) {
		TreeNode<Double> foodDistance = new DistanceToEntity(cow, world, Tag.GRASS);
		SteeringBehaviour behaviour = new WanderingBehaviour();
		return new SteeringAction(foodDistance, behaviour);
	}
	
	public static Action buildRunAway(Cow cow, World world) {
		TreeNode<Double> hunterDist = new DistanceToEntity(cow, world, Tag.HUNTER);
		TreeNode<Double> inverted = new InvertNode(hunterDist);
		
		return new EvadePreditor(new QuadraticCurve(inverted, 1, 0.8));
	}
	
	public static Action buildWanderingBehavour(Cow cow, World world, UtilitySystem util) {
		AbstractTreeNode hunger = new CowProperty(cow, Property.SATURATION);
		TreeNode<Double> hunterDist = new InvertNode(new DistanceToEntity(cow, world, Tag.HUNTER));
		MeanNode curveInput = new MeanNode(hunterDist, hunger);
		
		SteeringBehaviour behaviour = new WanderingBehaviour();
		return new SteeringAction(new LogisticCurve(hunterDist), behaviour);
	}
	
	public static Action buildReproduceBehavour(CowPopulationManager pop, UtilitySystem util) {
		AbstractTreeNode currPop = new CowPopulationStats("current", pop);
		AbstractTreeNode maxPop = new CowPopulationStats("max", pop);
		MathNode div = new MathNode('/', maxPop, currPop);
		return new MakeBabbyCow(div, pop);
	}
	
	public static void applyCowActions(Cow cow, World world, CowPopulationManager pop, UtilitySystem util) {
		//util.addAction(buildReproduceBehavour(pop, util));
		util.addAction(buildEatAction(cow, util));
		util.addAction(buildWanderingBehavour(cow, world, util));
		util.addAction(buildRunAway(cow, world));
		//util.addAction(buildNearFood(cow, world, util));
	}
	
}
