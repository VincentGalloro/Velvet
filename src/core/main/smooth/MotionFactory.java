
package core.main.smooth;

import core.main.smooth.motion.LinearMotion;
import core.main.smooth.motion.MotionType;
import core.main.smooth.motion.OvershootMotion;
import core.main.smooth.motion.SCurveMotion;
import core.main.smooth.motion.SmoothMotion;
import core.main.smooth.motion.SwishMotion;
import core.main.structs.Vector;

public class MotionFactory {
    
    private final MotionType motionType;
    private final int timeSteps;
    
    public MotionFactory(MotionType motionType, int timeSteps){
        this.motionType = motionType;
        this.timeSteps = timeSteps;
    }
    
    public SmoothMotion create(){
        if(motionType == MotionType.LINEAR){ return new LinearMotion(timeSteps); }
        if(motionType == MotionType.OVERSHOOT){ return new OvershootMotion(timeSteps); }
        if(motionType == MotionType.SCURVE){ return new SCurveMotion(timeSteps); }
        if(motionType == MotionType.SWISH){ return new SwishMotion(timeSteps); }
        return null;
    }
    
    public MotionVector create(Vector start, Vector end){
        return new MotionVector(start, end, create());
    }
}
