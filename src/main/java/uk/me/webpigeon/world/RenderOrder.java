package uk.me.webpigeon.world;

import java.util.Comparator;

public class RenderOrder implements Comparator<Entity> {

	@Override
	public int compare(Entity arg0, Entity arg1) {
		return Integer.compare(arg0.getZIndex(), arg1.getZIndex());
	}

}
