
package core.main.smooth.motion.factories;

import core.main.smooth.motion.ISmoothMotion;
import core.main.smooth.motion.SCurveMotion;
import core.main.structs.Vector;

public class SCurveMotionFactory implements IMotionFactory{

    private int timeSteps;
    
    public SCurveMotionFactory(int timeSteps){
        this.timeSteps = timeSteps;
    }
    
    public ISmoothMotion create(Vector start, Vector end) {
        return new SCurveMotion(start, end, timeSteps);
    }
}
