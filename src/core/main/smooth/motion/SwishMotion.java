
package core.main.smooth.motion;

import core.main.structs.Vector;

public class SwishMotion implements ISmoothMotion{

    private final Vector target;
    private Vector current;
    private final double speed, tolerance;
    
    public SwishMotion(Vector current, Vector target, double speed, double tolerance){
        this.current = current;
        this.target = target;
        this.speed = speed;
        this.tolerance = tolerance;
    }
    
    public Vector update() {
        current = current.multiply(1-speed).add(target.multiply(speed));
        return current;
    }

    public boolean atTarget() {
        return current.withinRange(target, tolerance);
    }
    
}
