package uk.me.webpigeon.util;

import java.util.HashMap;

/**
 * Caches Vector2D's
 */
public class CachedVector2DSource {
    private HashMap<Integer, HashMap<Integer, Vector2D>> vectors;
    int width;
    int height;

    public CachedVector2DSource(int width, int height) {
        vectors = new HashMap<>(width);
        this.width = width;
        this.height = height;
    }

    public Vector2D getVector(int x, int y) {
        if (!vectors.containsKey(x)) vectors.put(x, new HashMap<Integer, Vector2D>(height));
        if (!vectors.get(x).containsKey(y)) vectors.get(x).put(y, new Vector2D(x, y, false));
        return vectors.get(x).get(y);
    }

    public Vector2D getVector(double x, double y) {
        return getVector((int) x, (int) y);
    }

    public int numberOfVectors() {
        int sum = 0;
        for (HashMap<Integer, Vector2D> temp : vectors.values()) {
            sum += temp.size();
        }
        return sum;
    }
}
