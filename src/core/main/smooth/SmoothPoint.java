
package core.main.smooth;

import core.main.smooth.motion.MotionFactory;
import core.main.structs.Vector;
import java.awt.Point;

public class SmoothPoint {
    
    private Point pos;
    private Vector smooth;
    private MotionFactory motionFactory;
    private MotionVector motionVector;
    
    public SmoothPoint(Point pos, MotionFactory motionFactory){
        this.pos = pos;
        this.smooth = new Vector(pos);
        this.motionFactory = motionFactory;
    }
    
    public void setMotionFactory(MotionFactory motionFactory){
        this.motionFactory = motionFactory;
    }
    
    public void setPos(Point pos){ 
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
    
    public Point getPos(){ return pos; }
    public Vector getSmooth(){ return smooth; }
    public boolean atTarget(){ return motionVector==null ? true : motionVector.atTarget(); }
}
