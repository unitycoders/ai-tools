package uk.me.webpigeon.world;

import java.util.Random;

import uk.me.webpigeon.steering.FleeBehaviour;
import uk.me.webpigeon.steering.SeekBehaviour;
import uk.me.webpigeon.steering.SteeringBehaviour;
import uk.me.webpigeon.steering.WanderingBehaviour;
import uk.me.webpigeon.steering.WeightedBehaviour;
import uk.me.webpigeon.util.Vector2D;

public class EntityFactory {
	private EntityFactory() {}
	
	public static Entity buildCow(int maxWidth, int maxHeight) {
		Random r = new Random();
		
		int x = r.nextInt(maxWidth);
		int y = r.nextInt(maxHeight);
		
		WeightedBehaviour wb = new WeightedBehaviour();
		wb.addBehavour(new SeekBehaviour(new Vector2D(400,300)), 2.5);
		//wb.addBehavour(new FleeBehaviour(new Vector2D(400, 300)), 0.2);
		//wb.addBehavour(new FleeBehaviour(new Vector2D(0, 0)), 4);
		//wb.addBehavour(new FleeBehaviour(new Vector2D(0, 600)), 4);
		//wb.addBehavour(new FleeBehaviour(new Vector2D(800, 0)), 4);
		//wb.addBehavour(new FleeBehaviour(new Vector2D(800, 600)), 4);
		wb.addBehavour(new WanderingBehaviour(), 5);
		
		Entity cow = new SteeringEntity(x, y, wb);
		return cow;
	}

}
