package uk.me.webpigeon.world;

import javax.swing.*;

/**
 * Created by Piers on 03/03/2015.
 */
public class World extends JComponent {

    // width of the world
    protected int width;
    // height of the world
    protected int height;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
