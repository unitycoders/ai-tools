package uk.me.webpigeon.world;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import uk.me.webpigeon.util.Vector2D;

public class MouseListener extends MouseAdapter {
	private World world;
	
	public MouseListener(World world) {
		this.world = world;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point worldPoint = world.project(e.getPoint());
		Vector2D worldVec = new Vector2D(worldPoint.x, worldPoint.y);
		Entity entity = world.getNearestEntityOfType(worldVec, Tag.AGENT);
		System.out.println("CLICKY CLICKY "+entity);
	}

}
