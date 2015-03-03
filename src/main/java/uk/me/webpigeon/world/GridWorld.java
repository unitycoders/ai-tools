package uk.me.webpigeon.world;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Piers on 03/03/2015.
 */
public class GridWorld extends World {

    // width in squares of the grid
    private int width;
    // height in squares of the grid
    private int height;
    // size in pixels of the grid
    private int gridSize;

    public GridWorld(int width, int height) {
        this.width = width;
        this.height = height;
        this.gridSize = 50;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Crazy World");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setPreferredSize(new Dimension(800, 600));

        frame.pack();
        frame.setVisible(true);
    }

    public int getCenterOfGrid(int input){
        return (input * gridSize) + (gridSize / 2);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension((width * gridSize) + gridSize, (height * gridSize) + gridSize);
    }
}
