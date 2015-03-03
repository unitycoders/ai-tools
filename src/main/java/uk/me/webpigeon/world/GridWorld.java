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
        JFrame frame = new JFrame("Crazy World");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setPreferredSize(new Dimension(800, 600));

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
}
