package uk.me.webpigeon.world;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Piers on 03/03/2015.
 */
public class GridWorld extends World {


    // size in pixels of the grid
    private int gridSize = 50;

    public GridWorld(int width, int height) {
        super(width, height);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Crazy Grid World");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setPreferredSize(new Dimension(800, 600));

        World world = new GridWorld(10, 10);
        world.addEntity(new DebugEntity());
        frame.add(world);

        frame.pack();
        frame.setVisible(true);
    }

    public int getCenterOfGrid(int input) {
        return (input * gridSize) + (gridSize / 2);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension((width * gridSize) + gridSize, (height * gridSize) + gridSize);
    }


    @Override
    public void draw(Graphics2D g2) {
        System.out.println("Hello");
        g2.setColor(Color.GREEN);
        for (int i = 0; i < width; i++) {
            g2.drawLine(i * gridSize, 0, i * gridSize, height * gridSize);
            g2.drawLine(0, i * gridSize, width * gridSize, i * gridSize);
        }
        super.draw(g2);
    }
}
