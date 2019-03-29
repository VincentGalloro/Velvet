package core.main.ui.active.impl;

import core.main.VGraphics;
import core.main.smooth.SmoothVector;
import core.main.smooth.motion.Motion;
import core.main.structs.Vector;
import core.main.ui.active.IUpdateable;
import core.main.ui.elements.IElement;
import java.awt.geom.AffineTransform;

public class OffsetTransition implements IUpdateable{

    private SmoothVector pos;
    
    public OffsetTransition(){
        pos = new SmoothVector(new Vector(), Motion.swish(15));
    }
    
    public void apply(IElement target){
        target.addUpdateHandler(this);
        target.addPreRenderHandler(this::preRender);
        target.addPostRenderHandler(this::postRender);
    }
    
    public void setOffset(Vector offset){ pos.setPos(offset); }
    
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