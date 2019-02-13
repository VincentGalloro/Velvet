package core.main.smooth.motion.factories;

import core.main.smooth.motion.ISmoothMotion;
import core.main.smooth.motion.LinearMotion;
import core.main.structs.Vector;

public class LinearMotionFactory implements IMotionFactory{

    private int timeSteps;
    
    public LinearMotionFactory(int timeSteps){
        this.timeSteps = timeSteps;
    }
    
    public ISmoothMotion create(Vector start, Vector end) {
        return new LinearMotion(start, end, timeSteps);
    }
}