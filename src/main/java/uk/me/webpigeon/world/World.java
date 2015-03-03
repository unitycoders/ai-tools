package uk.me.webpigeon.world;

/**
 * Created by Piers on 03/03/2015.
 */
public class World {

    // width in squares of the grid
    private int width;
    // height in squares of the grid
    private int height;
    // size in pixels of the grid
    private int gridSize;

    public int getCenterOfGrid(int input){
        return (input * gridSize) + (gridSize / 2);
    }
}
