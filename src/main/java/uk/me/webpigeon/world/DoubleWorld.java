package uk.me.webpigeon.world;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import uk.me.webpigeon.steering.SeekBehavour;
import uk.me.webpigeon.util.Vector2D;

public class DoubleWorld extends World{
	
	public DoubleWorld(int width, int height) {
		super(width, height);
	}

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.draw(g2);
    }
	
	public static void main(String[] args) {
		World world = new DoubleWorld(50, 50);
		
		Entity entity = new SteeringEntity(new SeekBehavour(new Vector2D(100, 100)));
		world.addEntity(entity);
		
		Thread t = new Thread(world);
		t.start();
		
        JFrame frame = new JFrame("Crazy Double World");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.add(world);
        frame.pack();
        frame.setVisible(true);
	}
	
}
