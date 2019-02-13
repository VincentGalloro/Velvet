package core.main.smooth.motion.factories;

import core.main.smooth.motion.ISmoothMotion;
import core.main.smooth.motion.OvershootMotion;
import core.main.structs.Vector;

public class OvershootMotionFactory implements IMotionFactory{

    private double acc, friction, distTolerance, velTolerance;
    
    public OvershootMotionFactory(double acc, double friction, double distTolerance, double velTolerance){
        this.acc = acc;
        this.friction = friction;
        this.distTolerance = distTolerance;
        this.velTolerance = velTolerance;
    }
    
    public ISmoothMotion create(Vector start, Vector end) {
        return new OvershootMotion(start, end, acc, friction, distTolerance, velTolerance);
    }
}