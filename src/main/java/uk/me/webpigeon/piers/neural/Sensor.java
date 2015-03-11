package uk.me.webpigeon.piers.neural;

import uk.me.webpigeon.world.Entity;

/**
 * Represents a fact about the world
 */
public abstract class Sensor<T extends Entity> {
    protected T entity;

    protected Double value;

    public void reset() {
        value = null;
    }

    public abstract void setValue();

    public double getValue() {
        if (value == null) setValue();
        return value;
    }

    public void bind(T entity) {
        this.entity = entity;
    }
}
