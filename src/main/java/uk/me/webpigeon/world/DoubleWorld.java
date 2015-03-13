package uk.me.webpigeon.world;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import uk.me.webpigeon.joseph.CowPopulationManager;
import uk.me.webpigeon.piers.HunterAgent;
import uk.me.webpigeon.piers.HunterVillage;
import uk.me.webpigeon.util.Vector2D;

public class DoubleWorld extends World {

    public DoubleWorld(int width, int height) {
        super(width, height);
    }

    @Override
    public void draw(Graphics2D g2) {
        scaleX = getWidth() / (width * 1.0);
        scaleY = getHeight() / (height * 1.0);
        g2.scale(scaleX, scaleY);

        g2.setColor(new Color(123, 191, 106));
        g2.fillRect(0, 0, width, height);
        super.draw(g2);
    }

    public static void main(String[] args) {
    	DoubleWorld.DEBUG_DRAW = true;
        World world = new DoubleWorld(800, 600);

        HunterAgent.initialiseBehaviour();
        HunterAgent.initialiseSensors();

        CowPopulationManager pop = new CowPopulationManager(10);
        pop.addMoreCows(10, world);
        world.addComponent(pop);

        for (int i = 0; i < 50; i++) {
            world.addEntity(new GrassEntity(Vector2D.getRandomCartesian(
                    world.width, world.height, true)));
        }

        world.addEntity(new HunterVillage(new Vector2D(250, 250)));


        Thread t = new Thread(world);
        t.start();

        world.addMouseListener(new MouseListener(world));

        JFrame frame = new JFrame("Crazy Double World");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.add(world);
        frame.add(new JPanel(), BorderLayout.EAST);
        frame.pack();
        frame.setVisible(true);
    }

}
