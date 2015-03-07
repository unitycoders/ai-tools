package uk.me.webpigeon.world;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComponent;

/**
 * Created by Piers on 03/03/2015.
 */
public class World extends JComponent implements Runnable {
    private static final Boolean DEBUG_DRAW = false;
    private List<Entity> entities;

    // width of the world
    protected int width;
    // height of the world
    protected int height;

    // boolean to say are we paused
    protected boolean paused;
    // boolean to say are we still running
    protected boolean running = true;

    // How long we aim to spend on each tick
    protected int tickDelay = 20;
    // Time spent since last update and this update
    protected long timeOfLastUpdate;
    protected long previousTime;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<Entity>();
    }

    @Override
    public void run() {
        while (running) {
            try {

                Thread.sleep(tickDelay);
                long currentTime = System.nanoTime();
                timeOfLastUpdate = currentTime - previousTime;
                previousTime = currentTime;

                if (!paused) {
                    update(timeOfLastUpdate);
                    repaint();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
        entity.bind(this);
    }

    public void update(long timeOfLastUpdate) {

        for (Entity entity : entities) {
            entity.update();
        }

    }

    public ArrayList<Entity> getNearEntities(Entity source, double radius) {
        return getNearEntities(source, radius, Entity.class);
    }

    public ArrayList<Entity> getNearEntities(Entity source, double radius, Class type){
        ArrayList<Entity> nearEntities = new ArrayList<>();
        nearEntities.addAll(entities.stream()
                .filter(s -> s.getClass().isAssignableFrom(type))
                .filter(entity -> entity.getLocation().dist(source.getLocation()) < radius)
                .collect(Collectors.toList()));

        return nearEntities;
    }

    public Entity getNearestEntityOfType(Entity source, Class type) {
        return entities.stream().filter(s -> s.getClass().isAssignableFrom(type))
                .sorted((s1, s2) -> (int) (source.getLocation().dist(s1.getLocation()) - source.getLocation().dist(s2.getLocation())))
                .collect(Collectors.toList()).get(0);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        draw(g2);
    }

    public void draw(Graphics2D g2) {
        for (Entity entity : entities) {
            entity.draw(g2);
            if (DEBUG_DRAW) {
                entity.debugDraw(g2);
            }
        }
    }

}
