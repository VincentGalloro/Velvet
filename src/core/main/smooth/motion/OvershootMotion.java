package core.main.smooth.motion;

import core.main.structs.Vector;

public class OvershootMotion implements ISmoothMotion{

    private Vector current, target, vel;
    private double acc, friction;
    private double distTolerance, velTolerance;
    
    public OvershootMotion(Vector current, Vector target, double acc, double friction, double distTolerance, double velTolerance){
        this.current = current;
        this.target = target;
        this.acc = acc;
        this.friction = friction;
        this.distTolerance = distTolerance;
        this.velTolerance = velTolerance;
        vel = new Vector();
    }

    public Vector update() {
        vel = vel.multiply(1-friction);
        vel = vel.move(current.getAngle(target), acc);
        current = current.add(vel);
        return current;
    }

    public boolean atTarget() {
        return current.withinRange(target, distTolerance) && vel.magnitude() <= velTolerance;
    }
}