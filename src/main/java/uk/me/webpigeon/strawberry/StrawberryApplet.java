package uk.me.webpigeon.strawberry;

import java.applet.Applet;
import java.awt.*;

/**
 * Created by Piers on 05/03/2015.
 */
public class StrawberryApplet extends Applet {

    StrawberryWorld world = new StrawberryWorld(800, 600);

    @Override
    public void init() {
        Thread thread = new Thread(world);
        this.add(world);
        thread.start();
        super.init();
    }

    @Override
    public void paint(Graphics g) {
        world.paint(g);
    }
}
