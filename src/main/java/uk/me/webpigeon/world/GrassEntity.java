package uk.me.webpigeon.world;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import uk.me.webpigeon.util.Vector2D;

/**
 * Created by Piers on 04/03/2015.
 */
public class GrassEntity extends Entity {
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
		// TODO do we regenerate
		age++;
		seedCooldown--;
		if (age < 100 && seedCooldown < 0 && Math.random() < 0.01) {
			Vector2D randomPolar = Vector2D.getRandomPolar(2 * Math.PI, 0, 150, true);
			Vector2D cartDir = Vector2D.add(Vector2D.toCartesian(randomPolar),getLocation());
			
			world.addEntity(new GrassEntity(cartDir));
			seedCooldown = 1000;
		}
		
		if (age > 100000) {
			health--;
		}
	}

	@Override
	public void draw(Graphics2D graphics) {
		if (grassImage != null) {
			graphics.drawImage(grassImage, null, (int) (location.x - 20),
					(int) (location.y - 20));
		}
	}
}
