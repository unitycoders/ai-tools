package uk.me.webpigeon.joseph.cow;

import uk.me.webpigeon.joseph.utility.Action;
import uk.me.webpigeon.joseph.utility.trees.AbstractTreeNode;
import uk.me.webpigeon.steering.SeekBehaviour;
import uk.me.webpigeon.util.Vector2D;
import uk.me.webpigeon.world.Entity;
import uk.me.webpigeon.world.GrassEntity;
import uk.me.webpigeon.world.World;

public class Eat extends Action {
	private static final Double EAT_RANGE = 2.0;
	SeekBehaviour behaviour;
	Entity plant;
	Vector2D dest;
	
	public Eat(AbstractTreeNode utilCalc) {
		super(utilCalc);
		behaviour = new SeekBehaviour(null);
	}
	
	@Override
	public void executeStep(Entity entity, World world) {
		if (plant == null) {
			plant = world.getNearestEntityOfType(entity, GrassEntity.class);
			if (plant == null) {
				//ah, there are no more plants, I'm a sad cow :(
				return;
			}
			dest = plant.getLocation();
		}
		
		if (dest.dist(entity.getLocation()) > EAT_RANGE) {
			behaviour.setTarget(dest);
			behaviour.bind(entity);
			Vector2D forceDir = behaviour.process();
			entity.setVelocity(forceDir);
			behaviour.bind(null);
		} else {
			//we can eat the plant now ^^
			Cow cow = (Cow)entity;
			cow.addSaturation(plant.getHealth());
			world.removeEntity(plant);
			
			plant = null;
			dest = null;
		}
		
		// find the closest plant
		// head towards plant
		// eat the plant
		// be happy
	}

}
