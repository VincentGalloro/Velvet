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

public class OffsetTransition extends SmoothVector implements IUpdateable{

    private IElement target;
    private final IRenderable preRender, postRender;
    
    public OffsetTransition(){
        this(Motion.swish(15));
    }
    
    public OffsetTransition(MotionFactory motion){
        super(new Vector(), motion);
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
    
    public void update(AffineTransform at) {
        super.update();
    }
    
    public AffineTransform getTransform(){
        AffineTransform at = new AffineTransform();
        at.translate(getSmooth().x, getSmooth().y);
        return at;
    }

    public void preRender(VGraphics g) {
        g.save();
        g.transform(getTransform());
    }

    public void postRender(VGraphics g) {
        g.reset();
    }
}