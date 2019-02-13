
package core.main.smooth;

import core.main.smooth.motion.factories.OvershootMotionFactory;
import core.main.smooth.motion.factories.SCurveMotionFactory;
import core.main.smooth.motion.factories.SwishMotionFactory;

public class Motion {
    
    public static SwishMotionFactory swish(double speed){
        return new SwishMotionFactory(speed, 0.5);
    }
    public static SwishMotionFactory swish(double speed, double tolerance){
        return new SwishMotionFactory(speed, tolerance);
    }
    
    public static SCurveMotionFactory sCurve(int timeSteps){
        return new SCurveMotionFactory(timeSteps);
    }
    
    public static OvershootMotionFactory overshoot(double acceleration, double friction){
        return new OvershootMotionFactory(acceleration, friction, 0.5, 2.4);
    }
    public static OvershootMotionFactory overshoot(double acceleration, double friction, double distTolerance, double velocityTolerance){
        return new OvershootMotionFactory(acceleration, friction, distTolerance, velocityTolerance);
    }
}
