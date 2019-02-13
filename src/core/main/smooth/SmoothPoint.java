
package core.main.smooth;

import core.main.smooth.motion.ISmoothMotion;
import core.main.smooth.motion.factories.IMotionFactory;
import core.main.structs.Vector;
import java.awt.Point;

public class SmoothPoint {
    
    private Point pos;
    private Vector smooth;
    private IMotionFactory motionFactory;
    private ISmoothMotion motion;
    
    public SmoothPoint(Point pos, IMotionFactory motionFactory){
        this.pos = pos;
        this.smooth = new Vector(pos);
        this.motionFactory = motionFactory;
    }
    
    public void setPos(Point pos){ 
        this.pos = pos; 
        motion = motionFactory.create(smooth, new Vector(pos));
    }
    
    public void update(){
        if(motion != null){
            smooth = motion.update();
            if(atTarget()){ motion = null; }
        }
    }
    
    public Point getPos(){ return pos; }
    public Vector getSmooth(){ return smooth; }
    public boolean atTarget(){ return motion==null ? true : motion.atTarget(); }
}
