
package core.main.smooth;

import core.main.smooth.motion.MotionFactory;
import core.main.structs.Vector;

public class SmoothVector {
    
    private Vector pos, smooth;
    private MotionFactory motionFactory;
    private MotionVector motionVector;
    
    public SmoothVector(Vector pos, MotionFactory motionFactory){
        this.pos = pos;
        this.smooth = new Vector(pos);
        this.motionFactory = motionFactory;
    }
    
    public SmoothVector(MotionFactory motionFactory){
        this.motionFactory = motionFactory;
        this.smooth = new Vector();
    }
    
    public void setMotionFactory(MotionFactory motionFactory){
        this.motionFactory = motionFactory;
    }
    
    public void setPos(Vector pos){ 
        this.pos = pos; 
        motionVector = new MotionVector(smooth, new Vector(pos), motionFactory.create());
    }
    
    public void update(){
        if(motionVector != null){
            smooth = motionVector.update();
            if(atTarget()){ 
                smooth = new Vector(pos);
                motionVector = null; 
            }
        }
    }
    
    public Vector getPos(){ return pos; }
    public Vector getSmooth(){ return smooth; }
    public boolean atTarget(){ return motionVector==null ? true : motionVector.atTarget(); }
}
