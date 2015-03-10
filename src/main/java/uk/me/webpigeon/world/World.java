package uk.me.webpigeon.world;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComponent;

import uk.me.webpigeon.util.Vector2D;

/**
 * Created by Piers on 03/03/2015.
 */
public class World extends JComponent implements Runnable {
    private static final Boolean DEBUG_DRAW = false;
    private List<Entity> entities;
    private List<WorldComponent> components;
    
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
     * @param component The WorldComponent to be added
     */
    public void addComponent(WorldComponent component) {
    	components.add(component);
    }

    /**
     * Adds the entity to the world and binds the world to the entity
     * @param entity The entity to be added to this world
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
        entity.bind(this);
    }
    
    public void removeEntity(Entity entity) {
    	entity.health = 0;
    }

    public void update(long timeOfLastUpdate) {

    	Iterator<Entity> entItr = entities.iterator();
    	while(entItr.hasNext()) {
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

    }

    /**
     * Gets the entities that are within the provided radius to the provided source entity
     * @param source The start point for the check
     * @param radius The search radius to use
     * @return The list of entities that are close enough
     */
    public ArrayList<Entity> getNearEntities(Entity source, double radius) {
        return getNearEntities(source, radius, Entity.class);
    }

    /**
     * Gets the entities that are within the provided radius to the provided source entity and are of a certain class
     * @param source The start point for the check
     * @param radius The search radius to use
     * @param type The class of the entity to search for
     * @return The list of entities that are close enough of the correct class
     */
    public ArrayList<Entity> getNearEntities(Entity source, double radius, Class type){
        ArrayList<Entity> nearEntities = new ArrayList<>();
        nearEntities.addAll(entities.stream()
                .filter(s -> s.getClass().isAssignableFrom(type))
                .filter(entity -> entity.getLocation().dist(source.getLocation()) < radius)
                .collect(Collectors.toList()));

        return nearEntities;
    }

    /**
     * Gets the nearest entity of a given type to the provided source
     * @param source The start point for the check
     * @param type The clas of the entity to search for
     * @return The entity that is the nearest of that type
     */
    public Entity getNearestEntityOfType(Entity source, Class type) {
        return entities.stream().filter(s -> s.getClass().isAssignableFrom(type))
                .sorted((s1, s2) -> (int) (source.getLocation().dist(s1.getLocation()) - source.getLocation().dist(s2.getLocation())))
                .collect(Collectors.toList()).get(0);
    }
    
    public Entity getNearestEntityOfType(Vector2D source, Class type) {
        List<Entity> entityList = entities.stream().filter(s -> type.isAssignableFrom(s.getClass()))
                .sorted((s1, s2) -> (int) (source.dist(s1.getLocation()) - source.dist(s2.getLocation())))
                .collect(Collectors.toList());
        
        if (entityList.isEmpty()) {
        	return null;
        }
        
        return entityList.get(0);
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
    
    public Point project(Point2D p) {
    	int x = (int)(p.getX() * scaleX);
    	int y = (int)(p.getY() * scaleY);
    	return new Point(x, y);
    }

}
