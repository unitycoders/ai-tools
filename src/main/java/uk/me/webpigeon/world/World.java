package uk.me.webpigeon.world;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComponent;

import uk.me.webpigeon.joseph.cow.Cow;
import uk.me.webpigeon.util.Vector2D;

/**
 * Created by Piers on 03/03/2015.
 */
public class World extends JComponent implements Runnable {
    public static Boolean DEBUG_DRAW = false;
    private List<Entity> entities;
    private List<WorldComponent> components;

    private List<Entity> addList;

    protected double scaleX;
    protected double scaleY;

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
        this.components = new ArrayList<WorldComponent>();
        this.addList = new ArrayList<Entity>();
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

    /**
     * Adds a new WorldComponent to this world
     *
     * @param component The WorldComponent to be added
     */
    public void addComponent(WorldComponent component) {
        components.add(component);
    }

    /**
     * Adds the entity to the world and binds the world to the entity
     *
     * @param entity The entity to be added to this world
     */
    public void addEntity(Entity entity) {
        addList.add(entity);
    }

    public void removeEntity(Entity entity) {
        entity.health = 0;
    }

    public void update(long timeOfLastUpdate) {

        Iterator<Entity> entItr = entities.iterator();
        while (entItr.hasNext()) {
            Entity entity = entItr.next();
            entity.update();

            //reap dead entities
            if (entity.isDead()) {
                entItr.remove();
            }
        }

        for (WorldComponent component : components) {
            component.update(this, 0);
        }

        Iterator<Entity> addItr = addList.iterator();
        while (addItr.hasNext()) {
            Entity entity = addItr.next();
            entities.add(entity);
            entity.bind(this);
            addItr.remove();
        }

    }

    /**
     * Gets the entities that are within the provided radius to the provided source entity
     *
     * @param source The start point for the check
     * @param radius The search radius to use
     * @return The list of entities that are close enough
     */
    public ArrayList<Entity> getNearEntities(Entity source, double radius) {
    	Vector2D srcLoc = source.getLocation();
    	List<Entity> closeBy = entities.stream()
    			.filter(s -> srcLoc.dist(s.location) <= radius)
    			.collect(Collectors.toList());
    	
        return new ArrayList<Entity>(closeBy);
    }

    /**
     * Gets the entities that are within the provided radius to the provided source entity and are of a certain class
     *
     * @param source The start point for the check
     * @param radius The search radius to use
     * @param type   The class of the entity to search for
     * @return The list of entities that are close enough of the correct class
     */
    public ArrayList<Entity> getNearEntities(Entity source, double radius, Tag type){
    	ArrayList<Entity> closeBy = getNearEntities(source, radius);
    	List<Entity> filtered = closeBy.stream().filter(e -> e.hasTag(type)).collect(Collectors.toList());
    	return new ArrayList<Entity>(filtered);
    }

    /**
     * Gets the nearest entity of a given type to the provided source
     *
     * @param source The start point for the check
     * @param type   The clas of the entity to search for
     * @return The entity that is the nearest of that type
     */
    public Entity getNearestEntityOfType(Entity source, Tag type) {
        return getNearestEntityOfType(source.location, type);
    }
    
    public Entity getNearestEntityOfType(Vector2D source, Tag type) {    	
        List<Entity> entityList = entities.stream().filter(s -> s.hasTag(type))
                .sorted( (s1, s2) -> Double.compare(source.dist(s1.getLocation()), source.dist(s2.getLocation())))
                .collect(Collectors.toList());
        
        if (entityList.isEmpty()) {
        	return null;
        }
        
        return entityList.get(0);
    }
    
    @Deprecated
    public Entity getNearestEntityOfType(Entity source, Class<?> type) {   
    	if (type.equals(Cow.class)) {
    		return getNearestEntityOfType(source, Tag.COW);
    	}
    	
    	return null;
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        draw(g2);
    }

    public void draw(Graphics2D g2) {
        List<Entity> renderList = new ArrayList<>(entities);
        Collections.sort(renderList, new RenderOrder());

        for (Entity entity : renderList) {
            entity.draw(g2);
            if (DEBUG_DRAW) {
                entity.debugDraw(g2);
            }
        }
    }

    public Point project(Point2D p) {
        int x = (int) (p.getX() * scaleX);
        int y = (int) (p.getY() * scaleY);
        return new Point(x, y);
    }

	public Collection<Entity> getEntities() {
		return Collections.unmodifiableList(entities);
	}

}
