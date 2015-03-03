package uk.me.webpigeon.world;

import java.awt.Graphics2D;
import java.util.List;

import javax.swing.*;

/**
 * Created by Piers on 03/03/2015.
 */
public class World extends JComponent {
	private List<Entity> entities;
	
	public void addEntity(Entity entity){
		entities.add(entity);
	}
	
	public void update() {
		
		for (Entity entity : entities) {
			entity.update(null);
		}
		
	}
	
	public void draw(Graphics2D g2) {
		for (Entity entity : entities) {
			entity.draw(g2);
		}
	}
}
