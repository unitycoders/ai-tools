package uk.me.webpigeon.world;

import java.awt.Graphics2D;

/**
 * Created by Piers on 03/03/2015.
 */
public class BehaviourEntity extends Entity {

	public String entityBehaviourName;

	static {
		// Initialise the necessary behaviour for this
		BehaviourRegistry.registerBehaviour("Wander", null);
	}

	public BehaviourEntity(String entityBehaviourName) {
		this.entityBehaviourName = entityBehaviourName;
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D graphics) {

	}
}
