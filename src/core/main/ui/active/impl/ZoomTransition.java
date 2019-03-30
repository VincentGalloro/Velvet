
package core.main.ui.active.impl;

import core.main.VGraphics;
import core.main.smooth.SmoothScalar;
import core.main.smooth.motion.Motion;
import core.main.structs.Vector;
import core.main.ui.active.IUpdateable;
import core.main.ui.elements.IElement;
import java.awt.geom.AffineTransform;
import java.util.function.Supplier;

public class ZoomTransition implements IUpdateable{

    private SmoothScalar scale;
    private Supplier<Vector> sizeGen;
    
    public ZoomTransition(Supplier<Vector> sizeGen){
        this.sizeGen = sizeGen;
        scale = new SmoothScalar(1, Motion.swish(15));
    }
    
    public void apply(IElement target){
        target.addUpdateHandler(this);
        target.addPreRenderHandler(this::preRender);
        target.addPostRenderHandler(this::postRender);
    }
    
    public void setScale(double scale){ this.scale.setValue(scale); }
    
    public void update(AffineTransform at) {
        scale.update();
    }

    public void preRender(VGraphics g) {
        g.save();
        g.translate(sizeGen.get().multiply(0.5));
        g.scale(scale.getSmooth());
        g.translate(sizeGen.get().multiply(-0.5));
    }

    public void postRender(VGraphics g) {
        g.reset();
    }
}
