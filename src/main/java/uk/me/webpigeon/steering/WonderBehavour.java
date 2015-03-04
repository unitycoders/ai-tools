package uk.me.webpigeon.steering;

public class WonderBehavour {
	private double wanderRadius;
	private double wanderDistance;
	private double jitter;
	
	private void calc() {
		//circle = p + dw.unit(v)
		//target q = pw + c
		//each time step randomly change c
		//displace to c + (ax, ay) where x and y are random between -1 and 1
		//project back onto circle: c = rw.unit(c + (ax, ay))
		//wander = seek(p + dw.unit(v) + x)
	}

}
