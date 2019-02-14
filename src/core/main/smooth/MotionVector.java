
package core.main.smooth;

import core.main.smooth.motion.SmoothMotion;
import core.main.structs.Vector;

public class MotionVector {
    
    private Vector start, end;
    private SmoothMotion motion;
    
    public MotionVector(Vector start, Vector end, SmoothMotion motion){
        this.start = start;
        this.end = end;
        this.motion = motion;
    }
    
    public Vector update(){
        motion.update();
        return start.multiply(1 - motion.getDelta()).add(end.multiply(motion.getDelta()));
    }
    
    public boolean atTarget(){ return motion.atTarget(); }
}
