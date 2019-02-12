
package core.main.smooth;

import core.main.smooth.motion.factories.SCurveMotionFactory;
import core.main.smooth.motion.factories.SwishMotionFactory;

public class Motion {
    
    public static SwishMotionFactory swish(double speed){
        return new SwishMotionFactory(speed, 0.1);
    }
    public static SwishMotionFactory swish(double speed, double tolerance){
        return new SwishMotionFactory(speed, tolerance);
    }
    public static SCurveMotionFactory sCurve(int timeSteps){
        return new SCurveMotionFactory(timeSteps);
    }
}
