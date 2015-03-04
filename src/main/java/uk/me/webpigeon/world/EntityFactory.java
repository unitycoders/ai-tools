package uk.me.webpigeon.world;

import java.util.Random;

import uk.me.webpigeon.steering.WanderingBehaviour;

public class EntityFactory {
	private EntityFactory() {}
	
	public static Entity buildCow(int maxWidth, int maxHeight) {
		Random r = new Random();
		
		int x = r.nextInt(maxWidth);
		int y = r.nextInt(maxHeight);
		
		Entity cow = new SteeringEntity(x, y, new WanderingBehaviour());
		return cow;
	}

}
