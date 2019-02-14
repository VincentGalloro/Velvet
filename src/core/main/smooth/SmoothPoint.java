
package core.main.smooth;

import core.main.smooth.motion.SmoothMotion;
import core.main.structs.Vector;
import java.awt.Point;

public class SmoothPoint {
    
    private Point pos;
    private Vector smooth;
    private MotionFactory motionFactory;
    private MotionVector motion;
    
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
    
    public Point getPos(){ return pos; }
    public Vector getSmooth(){ return smooth; }
    public boolean atTarget(){ return motion==null ? true : motion.atTarget(); }
}
