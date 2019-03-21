package core.main.ui.active.impl;

import core.main.VGraphics;
import core.main.smooth.SmoothVector;
import core.main.smooth.motion.Motion;
import core.main.structs.Vector;
import core.main.ui.active.IEventable;
import core.main.ui.active.IRenderable;
import core.main.ui.active.IUpdateable;
import java.awt.geom.AffineTransform;

public class OffsetTransition implements IUpdateable, IRenderable{

    private SmoothVector pos;
    
    public OffsetTransition(){
        pos = new SmoothVector(new Vector(), Motion.swish(15));
    }
    
    public class Event implements IEventable{
        private Vector offset;
        
        public Event(Vector offset){
            this.offset = offset;
        }
        
        public void onEvent(){
            pos.setPos(offset);
        }
    }
    
    public void update(AffineTransform at) {
        pos.update();
    }

    public void preRender(VGraphics g) {
        g.save();
        g.translate(pos.getSmooth());
    }

    public void postRender(VGraphics g) {
        g.reset();
    }
}