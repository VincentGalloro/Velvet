
package core.main.ui.composites;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.active.impl.ZoomTransition;
import core.main.ui.elements.BasicScrollable;
import core.main.ui.elements.BasicSizeable;
import core.main.ui.elements.IBoxable;
import core.main.ui.elements.IElementBuilder;
import core.main.ui.elements.impl.BoxElement;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.Shape;

public abstract class BasicScrollBar extends BasicScrollable implements IBoxable{
    
    public class Builder extends BasicScrollable.Builder{
    
        private IElementBuilder knobBuilder;
        
        public Builder(){
            knobBuilder = knob.getBuilder();
        }
        
        public void handleString(String field, String value){
            super.handleString(field, value);
            knobBuilder.handleString(field, value);
        }
    }
    
    protected final BoxElement knob;
    protected final BasicSizeable knobSizeable;
    
    public BasicScrollBar(){
        knob = new BoxElement();
        knob.setFillColor(Color.WHITE);
        knob.setRounding(16d);
        knobSizeable = new BasicSizeable();
        knobSizeable.setSize(new Vector(16));
        knob.setElement(knobSizeable);
        
        addUpdateHandler(this::knobUpdate);
        addPostRenderHandler(this::knobRender);
        
        ZoomTransition zt = new ZoomTransition(() -> knob.getSize());
        zt.apply(knob);
        addHoverStartHandler(() -> zt.setScale(1.1));
        addHoverEndHandler(() -> zt.setScale(1));
    }
    
    protected abstract AffineTransform getTransform();
    
    public final void knobUpdate(AffineTransform at){
        AffineTransform nat = new AffineTransform(at);
        nat.concatenate(getTransform());
        knob.update(nat);
    }
    
    public void setOutlineColor(Color o) { knob.setOutlineColor(o); }
    public void setFillColor(Color f) { knob.setFillColor(f); }
    public void setOutlineThickness(float t) { knob.setOutlineThickness(t); }
    public void setRounding(Double d){ knob.setRounding(d); }
    
    public Color getOutlineColor() { return knob.getOutlineColor(); }
    public Color getFillColor() { return knob.getFillColor(); }
    public Shape getShape(){ return knob.getShape(); }
    
    public final void knobRender(VGraphics g){
        g.save();
        g.transform(getTransform());
        knob.render(g);
        g.reset();
    }
}
