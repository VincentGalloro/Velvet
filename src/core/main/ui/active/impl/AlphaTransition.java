
package core.main.ui.active.impl;

import core.main.VGraphics;
import core.main.smooth.SmoothScalar;
import core.main.smooth.motion.Motion;
import core.main.ui.active.IRenderable;
import core.main.ui.active.IUpdateable;
import core.main.ui.elements.IElement;
import java.awt.AlphaComposite;
import java.awt.geom.AffineTransform;

public class AlphaTransition implements IUpdateable{

    private IElement target;
    private SmoothScalar alpha;
    private final IRenderable preRender, postRender;
    
    public AlphaTransition(){
        alpha = new SmoothScalar(1, Motion.linear(30));
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
            target = null;
        }
    }
    
    public void overrideAlpha(double alpha){ this.alpha.override(alpha); }
    public void setAlpha(double alpha){ this.alpha.setValue(alpha); }
    
    public void update(AffineTransform at) {
        alpha.update();
    }

    public void preRender(VGraphics g) {
        AlphaComposite alcom = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, (float)alpha.getSmooth());
        g.setComposite(alcom);
    }

    public void postRender(VGraphics g) {
        AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
        g.setComposite(alcom);
    }
}
