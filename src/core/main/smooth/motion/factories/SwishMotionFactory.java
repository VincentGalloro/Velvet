
package core.main.smooth.motion.factories;

import core.main.smooth.motion.ISmoothMotion;
import core.main.smooth.motion.SwishMotion;
import core.main.structs.Vector;

public class SwishMotionFactory implements IMotionFactory{

    private final double speed, tolerance;

    public SwishMotionFactory(double speed, double tolerance){
        this.speed = speed;
        this.tolerance = tolerance;
    }
    
    public ISmoothMotion create(Vector start, Vector end) {
        return new SwishMotion(start, end, speed, tolerance);
    }
    
}
