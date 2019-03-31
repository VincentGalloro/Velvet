package core.main.ui.active.impl;

import core.main.VGraphics;
import core.main.smooth.SmoothVector;
import core.main.smooth.motion.Motion;
import core.main.smooth.motion.MotionFactory;
import core.main.structs.Vector;
import core.main.ui.active.IRenderable;
import core.main.ui.active.IUpdateable;
import core.main.ui.elements.IElement;
import java.awt.geom.AffineTransform;

public class OffsetTransition implements IUpdateable{

    private IElement target;
    private SmoothVector pos;
    private final IRenderable preRender, postRender;
    
    public OffsetTransition(){
        this(Motion.swish(15));
    }
    
    public OffsetTransition(MotionFactory motion){
        pos = new SmoothVector(new Vector(), motion);
        preRender = this::preRender;
        postRender = this::postRender;
    }
    
    public void apply(IElement target){
        detach();
        this.target = target;
        target.addUpdateHandler(this);
        target.addPreRenderHandler(preRender);
        target.addPostRenderHandler(postRender);
    }
    public void detach(){
        if(target != null){
            target.removeUpdateHandler(this);
            target.removePreRenderHandler(preRender);
            target.removePostRenderHandler(postRender);
            target=null;
        }
    }
    
    public void setOffset(Vector offset){ pos.setPos(offset); }
    public void overrideOffset(Vector offset){ pos.overridePos(offset); }
    
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