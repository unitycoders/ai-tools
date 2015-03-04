package uk.me.webpigeon.world;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import uk.me.webpigeon.steering.WanderingBehaviour;
import uk.me.webpigeon.steering.WeightedBehaviour;
import uk.me.webpigeon.util.Vector2D;

public class DoubleWorld extends World {

    public DoubleWorld(int width, int height) {
        super(width, height);
    }

    @Override
    public void draw(Graphics2D g2) {
        double screenWidth = getWidth() / (width * 1.0);
        double screenHeight = getHeight() / (height * 1.0);
        g2.scale(screenWidth, screenHeight);

        g2.setColor(new Color(123, 191, 106));
        g2.fillRect(0, 0, width, height);
        super.draw(g2);
    }

    public static void main(String[] args) {
        World world = new DoubleWorld(800, 600);

        for (int i = 0; i < 50; i++) {
            world.addEntity(new GrassEntity(Vector2D.getRandomCartesian(world.width, world.height, true)));
        }
        for (int i = 0; i < 10; i++) {
            world.addEntity(EntityFactory.buildCow(world.width, world.height));
        }
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
