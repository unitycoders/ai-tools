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

	public GrassEntity(Vector2D location) {
		this.location = location;
	}

	public void update() {
		// TODO do we regenerate
	}

	@Override
	public void draw(Graphics2D graphics) {
		if (grassImage != null) {
			graphics.drawImage(grassImage, null, (int) (location.x - 20),
					(int) (location.y - 20));
		}
	}
}
