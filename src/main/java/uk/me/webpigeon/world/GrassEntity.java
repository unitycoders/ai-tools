package uk.me.webpigeon.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import uk.me.webpigeon.util.Vector2D;

/**
 * Created by Piers on 04/03/2015.
 */
public class GrassEntity extends Entity {
	private static final Float GRASS_MAX_AGE = 1000f;
	private static BufferedImage grassImage;

	static {
		try {
			grassImage = ImageIO.read(GrassEntity.class
					.getResourceAsStream("/images/grass.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private int age;
	private int seedCooldown;

	public GrassEntity(Vector2D location) {
		this.age = 0;
		this.location = location;
	}

	public void update() {
		super.update();
		age++;
		seedCooldown--;
		if (age > 500 && seedCooldown <= 0) {
			Vector2D randomPolar = Vector2D.getRandomPolar(2 * Math.PI, 0, 150, true);
			Vector2D cartDir = Vector2D.add(Vector2D.toCartesian(randomPolar),getLocation());
			
			world.addEntity(new GrassEntity(cartDir));
			seedCooldown = (int)(Math.random() * 1000);
		}
		
		if (age > GRASS_MAX_AGE) {
			health = 0;
		}
	}

	@Override
	public void draw(Graphics2D graphics) {
		if (grassImage != null) {
			//graphics.drawImage(grassImage, null, (int) (location.x - 16),
			//		(int) (location.y - 16));
		}
		debugDraw(graphics);
	}
	
	public void debugDraw(Graphics2D graphics) {
		graphics.setColor(new Color(0.0f, 1 - (age / GRASS_MAX_AGE), 0.0f));
		graphics.fillRect((int)(location.x - 10), (int)(location.y - 10), 20, 20);
	}
	
	public int getZIndex() {
		return -100;
	}
}
