
package core.main.smooth;

import core.main.smooth.motion.SmoothMotion;

public class MotionScalar{ 
    
    private final double start, end;
    private final SmoothMotion motion;
    
    public MotionScalar(double start, double end, SmoothMotion motion){
        this.start = start;
        this.end = end;
        this.motion = motion;
    }
    
    public double update(){
        motion.update();
        return start * (1 - motion.getDelta()) + end * motion.getDelta();
    }
    
    public boolean atTarget(){ return motion.atTarget(); }
    
}
