
package core.main.smooth;

import core.main.smooth.motion.SmoothMotion;
import core.main.structs.Vector;
import java.awt.Point;

public class SmoothVector {
    
    private Vector pos, smooth;
    private MotionFactory motionFactory;
    private MotionVector motion;
    
    public SmoothVector(Vector pos, MotionFactory motionFactory){
        this.pos = pos;
        this.smooth = new Vector(pos);
        this.motionFactory = motionFactory;
    }
    
    public void setMotionFactory(MotionFactory motionFactory){
        this.motionFactory = motionFactory;
    }
    
    public void setPos(Vector pos){ 
        this.pos = pos; 
        motion = motionFactory.create(smooth, new Vector(pos));
    }
    
    public void update(){
        if(motion != null){
            smooth = motion.update();
            if(atTarget()){ 
                smooth = new Vector(pos);
                motion = null; 
            }
        }
    }
    
    public Vector getPos(){ return pos; }
    public Vector getSmooth(){ return smooth; }
    public boolean atTarget(){ return motion==null ? true : motion.atTarget(); }
}
